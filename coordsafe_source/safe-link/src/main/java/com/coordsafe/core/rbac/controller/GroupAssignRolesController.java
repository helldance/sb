package com.coordsafe.core.rbac.controller;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.rbac.decorators.GroupAssignRolesCheckboxTableDecorator;
import com.coordsafe.core.rbac.entity.Group;
import com.coordsafe.core.rbac.entity.Role;
import com.coordsafe.core.rbac.exception.GroupException;
import com.coordsafe.core.rbac.service.GroupService;
import com.coordsafe.core.rbac.service.RoleService;

@Controller
@RequestMapping("/" + Constants.GROUPHOME + Constants.GROUPASSIGNROLES)
public class GroupAssignRolesController {
	
	GroupService groupService;
	RoleService roleService;
	
	@Autowired
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Model assignRoles(
			@RequestParam(value = Constants.GROUPPARAM, required = true) String name,
			Model model) {
		model.addAttribute("group", groupService.findByName(name));
		model.addAttribute("roleList", roleService.findAllRoles());
		
		GroupAssignRolesCheckboxTableDecorator decorator = new GroupAssignRolesCheckboxTableDecorator();
		decorator.setId("name"); // id = name from role
		decorator.setFieldName("group"); // field name is group
		model.addAttribute("groupAssignRolesCheckboxDecorator", decorator);
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String addRoles(Group groupOld, HttpServletRequest request) {
		String[] currentRole = request.getParameterValues("_chk");
		Set<Role> roles = new HashSet<Role>();
		Group group = groupService.findByName(groupOld.getName());
		
		if (currentRole != null) {
			for (int i = 0; i < currentRole.length; i++) {
				roles.add(roleService.findByRoleName(currentRole[i]));
			}
		} else {
			roles.clear();
		}
		
		group.setRoles(roles);
		try {
			groupService.updateGroup(group);
		} catch (GroupException e) {
			e.printStackTrace();
			return "redirect:/" + Constants.GROUPHOME + Constants.GROUPSEARCH;
		}
		
		return "redirect:/" + Constants.GROUPHOME + Constants.GROUPSEARCH;
	}
}
