package com.coordsafe.locator.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.coordsafe.company.service.CompanyService;
import com.coordsafe.constants.Constants;
import com.coordsafe.core.codetable.editor.CodeTableTypeEditor;
import com.coordsafe.core.codetable.entity.CodeTable;
import com.coordsafe.core.codetable.service.CodeTableService;

import com.coordsafe.locator.entity.Locator;
import com.coordsafe.locator.service.LocatorService;
import com.coordsafe.vehicle.entity.Vehicle;
import com.coordsafe.vehicle.service.VehicleService;
import org.slf4j.*;
@Controller
@RequestMapping("/" + Constants.LOCATORHOME)
@SessionAttributes("locator")
public class LocatorController {
	private Logger logger = LoggerFactory.getLogger(LocatorController.class);
	
	@Autowired
	private LocatorService locatorService;
	@Autowired
	private CodeTableService codeTableService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private CompanyService companyService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(CodeTable.class, new CodeTableTypeEditor());
	}

	@RequestMapping(method = RequestMethod.GET, params = Constants.CREATEPARAM)
	public String createWard(Model model) {
		model.addAttribute(new Locator());
		model.addAttribute("madeByType",
				codeTableService.findByType(Constants.MANUFACTURER));
		model.addAttribute("modelType",
				codeTableService.findByType(Constants.MODEL));
		model.addAttribute("company",companyService.findAll());

		return Constants.LOCATORHOME + Constants.CREATE;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addLocator(@Valid Locator locator,
			BindingResult bindingResult, HttpServletRequest request, Model model) {
		// model.addAttribute("resourceType",
		// codeTableService.findByType("RESOURCE_TYPE"));

		if (bindingResult.hasErrors()) {
			return Constants.LOCATORHOME + Constants.CREATE;
		} else {
			locatorService.createLocator(locator);
		}

		return "redirect:/" + Constants.LOCATORHOME + Constants.SEARCH;

	}

	@RequestMapping(value = Constants.SEARCH, method = RequestMethod.GET)
	public Model searchLocator(HttpServletRequest request, Model model) {

		//String loginName = request.getUserPrincipal().getName();
		//User user = userService.findByUsername(loginName);
		model.addAttribute("locatorList", locatorService.findAllLocators());
		model.addAttribute("vehicleList", vehicleService.findAll());
		model.addAttribute("companyList", companyService.findAll());
		
		return model;
	}

	@RequestMapping(value = Constants.EDIT, method = RequestMethod.GET)
	public Model editLocator(@RequestParam(value = "imeiCode", required = true) String imeiCode, Model model) {

		model.addAttribute("locator", locatorService.findLocatorByImei(imeiCode) );
		model.addAttribute("madeByType", codeTableService.findByType(Constants.MANUFACTURER));
		model.addAttribute("modelType", codeTableService.findByType(Constants.MODEL));
		model.addAttribute("company", companyService.findAll());
		
		return model;
		//return "redirect:/" + Constants.LOCATORHOME + Constants.SEARCH;

	}
	
	@RequestMapping(value = Constants.EDIT, method = RequestMethod.POST)
	public String updateLocator(@Valid Locator locator,	BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return Constants.LOCATORHOME + Constants.EDIT;
		}
		
		Locator loc = locatorService.findLocatorById(locator.getId());
		//loc = locator;
		loc.setMadeBy(locator.getMadeBy());
		loc.setModel(locator.getModel());
		loc.setAssignedTo(locator.getAssignedTo());
		loc.setLabel(locator.getLabel());
		
		try {
			locatorService.updateLocator(loc);
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.addError(new FieldError("locator", "name", messageSource.getMessage("locator.imeiExists", null, null)));
			return Constants.LOCATORHOME + Constants.SEARCH;
		}

		return "redirect:/" + Constants.LOCATORHOME	+ Constants.SEARCH;
	}
	

	@RequestMapping(value = Constants.DELETE, method = RequestMethod.GET)
	public String deleteLocator(
			@RequestParam(value = "imeiCode", required = true) String imeiCode) {

		locatorService.deleteLocator(imeiCode);
		return "redirect:/" + Constants.LOCATORHOME + Constants.SEARCH;

	}

	@RequestMapping(value = "locate", method = RequestMethod.GET)
	public String locate(@RequestParam(value = "imeiCode", required = true) String imeiCode) {

		return "redirect:/" + Constants.LOCATORHOME + Constants.LOCATE + "?imeiCode=" + imeiCode;

	}
	
	@RequestMapping(value = "assign", method = RequestMethod.GET)
	public String assignLocate(@RequestParam String imeiCode,Model model) {
		logger.info("In the assign locator Get method..." + imeiCode);
		
		model.addAttribute("locator", locatorService.findLocatorByImei(imeiCode));
		model.addAttribute("vehicles", vehicleService.findAllNoLocator());
		
		return Constants.LOCATORHOME + "/assign";

	}
	
	@RequestMapping(value = "assign", method = RequestMethod.POST)
	public String assignLocate(@RequestParam(value = "id", required = true) String locatorID,
			@RequestParam(value = "assignedTo", required = true) String vehicleID, 
			@RequestParam(value="unassign", required = false) boolean unassign, HttpSession session) {
		
		logger.info("The Locator=" + locatorID +" to vehicle" + vehicleID);
		
		Vehicle vehicle = vehicleService.findVehicleById(new Long(vehicleID));
		Locator locatorUpdate = (Locator)session.getAttribute("locator");
		
		// remove prev assingation
		Vehicle oldVehilce = vehicleService.findVehicleByLocatorId(Long.parseLong(locatorID));
		
		if( oldVehilce != null){
			
			oldVehilce.setLocator(null);
			vehicleService.update(oldVehilce);
		}
		
		if (unassign){
			vehicle.setLocator(null);
		}
		else{
			vehicle.setLocator(locatorUpdate);
		}
		//locatorUpdate.setVehicle(vehicle);
		
		vehicleService.update(vehicle);
		//locatorService.updateLocator(locatorUpdate);
		return "redirect:/" + Constants.LOCATORHOME + Constants.SEARCH;
	}
}