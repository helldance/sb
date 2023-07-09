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
import com.coordsafe.core.rbac.decorators.ResourceSearchTableDecorator;
import com.coordsafe.core.rbac.entity.Resource;
import com.coordsafe.core.rbac.exception.ResourceException;
import com.coordsafe.core.rbac.service.ResourceService;

@Controller
@RequestMapping("/" + Constants.RESOURCEHOME)
public class ResourceController {

	ResourceService resourceService;
	CodeTableService codeTableService;
	MessageSource messageSource;
	
	@Autowired
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
	
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@Autowired
	public void setCodeTableService(CodeTableService codeTableService) {
		this.codeTableService = codeTableService;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(CodeTable.class, new CodeTableTypeEditor());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = Constants.RESOURCESEARCH)
	public Model searchResource(Model model) {
		model.addAttribute("resources", resourceService.findAll());
		model.addAttribute("resourceSearchTableDecorator", new ResourceSearchTableDecorator(codeTableService));
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.GET, params = Constants.CREATEPARAM)
	public String createResource(Model model) {
		model.addAttribute(new Resource());
		model.addAttribute("resourceType", codeTableService.findByType("RESOURCE_TYPE"));
		
		return Constants.RESOURCEHOME + Constants.RESOURCECREATE;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String addResource(@Valid Resource resource, BindingResult bindingResult, Model model) {
		model.addAttribute("resourceType", codeTableService.findByType("RESOURCE_TYPE"));
		
		if (bindingResult.hasErrors()) {
			return Constants.RESOURCEHOME + Constants.RESOURCECREATE;
		}
		
		try {
			resourceService.create(resource);
		} catch (ResourceException e) {
			e.printStackTrace();
			bindingResult.addError(new FieldError("resource", "name", messageSource
					.getMessage("resource.nameExists", null, null)));
			return Constants.RESOURCEHOME + Constants.RESOURCECREATE;
		}
		return "redirect:/" + Constants.RESOURCEHOME + Constants.RESOURCESEARCH;
	}
	
	/*below for edit service*/
	
	@RequestMapping(method = RequestMethod.GET, value = Constants.RESOURCEEDIT)
	public Model editResource(@RequestParam(value = Constants.RESOURCEPARAM, required = true) String name, Model model) {
		model.addAttribute("resource", resourceService.findByName(name));
		model.addAttribute("resourceType", codeTableService.findByType("RESOURCE_TYPE"));
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = Constants.RESOURCEEDIT)
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
