package com.coordsafe.core.rbac.controller;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.rbac.decorators.UserAssignRolesCheckboxTableDecorator;
import com.coordsafe.core.rbac.entity.Role;
import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.exception.UserException;
import com.coordsafe.core.rbac.service.RoleService;
import com.coordsafe.core.rbac.service.UserService;
import org.slf4j.*;
@Controller
@SessionAttributes("user")
@RequestMapping("/" + Constants.USERHOME + Constants.USERASSIGNROLES)
public class UserAssignRolesController {
	private static final Logger log = LoggerFactory.getLogger(UserAssignRolesController.class);
	UserService userService;
	RoleService roleService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Model assignRoles(
			@RequestParam(value = Constants.USERPARAM, required = true) String username,
			Model model) {
		model.addAttribute("user", userService.findByUsername(username));
		model.addAttribute("roleList", roleService.findAllRoles());
		
		UserAssignRolesCheckboxTableDecorator decorator = new UserAssignRolesCheckboxTableDecorator();
		decorator.setId("name"); // id = name from role
		decorator.setFieldName("user"); // field name is user
		model.addAttribute("userAssignRolesCheckboxDecorator", decorator);
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String addRoles(HttpServletRequest request, HttpSession session) {
		String[] currentRole = request.getParameterValues("_chk");
		log.info("The Role assigned POST method..." + currentRole.length);
		Set<Role> roles = new HashSet<Role>();
		User user = (User)session.getAttribute("user");//userService.findByUsername(userOld.getUsername());
		
		if (currentRole.length > 0 ) {
			for (int i = 0; i < currentRole.length; i++) {
				roles.add(roleService.findByRoleName(currentRole[i]));
			}
		} else {
			roles.clear();
		}
		
		user.setRoles(roles);
		try {
			userService.updateUser(user);
		} catch (UserException e) {
			e.printStackTrace();
			return "redirect:/" + Constants.USERHOME + Constants.USERSEARCH;
		}
		
		return "redirect:/" + Constants.USERHOME + Constants.USERSEARCH;
	}
}
