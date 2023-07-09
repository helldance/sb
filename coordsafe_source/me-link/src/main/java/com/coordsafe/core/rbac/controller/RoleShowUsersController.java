package com.coordsafe.core.rbac.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.rbac.decorators.RoleShowUsersCheckboxTableDecorator;
import com.coordsafe.core.rbac.entity.Role;
import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.exception.UserException;
import com.coordsafe.core.rbac.service.RoleService;
import com.coordsafe.core.rbac.service.UserService;

@Controller
@RequestMapping("/" + Constants.ROLEHOME + Constants.ROLEUSERS)
public class RoleShowUsersController {

	private RoleService roleService;
	private UserService userService;
	
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Model showUsers(@RequestParam(value = Constants.ROLEPARAM, required = true) String name, Model model) {
		model.addAttribute("userList", userService.findAll());
		model.addAttribute("roleUsers", roleService.findByRoleName(name).getUsers());
		model.addAttribute("role", roleService.findByRoleName(name));
		
		RoleShowUsersCheckboxTableDecorator decorator = new RoleShowUsersCheckboxTableDecorator();
		decorator.setId("username");
		decorator.setFieldName("role");
		
		model.addAttribute("roleShowUsersCheckboxTableDecorator", decorator);
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String addUsers(Role oldRole, HttpServletRequest request) {
		String[] currentUser = request.getParameterValues("_chk");
 		Role role = roleService.findByRoleName(oldRole.getName());
		
		if (currentUser != null) {
			clearRoles(role);
			for (int i = 0; i < currentUser.length; i++) {
				User user = userService.findByUsername(currentUser[i]);
				user.getRoles().add(role);
				try {
					userService.updateUser(user);
				} catch (UserException e) {
					e.printStackTrace();
				}
			}
		} else {
			clearRoles(role);
		}
		
		return "redirect:/" + Constants.ROLEHOME + Constants.ROLESEARCH;
	}
	
	private void clearRoles(Role role) {
		for (User user : role.getUsers()) {
			user.getRoles().remove(role);
			try {
				userService.updateUser(user);
			} catch (UserException e) {
				e.printStackTrace();
			}
		}
	}
}
