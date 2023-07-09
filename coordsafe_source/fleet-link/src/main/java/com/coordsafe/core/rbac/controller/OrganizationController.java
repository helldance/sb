package com.coordsafe.core.rbac.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.rbac.editor.OrganizationEditor;
import com.coordsafe.core.rbac.entity.Organization;
import com.coordsafe.core.rbac.exception.OrganizationException;
import com.coordsafe.core.rbac.service.OrganizationService;

@Controller
@RequestMapping("/" + Constants.ORGANIZATIONHOME)
public class OrganizationController {

	private OrganizationService organizationService;
	private MessageSource messageSource;

	@Autowired
	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Organization.class, new OrganizationEditor(
				organizationService));
	}

	@ModelAttribute("organizations")
	public List<Organization> populateOrganization() {
		return organizationService.findAll();
	}

	@RequestMapping(value = Constants.ORGANIZATIONSEARCH, method = RequestMethod.GET)
	public ModelAndView searchOrganization() {
		ModelAndView mav = new ModelAndView();
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, params = "new")
	public String createOrganization(Model model) {
		model.addAttribute(new Organization());
		return Constants.ORGANIZATIONHOME + Constants.ORGANIZATIONCREATE;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addOrganization(@Valid Organization organization,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Constants.ORGANIZATIONHOME
					+ Constants.ORGANIZATIONCREATE;
		}
		try {
			organizationService.create(organization);
		} catch (OrganizationException e) {
			e.printStackTrace();
			bindingResult.addError(new FieldError("organization", "name",
					messageSource.getMessage("organization.nameExists", null,
							null)));
			return Constants.ORGANIZATIONHOME
					+ Constants.ORGANIZATIONCREATE;
		}
		return "redirect:/" + Constants.ORGANIZATIONHOME
				+ Constants.ORGANIZATIONSEARCH;
	}
}
