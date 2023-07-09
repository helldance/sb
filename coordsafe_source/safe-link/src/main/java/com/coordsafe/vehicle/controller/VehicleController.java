package com.coordsafe.vehicle.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.coordsafe.company.service.CompanyService;
import com.coordsafe.constants.Constants;
import com.coordsafe.core.codetable.service.CodeTableService;
import com.coordsafe.exception.CsStatusCode;
import com.coordsafe.exception.kinds.CoordSafeResponse;
import com.coordsafe.locator.entity.Locator;
import com.coordsafe.locator.service.LocatorService;
import com.coordsafe.vehicle.entity.Vehicle;
import com.coordsafe.vehicle.entity.VehicleStatus;
import com.coordsafe.vehicle.service.VehicleGroupService;
import com.coordsafe.vehicle.service.VehicleService;

@Controller
@RequestMapping("/vehicle")
@SessionAttributes("vehicle")
public class VehicleController {
	private static final Logger log = LoggerFactory.getLogger(VehicleController.class);

	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private VehicleGroupService vehicleGroupService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private LocatorService locatorService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private CodeTableService codetableService;
	
	@ModelAttribute("vehicles")
	@RequestMapping(value="/search")
	public List<Vehicle> populateVehicles(@RequestParam String companyid) {
		
		return vehicleService.findVehiclesByCompany(companyid);
		//return vehicleService.findAll();
	}
	
	@RequestMapping(value="/nameList")
	public @ResponseBody List<Map<String, String>> getVehicleNames(@RequestParam String companyid) {
		
		List<Vehicle> vcs = vehicleService.findVehiclesByCompany(companyid);
		List<Map<String, String>> names = new ArrayList<Map<String, String>>();
		
		for (Vehicle v : vcs){
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", v.getName());
			map.put("license", v.getLicensePlate());
			
			names.add(map);
		}
		
		return names;
	}

	/*!important: used for serving map page, i.e., home.jsp*/
	@RequestMapping(method = RequestMethod.GET, value = "/company/{companyId}")
	public @ResponseBody String getVehicleByCompany (@PathVariable("companyId") String companyId){
		
		return vehicleService.findMinimalVehicleByCompany(companyId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/new")
	public String createVehicle(Model model, @RequestParam String companyid) {
		log.info("Creating a new Vehicle in Get Request Method...");
		Vehicle vehicle = new Vehicle();
		vehicle.setCompany(companyService.findById(new Long(companyid)));
		model.addAttribute("vehicle",vehicle);
		model.addAttribute("vehicleGroups",vehicleGroupService.findVehicleGroupsByCompany(companyid));
		model.addAttribute("vehicleType",codetableService.findByType(Constants.VEHICLE_TYPE));
		
		return "vehicle/create";
	}
	
	@RequestMapping(method = RequestMethod.POST,value="/create")
	public @ResponseBody CoordSafeResponse addVehicle(@Valid Vehicle vehicle2, BindingResult bindingResult, 
			HttpSession session, HttpServletResponse response) {
		log.info("Adding a new Vehicle in Post Request Method...");
				
		if (bindingResult.hasErrors()) {
			//return "vehicle/create";
		}
		
		Vehicle vehicle = (Vehicle)session.getAttribute("vehicle");

		vehicle.setName(vehicle2.getName());
		vehicle.setModel(vehicle2.getModel());
		vehicle.setType(vehicle2.getType());
		vehicle.setVehiclegroup(vehicle2.getVehiclegroup());
		// by default vehicle is stopped
		vehicle.setStatus(VehicleStatus.STOPPED);
		
		/*Vehicle vehicle = new Vehicle(vehicle2.getName(), vehicle2.getModel(), vehicle2.getType(), 
				vehicle2.getStatus(), vehicle2.getLicensePlate(), vehicle2.getVehiclegroup(), vehicle2.getCompany());*/
		try {
			vehicleService.persist(vehicle);
			//return "redirect:/" + "vehicle/search?companyid=" + vehicle.getCompany().getId();
			
			CoordSafeResponse result = new CoordSafeResponse(CsStatusCode.SUCCESS, String.format("Vehicle %s is created", vehicle2.getName()));
			
			return result;

		} catch (Exception e) {
			log.error("error create vehicle: ");
			//e.printStackTrace();
			
			if (e instanceof ConstraintViolationException){
				ConstraintViolationException cve = (ConstraintViolationException)e;
				log.error("constraint: "  + cve.getConstraintName());
			}
			
			//String [] args = {vehicle2.getName()};
			
			bindingResult.addError(new FieldError("vehicle", "name",
					messageSource.getMessage("vehicle.nameExists", null, null)));
			//bindingResult.rejectValue("name", "", messageSource.getMessage("vehicle.nameExists", null, null));
			
			/*try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			//model.addAttribute("result", "Failed to create vehicle, see error message for details");
			
			log.error("bindingresult: " + bindingResult.getFieldErrors());
			
			//return "vehicle/create";
			
			CoordSafeResponse result = new CoordSafeResponse(CsStatusCode.FAIL, String.format("Failed to create vehicle %s : %s", vehicle2.getName(), 
					messageSource.getMessage("vehicle.nameExists", null, null)));

			return result;
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/edit")
	public Model editVehicle(@RequestParam(value = "name", required = true) String vehiclename, Model model) {
		log.info("Vehicle Editing...");
		Vehicle vehicle = vehicleService.findVehicleByName(vehiclename);
		model.addAttribute("vehicle", vehicle);
		model.addAttribute("vehicleGroups",vehicleGroupService.findVehicleGroupsByCompany(vehicle.getCompany().getId().toString()));
		model.addAttribute("vehicleType",codetableService.findByType(Constants.VEHICLE_TYPE));
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST,value="/edit")
	public @ResponseBody CoordSafeResponse updateVehicle(@Valid @ModelAttribute Vehicle vehicle2,
			BindingResult bindingResult,HttpSession session) {
		/*if (bindingResult.hasErrors()) {
			return "vehicle/edit";
		}*/
		
		Vehicle vehicle = (Vehicle)session.getAttribute("vehicle");
		CoordSafeResponse result = null;
		
		try {
			vehicle.setName(vehicle2.getName());
			vehicle.setModel(vehicle2.getModel());
			vehicle.setType(vehicle2.getType());
			vehicleService.update(vehicle);
			
			result = new CoordSafeResponse(CsStatusCode.SUCCESS, String.format("Vehicle %s is updated", vehicle2.getName()));
		} catch (Exception e) {
			log.error("error update vehicle: ");
			e.printStackTrace();
			
			//String [] args = {vehicle2.getName()};
			
			bindingResult.addError(new FieldError("vehicle", "name",
					messageSource.getMessage("vehicle.nameExists", null, null)));
			
			result = new CoordSafeResponse(CsStatusCode.FAIL, String.format("Failed to update vehicle %s", vehicle2.getName()));
			
			//return "vehicle/edit";
		}

		//return "redirect:/" + "vehicle/search?companyid=" + vehicle.getCompany().getId();
		return result;
	}	
	
	@RequestMapping(method = RequestMethod.GET,value="/delete")
	public Model deleteVehicle(@RequestParam(value = "name", required = true) String name, Model model) {
		
		log.info("Vehicle Editing...");
		model.addAttribute("vehicle", vehicleService.findVehicleByName(name));
		model.addAttribute("name", name);
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST,value="/delete")
	public String deleteVehicle1(@RequestParam(value = "name", required = true) String name, HttpSession session) {
		Vehicle vehicle = (Vehicle)session.getAttribute("vehicle");
		Locator locator = vehicle.getLocator();
		try {
			/*if(locator != null){
				locator.setVehicle(null) ;
				locatorService.updateLocator(locator);				
			}*/

			vehicleService.delete(name);
		} catch (Exception e) {
			log.error("Error: ");
			e.printStackTrace();
					
			return "redirect:/" + "web/error";
		}
		
		return "redirect:/" + "vehicle/search?companyid=" + vehicle.getCompany().getId();
	}
}
