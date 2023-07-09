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
import com.coordsafe.core.rbac.entity.Group;
import com.coordsafe.core.rbac.exception.GroupException;
import com.coordsafe.core.rbac.service.GroupService;

@Controller
@RequestMapping("/" + Constants.GROUPHOME + Constants.GROUPEDIT)
public class GroupEditController {

	private GroupService groupService;
	private MessageSource messageSource;
	
	@Autowired
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Model editGroup(@RequestParam(value = Constants.GROUPPARAM, required = true) String name, Model model) {
		model.addAttribute("group", groupService.findByName(name));
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String updateGroup(@Valid Group group, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Constants.GROUPHOME + Constants.GROUPEDIT;
		}
		
		try {
			groupService.updateGroup(group);
		} catch (GroupException e) {
			e.printStackTrace();
			bindingResult.addError(new FieldError("group", "name", messageSource.getMessage("group.nameExists", null, null)));
			return Constants.GROUPHOME + Constants.GROUPEDIT;
		}
		
		return "redirect:/" + Constants.GROUPHOME + Constants.GROUPSEARCH;
	}
}
