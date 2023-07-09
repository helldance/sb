package com.coordsafe.core.rbac.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.rbac.decorators.UserJoinOrganizationCheckboxTableDecorator;
import com.coordsafe.core.rbac.entity.Organization;
import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.exception.UserException;
import com.coordsafe.core.rbac.service.OrganizationService;
import com.coordsafe.core.rbac.service.UserService;

@Controller
@RequestMapping("/" + Constants.USERHOME + Constants.USERJOINORGANIZATION)
public class UserJoinOrganizationController {

	private UserService userService;
	private OrganizationService organizationService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	@ModelAttribute("organizations")
	public List<Organization> populateOrganization() {
		return organizationService.findAll();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView joinOrganization(
			@RequestParam(value = Constants.USERPARAM, required = true) String username,
			ModelAndView mav) {
		mav.addObject("user", userService.findByUsername(username));
		
		UserJoinOrganizationCheckboxTableDecorator decorator = new UserJoinOrganizationCheckboxTableDecorator();
		decorator.setId("name");
		decorator.setFieldName("user");
		mav.addObject("userJoinOrganizationCheckboxTableDecorator", decorator);
		
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String addRoles(User userOld, HttpServletRequest request) {
		String[] currentOrganization = request.getParameterValues("_chk");
		Set<Organization> organizations = new HashSet<Organization>();
		User user = userService.findByUsername(userOld.getUsername());
		
		if (currentOrganization != null) {
			for (int i = 0; i < currentOrganization.length; i++) {
				organizations.add(organizationService.findByName(currentOrganization[i]));
			}
		} else {
			organizations.clear();
		}
		
		user.setOrganizations(organizations);
		try {
			userService.updateUser(user);
		} catch (UserException e) {
			e.printStackTrace();
			return "redirect:/" + Constants.USERHOME + Constants.USERSEARCH;
		}
		
		return "redirect:/" + Constants.USERHOME + Constants.USERSEARCH;
	}
}
