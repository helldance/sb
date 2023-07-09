package com.coordsafe.core.rbac.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.exception.UserException;
import com.coordsafe.core.rbac.service.UserService;

@Controller
@RequestMapping("/" + Constants.USERHOME + Constants.USERRESETPASSWORD)
public class UserResetPasswordController {

	private UserService userService;
	private MessageSource messageSource;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Model resetPassword(@RequestParam(value = Constants.USERPARAM, required = true) String username, Model model) {
		model.addAttribute("user", userService.findByUsername(username));
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String resetPassword1(@Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Constants.USERHOME + Constants.USERRESETPASSWORD;
		}

		if (!user.getPassword().equals(user.getConfirmPassword())) {
			bindingResult.addError(new FieldError("user", "confirmPassword",
					messageSource.getMessage("user.passwordDoNotMatch", null,
							null)));
			return Constants.USERHOME + Constants.USERRESETPASSWORD;
		}
		
		try {
			userService.resetPassword(user.getUsername(), user.getPassword());
		} catch (UserException e) {
			e.printStackTrace();
			return "redirect:/" + Constants.USERHOME + "/" + Constants.USERSEARCH;
		}

		return "redirect:/" + Constants.USERHOME + "/" + Constants.USERSEARCH;
	}
}
