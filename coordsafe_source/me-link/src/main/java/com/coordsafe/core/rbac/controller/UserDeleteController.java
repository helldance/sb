package com.coordsafe.core.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.rbac.exception.UserException;
import com.coordsafe.core.rbac.service.UserService;

@Controller
@RequestMapping("/" + Constants.USERHOME + Constants.USERDELETE)
public class UserDeleteController {

	UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Model deleteUser(@RequestParam(value = Constants.USERPARAM, required = true) String username, Model model) {
		
		model.addAttribute("username", username);
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String deleteUser1(@RequestParam(value = Constants.USERPARAM, required = true) String username) {
		
		try {
			userService.removeUser(username);
		} catch (UserException e) {
			e.printStackTrace();
			return "redirect:/" + Constants.USERHOME + Constants.USERSEARCH;
		}
		
		return "redirect:/" + Constants.USERHOME + Constants.USERSEARCH;
	}
}
