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
import com.coordsafe.core.codetable.service.CodeTableService;
import com.coordsafe.core.rbac.entity.Resource;
import com.coordsafe.core.rbac.exception.ResourceException;
import com.coordsafe.core.rbac.service.ResourceService;

@Controller
@RequestMapping("/xx" + Constants.RESOURCEHOME + Constants.RESOURCEEDIT)
public class ResourceEditController {

	ResourceService resourceService;
	MessageSource messageSource;
	@Autowired
	CodeTableService codeTableService;

	@Autowired
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
	
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Model editResource(@RequestParam(value = Constants.RESOURCEPARAM, required = true) String name, Model model) {
		model.addAttribute("resource", resourceService.findByName(name));
		model.addAttribute("resourceType", codeTableService.findByType("RESOURCE_TYPE"));
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String updateResource(@Valid Resource resource, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Constants.RESOURCEHOME + Constants.RESOURCEEDIT;
		}
		
		try {
			resourceService.update(resource);
		} catch (ResourceException e) {
			e.printStackTrace();
			bindingResult.addError(new FieldError("resource", "name", messageSource
					.getMessage("resource.nameExists", null, null)));
			return Constants.RESOURCEHOME + Constants.RESOURCEEDIT;
		}
		
		return "redirect:/" + Constants.RESOURCEHOME + Constants.RESOURCESEARCH;
	}
}
