package com.coordsafe.core.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.rbac.exception.ResourceException;
import com.coordsafe.core.rbac.service.ResourceService;

@Controller
@RequestMapping("/" + Constants.RESOURCEHOME + Constants.RESOURCEDELETE)
public class ResourceDeleteController {

	ResourceService resourceService;
	
	@Autowired
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Model deleteRole(@RequestParam(value = Constants.RESOURCEPARAM, required = true) String name, Model model) {
		model.addAttribute("name", name);
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String deleteRole1(@RequestParam(value = Constants.RESOURCEPARAM, required = true) String name) {
		try {
			resourceService.delete(name);
		} catch (ResourceException e) {
			e.printStackTrace();
			return "redirect:/" + Constants.RESOURCEHOME + Constants.RESOURCESEARCH;
		}
		
		return "redirect:/" + Constants.RESOURCEHOME + Constants.RESOURCESEARCH;
	}
}
