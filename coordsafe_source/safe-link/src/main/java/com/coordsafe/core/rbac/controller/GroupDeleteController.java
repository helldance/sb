package com.coordsafe.core.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.rbac.exception.GroupException;
import com.coordsafe.core.rbac.service.GroupService;

@Controller
@RequestMapping("/" + Constants.GROUPHOME + Constants.GROUPDELETE)
public class GroupDeleteController {

	GroupService groupService;
	
	@Autowired
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Model deleteUser(@RequestParam(value = Constants.GROUPPARAM, required = true) String name, Model model) {
		
		model.addAttribute("name", name);
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String deleteUser1(@RequestParam(value = Constants.GROUPPARAM, required = true) String name) {
		
		try {
			groupService.deleteGroup(name);
		} catch (GroupException e) {
			e.printStackTrace();
			return "redirect:/" + Constants.GROUPHOME + Constants.GROUPSEARCH;
		}
		
		return "redirect:/" + Constants.GROUPHOME + Constants.GROUPSEARCH;
	}
}
