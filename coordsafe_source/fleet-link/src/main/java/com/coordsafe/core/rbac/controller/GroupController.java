package com.coordsafe.core.rbac.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.rbac.entity.Group;
import com.coordsafe.core.rbac.exception.GroupException;
import com.coordsafe.core.rbac.service.GroupService;

@Controller
@RequestMapping("/" + Constants.GROUPHOME)
public class GroupController {

	GroupService groupService;
	MessageSource messageSource;
	
	@Autowired
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@ModelAttribute("groups")
	public List<Group> populateGroup() {
		return groupService.findAll();
	}
	
	@RequestMapping(value = Constants.GROUPSEARCH, method = RequestMethod.GET)
	public ModelAndView searchGroup(ModelAndView mav) {
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET, params = Constants.CREATEPARAM)
	public String createGroup(Model model) {
		model.addAttribute(new Group());
		
		return Constants.GROUPHOME + Constants.GROUPCREATE;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String addGroup(@Valid Group group, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Constants.GROUPHOME + Constants.GROUPCREATE;
		}
		
		try {
			groupService.addGroup(group);
		} catch (GroupException e) {
			e.printStackTrace();
			bindingResult.addError(new FieldError("group", "name", messageSource.getMessage("group.nameExists", null, null)));
			return Constants.GROUPHOME + Constants.GROUPCREATE;
		}
		
		return "redirect:/" + Constants.GROUPHOME + Constants.GROUPSEARCH;
	}
}
