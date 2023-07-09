package com.coordsafe.core.rbac.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.codetable.editor.CodeTableTypeEditor;
import com.coordsafe.core.codetable.entity.CodeTable;
import com.coordsafe.core.codetable.service.CodeTableService;
import com.coordsafe.core.rbac.entity.Role;
import com.coordsafe.core.rbac.exception.RoleException;
import com.coordsafe.core.rbac.service.RoleService;

@Controller
@RequestMapping("/" + Constants.ROLEHOME + Constants.ROLEEDIT)
public class RoleEditController {

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
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(CodeTable.class, new CodeTableTypeEditor());
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Model editRole(@RequestParam(value = Constants.ROLEPARAM, required = true) String name, Model model) {
		model.addAttribute("role", roleService.findByRoleName(name));
		model.addAttribute("roleType", codeTableService.findByType("ROLE_TYPE"));
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String updateRole(@Valid Role role, BindingResult bindingResult, Model model) {
		model.addAttribute("roleType", codeTableService.findByType("ROLE_TYPE"));
		if (bindingResult.hasErrors()) {
			return Constants.ROLEHOME + Constants.ROLEEDIT;
		}
		
		try {
			roleService.updateRole(role);
		} catch (RoleException e) {
			e.printStackTrace();
			bindingResult.addError(new FieldError("role", "name", messageSource
					.getMessage("role.nameExists", null, null)));
			return Constants.ROLEHOME + Constants.ROLEEDIT;
		}
		
		return "redirect:/" + Constants.ROLEHOME + Constants.ROLESEARCH;
	}
}
