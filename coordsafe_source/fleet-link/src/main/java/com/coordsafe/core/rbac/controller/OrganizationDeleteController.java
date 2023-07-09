package com.coordsafe.core.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.rbac.exception.OrganizationException;
import com.coordsafe.core.rbac.service.OrganizationService;

@Controller
@RequestMapping("/" + Constants.ORGANIZATIONHOME + Constants.ORGANIZATIONDELETE)
public class OrganizationDeleteController {

	OrganizationService organizationService;
	
	@Autowired
	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Model deleteOrganization(@RequestParam(value = Constants.ORGANIZATIONPARAM, required = true) String name, Model model) {
		
		model.addAttribute("name", name);
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String deleteOrganization1(@RequestParam(value = Constants.ORGANIZATIONPARAM, required = true) String name) {
		
		try {
			organizationService.delete(name);
		} catch (OrganizationException e) {
			e.printStackTrace();
			return "redirect:/" + Constants.ORGANIZATIONHOME + Constants.ORGANIZATIONSEARCH;
		}
		
		return "redirect:/" + Constants.ORGANIZATIONHOME + Constants.ORGANIZATIONSEARCH;
	}
}
