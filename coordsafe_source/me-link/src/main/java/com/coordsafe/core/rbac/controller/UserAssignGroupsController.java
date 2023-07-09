package com.coordsafe.core.rbac.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.rbac.decorators.UserAssignGroupsCheckboxTableDecorator;
import com.coordsafe.core.rbac.entity.Group;
import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.exception.GroupException;
import com.coordsafe.core.rbac.service.GroupService;
import com.coordsafe.core.rbac.service.UserService;
import org.slf4j.*;
@Controller
@RequestMapping("/" + Constants.USERHOME + Constants.USERASSIGNGROUPS)
public class UserAssignGroupsController {
	private static final Logger log = LoggerFactory.getLogger(UserAssignGroupsController.class);
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
	public Model assignGroup(
			@RequestParam(value = Constants.USERPARAM, required = true) String username,
			Model model) {
		log.info("We are in the Group assign get method...");
		model.addAttribute("user", userService.findByUsername(username));
		model.addAttribute("groups", groupService.findAll());
		
		UserAssignGroupsCheckboxTableDecorator decorator = new UserAssignGroupsCheckboxTableDecorator();
		decorator.setId("name"); // id = name from group
		decorator.setFieldName("user"); // field name is user
		model.addAttribute("userAssignGroupsCheckboxDecorator", decorator);
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String addGroups(User userOld, HttpServletRequest request) {
		String[] currentGroup = request.getParameterValues("_chk");
		User user = userService.findByUsername(userOld.getUsername());
		
		if (currentGroup != null) {
			clearGroups(user);
			for (int i = 0; i < currentGroup.length; i++) {
				Group group = groupService.findByName(currentGroup[i]);
				group.getUsers().add(user);
				try {
					groupService.updateGroup(group);
				} catch (GroupException e) {
					e.printStackTrace();
				}
			}
		} else {
			clearGroups(user);
		}
		
		return "redirect:/" + Constants.USERHOME + Constants.USERSEARCH;
	}
	
	private void clearGroups(User user) {		
		for (Group group : user.getGroups()) {
			group.getUsers().remove(user);
			try {
				groupService.updateGroup(group);
			} catch (GroupException e) {
				e.printStackTrace();
			}
		}
	}
}
