package com.coordsafe.core.rbac.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coordsafe.company.entity.Company;
import com.coordsafe.company.service.CompanyService;
import com.coordsafe.constants.Constants;
import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.entity.UserInformation;
import com.coordsafe.core.rbac.exception.UserException;
import com.coordsafe.core.rbac.service.UserService;
import com.coordsafe.exception.CsStatusCode;
import com.coordsafe.exception.kinds.CoordSafeResponse;

@Controller
@RequestMapping("/" + Constants.USERHOME)
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	private UserService userService;
	
	@Autowired
	private CompanyService companyService;

	private MessageSource messageSource;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/profile/{username}")
	public String viewProfiel(@PathVariable String username, Model model) {
		model.addAttribute("userinfo", userService.findByUsername(username));
		
		return Constants.USERHOME + "/profile";
	}

	@RequestMapping(method = RequestMethod.GET, params = "new")
	public String createUser(Model model) {
		model.addAttribute(new User());
		model.addAttribute("companys",companyService.findAll());
		return Constants.USERHOME + Constants.USERCREATE;
	}

	@RequestMapping(method = RequestMethod.POST, params = "new")
	public @ResponseBody CoordSafeResponse addUser(@Valid @ModelAttribute User user, BindingResult bindingResult) {
		//log.info("In the create user post method..." + user.getCompanyName());
		CoordSafeResponse result;
		String uname = user.getUsername();
		/*if (bindingResult.hasErrors()) {
			log.info("binding has error" + bindingResult.toString());
			
			return Constants.USERHOME + Constants.USERCREATE;
		}
		if(user.getCompany().getName().equalsIgnoreCase("NONE")){
			bindingResult.addError(new FieldError("user", "company",
					messageSource.getMessage("user.company", null,
							null)));
			
			log.info("binding user");
			return Constants.USERHOME + Constants.USERCREATE;
		}*/
		Company companySelected = companyService.findById(new Long(user.getCompanyName()));
		user.setCompany(companySelected);
		//user.setCompanyName(companySelected.getName());
		
		/*if (!user.getPassword().equals(user.getConfirmPassword())) {
			bindingResult.addError(new FieldError("user", "confirmPassword",
					messageSource.getMessage("user.passwordDoNotMatch", null,
							null)));
			return Constants.USERHOME + Constants.USERCREATE;
		}*/

		try {
			userService.addUser(user);
			
			result = new CoordSafeResponse(CsStatusCode.SUCCESS, String.format("User %s is created", uname));
			
			log.info("created user " + uname);
		} catch (UserException e) {
			e.printStackTrace();
			bindingResult
					.addError(new FieldError("user", "username", messageSource
							.getMessage("user.usernameExists", null, null)));
			//return Constants.USERHOME + Constants.USERCREATE;
			
			//TODO fail reason?
			result = new CoordSafeResponse(CsStatusCode.FAIL, String.format("Failed to create user %s: %s", uname, e.getMessage()));
		}

		//return "redirect:/" + Constants.USERHOME + Constants.USERSEARCH;
		return result;
	}

	@RequestMapping(value = Constants.USERSEARCH, method = RequestMethod.GET)
	public Model searchUser(Model model) {
		model.addAttribute("userList", userService.findAll());
		return model;
	}

	@RequestMapping(method = RequestMethod.GET, value = Constants.USERDISABLE)
	public String disableUser(
			@RequestParam(value = Constants.USERPARAM, required = true) String username) {
		log.info("disable user: " + username);
		
		userService.disableUser(username);
		
		return "redirect:/" + Constants.USERHOME + Constants.USERSEARCH;
	}

	@RequestMapping(method = RequestMethod.GET, value = Constants.USERENABLE)
	public String enableUser(
			@RequestParam(value = Constants.USERPARAM, required = true) String username) {
		log.info("enable user: " + username);
		
		userService.enableUser(username);
		
		return "redirect:/" + Constants.USERHOME + Constants.USERSEARCH;
	}
	
	/*
	 * Sample for restful service and method control.
	 * go to /user/{username}[.xml|.json]
	 */
	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public void getUser(@PathVariable String username, Model model) {
		model.addAttribute(userService.findByUsername(username));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/edit")
	public Model editUser(@RequestParam(value = Constants.USERPARAM, required = true) String username, Model model) {
		model.addAttribute("companys",companyService.findAll());
		model.addAttribute("user", userService.findByUsername(username));
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/edit")
	public String updateUser(@Valid User user, BindingResult bindingResult, Model model) {
		log.info("update user profile: " + user.getUsername());
		
		if (bindingResult.hasErrors()) {
			//TODO - add css error to front end instead of returning below
			return Constants.USERHOME + Constants.USEREDIT;
		}		
		
		User user1 = userService.findById(user.getId());
		
		UserInformation ui = user1.getUserInformation();
		
		if (ui == null){
			ui = new UserInformation();
		}

		ui.setNickName(user.getUserInformation().getNickName());
		
		user1.setUserInformation(ui);
		user1.setEmail(user.getEmail());
		user1.setContact(user.getContact());
		
		try {
			userService.updateUser(user1);
		} catch (UserException e) {
			e.printStackTrace();
			bindingResult.addError(new FieldError("user", "username", messageSource
							.getMessage("user.usernameExists", null, null)));
			return Constants.USERHOME + Constants.USEREDIT;
		}

		// TODO
		model.addAttribute("userinfo", userService.findByUsername(user.getUsername()));
		
		return "user/profile";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/edit/{username}")
	public String editUser(@Valid @ModelAttribute User user, BindingResult bindingResult) {
		log.info("edit user: " + user.getUsername());
		
		/*if (bindingResult.hasErrors()) {
			//TODO - add css error to front end instead of returning below
			log.error("bindingResule has errors" + bindingResult.toString());
			
			return Constants.USERHOME + Constants.USEREDIT;
		}*/		
		
		User user1 = userService.findById(user.getId());
		
		user1.setUsername(user.getUsername());
		user1.setEmail(user.getEmail());
		user1.setContact(user.getContact());
		Company companySelected = companyService.findById(new Long(user.getCompanyName()));
		user1.setCompany(companySelected);
		user1.setCompanyName(companySelected.getName());
		
		try {
			userService.updateUser(user1);
		} catch (UserException e) {
			e.printStackTrace();
			bindingResult.addError(new FieldError("user", "username", messageSource
							.getMessage("user.usernameExists", null, null)));
			return Constants.USERHOME + Constants.USEREDIT;
		}
		
		return "user/search";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/changePassword")
	public @ResponseBody String changePassword(@Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Constants.USERHOME + "/changePassword";
		}

		if (!user.getPassword().equals(user.getConfirmPassword())) {
			bindingResult.addError(new FieldError("user", "confirmPassword",
					messageSource.getMessage("user.passwordDoNotMatch", null,
							null)));
			return Constants.USERHOME + "/changePassword";
		}
		
		log.info("change password for: " + user.getUsername());
		
		try {
			userService.changePassword(user.getUsername(), user.getOldPassword(), user.getPassword());
			
			log.info("///" + user.getPassword());
		} catch (UserException e) {
			log.error("error change password", e.getStackTrace());
			return "redirect:/" + Constants.USERHOME + "/" + Constants.USERSEARCH;
		}

		return "OK" ;
	}
}
