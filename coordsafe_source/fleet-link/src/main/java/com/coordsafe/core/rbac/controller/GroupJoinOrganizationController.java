package com.coordsafe.core.rbac.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.rbac.decorators.GroupJoinOrganizationCheckboxTableDecorator;
import com.coordsafe.core.rbac.entity.Group;
import com.coordsafe.core.rbac.entity.Organization;
import com.coordsafe.core.rbac.exception.GroupException;
import com.coordsafe.core.rbac.service.GroupService;
import com.coordsafe.core.rbac.service.OrganizationService;

@Controller
@RequestMapping("/" + Constants.GROUPHOME + Constants.GROUPJOINORGANIZATION)
public class GroupJoinOrganizationController {

	private GroupService groupService;
	private OrganizationService organizationService;

	@Autowired
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	@Autowired
	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	@ModelAttribute("organizations")
	public List<Organization> populateOrganization() {
		return organizationService.findAll();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView joinOrganization(
			@RequestParam(value = Constants.GROUPPARAM, required = true) String name,
			ModelAndView mav) {
		mav.addObject("group", groupService.findByName(name));
		
		GroupJoinOrganizationCheckboxTableDecorator decorator = new GroupJoinOrganizationCheckboxTableDecorator();
		decorator.setId("name");
		decorator.setFieldName("group");
		mav.addObject("groupJoinOrganizationCheckboxTableDecorator", decorator);
		
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String addRoles(Group groupOld, HttpServletRequest request) {
		String[] currentOrganization = request.getParameterValues("_chk");
		Set<Organization> organizations = new HashSet<Organization>();
		Group group = groupService.findByName(groupOld.getName());
		
		if (currentOrganization != null) {
			for (int i = 0; i < currentOrganization.length; i++) {
				organizations.add(organizationService.findByName(currentOrganization[i]));
			}
		} else {
			organizations.clear();
		}
		
		group.setOrganizations(organizations);
		try {
			groupService.updateGroup(group);
		} catch (GroupException e) {
			e.printStackTrace();
			return "redirect:/" + Constants.GROUPHOME + Constants.GROUPSEARCH;
		}
		
		return "redirect:/" + Constants.GROUPHOME + Constants.GROUPSEARCH;
	}
}
