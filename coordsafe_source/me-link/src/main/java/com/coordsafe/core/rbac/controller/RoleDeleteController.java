package com.coordsafe.core.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.rbac.exception.RoleException;
import com.coordsafe.core.rbac.service.RoleService;

@Controller
@RequestMapping("/" + Constants.ROLEHOME + Constants.ROLEDELETE)
public class RoleDeleteController {
	
	RoleService roleService;
	
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Model deleteRole(@RequestParam(value = Constants.ROLEPARAM, required = true) String name, Model model) {
		
		model.addAttribute("name", name);
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String deleteRole1(@RequestParam(value = Constants.ROLEPARAM, required = true) String name) {
		
		try {
			roleService.removeRole(name);
		} catch (RoleException e) {
			e.printStackTrace();
			return "redirect:/" + Constants.ROLEHOME + Constants.ROLESEARCH;
		}
		
		return "redirect:/" + Constants.ROLEHOME + Constants.ROLESEARCH;
	}
}
