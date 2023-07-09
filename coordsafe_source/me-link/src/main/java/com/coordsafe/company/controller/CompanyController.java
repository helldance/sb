package com.coordsafe.company.controller;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.coordsafe.constants.Constants;
import com.coordsafe.company.entity.Company;
import com.coordsafe.company.service.CompanyService;
import com.coordsafe.core.rbac.entity.Organization;
import com.coordsafe.core.rbac.exception.OrganizationException;

import org.slf4j.*;


@Controller
@RequestMapping(value="/company")
public class CompanyController {
	private static final Logger log = LoggerFactory.getLogger(CompanyController.class);
	@Autowired
	private CompanyService companyService;
	@Autowired
	private MessageSource messageSource;

	
	@ModelAttribute("companys")
	@RequestMapping(value="/search")
	public List<Company> populateCompanys() {
		return companyService.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, params = "new")
	public String createCompany(Model model) {
		log.info("Creating a new Company in Get Request Method...");
		model.addAttribute(new Company());
		return "company/create";
	}
	
	@RequestMapping(method = RequestMethod.POST,value="/create")
	public String addCompany(@Valid Company company,
			BindingResult bindingResult) {
		log.info("Adding a new Comapny in Post Request Method...");
/*		if (bindingResult.hasErrors()) {
			return "company/create";
		}*/
		try {
			companyService.create(company);
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.addError(new FieldError("company", "name",
					messageSource.getMessage("company.nameExists", null,
							null)));
			return "company/create";
		}
		return "redirect:/" +"company/search";
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/edit")
	public Model editCompany(@RequestParam(value = "name", required = true) String name, Model model) {
		model.addAttribute("company", companyService.findByName(name));

		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST,value="/edit")
	public String updateCompany(@Valid Company company,
			BindingResult bindingResult) {
/*		if (bindingResult.hasErrors()) {
			return "company/edit";
		}*/

		try {
			companyService.update(company);
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.addError(new FieldError("company", "name",
					messageSource.getMessage("company.nameExists", null,
							null)));
			return "company/edit";
		}

		return "redirect:/" + "company/search";
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/delete")
	public Model deleteCompany(@RequestParam(value = "name", required = true) String name, Model model) {
		
		model.addAttribute("name", name);
		
		return model;
	}
	@RequestMapping(method = RequestMethod.POST,value="/delete")
	public String deleteCompany1(@RequestParam(value = "name", required = true) String name) {
		
		try {
			companyService.delete(name);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/" + "company/search";
		}
		
		return "redirect:/" +  "company/search";
	}

}
