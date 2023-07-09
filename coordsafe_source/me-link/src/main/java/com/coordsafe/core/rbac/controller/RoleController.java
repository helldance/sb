package com.coordsafe.core.rbac.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.codetable.service.CodeTableService;
import com.coordsafe.core.rbac.decorators.RoleSearchTableDecorator;
import com.coordsafe.core.rbac.entity.Role;
import com.coordsafe.core.rbac.exception.RoleException;
import com.coordsafe.core.rbac.service.RoleService;

/**
 * 
 * @author Darren Mok
 *
 */
@Controller
@RequestMapping("/" + Constants.ROLEHOME)
public class RoleController {

	private RoleService roleService;
	private CodeTableService codeTableService;
	private MessageSource messageSource;

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@Autowired
	public void setCodeTableService(CodeTableService codeTableService) {
		this.codeTableService = codeTableService;
	}

	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
 
	@RequestMapping(method = RequestMethod.GET, value = Constants.ROLESEARCH)
	public Model searchRole(Model model) {
		model.addAttribute("roleSearchTableDecorator", new RoleSearchTableDecorator(codeTableService));
		model.addAttribute("roleList", roleService.findAllRoles());
		return model;
	}

	@RequestMapping(method = RequestMethod.GET, params = Constants.CREATEPARAM)
	public String createRole(Model model) {
		model.addAttribute(new Role());
		model.addAttribute("roleType", codeTableService.findByType(Constants.ROLETYPE));
		return Constants.ROLEHOME + Constants.ROLECREATE;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addRole(@Valid Role role, BindingResult bindingResult, Model model) {
		model.addAttribute("roleType", codeTableService.findByType(Constants.ROLETYPE));
		if (bindingResult.hasErrors()) {
			return Constants.ROLEHOME + Constants.ROLECREATE;
		}
		try {
			roleService.addRole(role);
		} catch (RoleException e) {
			e.printStackTrace();
			bindingResult.addError(new FieldError("role", "name", messageSource
					.getMessage("role.nameExists", null, null)));
			return Constants.ROLEHOME + Constants.ROLECREATE;
		}
		return "redirect:/" + Constants.ROLEHOME + Constants.ROLESEARCH;
	}
	
	/*
	 * Sample for restful service and method control.
	 * go to /role/{name}[.xml|.json]
	 */	
	@Secured("ROLE_ADMINISTRATOR")	
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public Model getRole(@PathVariable String name, Model model) {
		model.addAttribute(roleService.findByRoleName(name));
		
		return model;
	}
}
