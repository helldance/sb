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
import com.coordsafe.core.rbac.decorators.GroupAssignUsersCheckboxTableDecorator;
import com.coordsafe.core.rbac.entity.Group;
import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.exception.GroupException;
import com.coordsafe.core.rbac.service.GroupService;
import com.coordsafe.core.rbac.service.UserService;

@Controller
@RequestMapping("/" + Constants.GROUPHOME + Constants.GROUPASSIGNUSERS)
public class GroupAssignUsersController {
	
	GroupService groupService;
	UserService userService;
	
	@Autowired
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	
	@Autowired
	public void setUserService(UserService roleService) {
		this.userService = roleService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Model assignUsers(
			@RequestParam(value = Constants.GROUPPARAM, required = true) String name,
			Model model) {
		model.addAttribute("group", groupService.findByName(name));
		model.addAttribute("userList", userService.findAll());
		
		GroupAssignUsersCheckboxTableDecorator decorator = new GroupAssignUsersCheckboxTableDecorator();
		decorator.setId("username"); // id = username from user
		decorator.setFieldName("group"); // field name is group
		model.addAttribute("groupAssignUsersCheckboxDecorator", decorator);
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String addUsers(Group groupOld, HttpServletRequest request) {
		String[] currentUser = request.getParameterValues("_chk");
		Set<User> users = new HashSet<User>();
		Group group = groupService.findByName(groupOld.getName());
		
		if (currentUser != null) {
			for (int i = 0; i < currentUser.length; i++) {
				users.add(userService.findByUsername(currentUser[i]));
			}
		} else {
			users.clear();
		}
		
		group.setUsers(users);
		try {
			groupService.updateGroup(group);
		} catch (GroupException e) {
			e.printStackTrace();
			return "redirect:/" + Constants.GROUPHOME + Constants.GROUPSEARCH;
		}
		
		return "redirect:/" + Constants.GROUPHOME + Constants.GROUPSEARCH;
	}
}
