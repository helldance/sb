package com.coordsafe.vehicle.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
import com.coordsafe.locator.entity.Locator;

import com.coordsafe.vehicle.entity.GroupVehicle;
import com.coordsafe.vehicle.entity.GroupVehicle.SimpleVehicle;
import com.coordsafe.vehicle.entity.GroupVehicle.SimpleVehicleGroup;
import com.coordsafe.vehicle.entity.Vehicle;
import com.coordsafe.vehicle.entity.VehicleGroup;
import com.coordsafe.vehicle.service.VehicleGroupService;
import com.coordsafe.vehicle.service.VehicleService;

import org.slf4j.*;


@Controller
@RequestMapping(value="/vgroup")
@SessionAttributes("vGroup")
public class VehicleGroupController {
	private static final Logger log = LoggerFactory.getLogger(VehicleGroupController.class);
	@Autowired
	private VehicleGroupService vgroupService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private VehicleService vehicleService;

	
	@ModelAttribute("vgroups")
	@RequestMapping(value="/search")
	public List<VehicleGroup> populateVehicleGroups(@RequestParam String companyid) {
		return vgroupService.findVehicleGroupsByCompany(companyid);
	}
	
	/*@RequestMapping(method = RequestMethod.GET, value="/company/{companyId}/list")
	@ResponseBody
	public List<VehicleGroup> fullVehicleGroups(@PathVariable("companyId") String companyid) {
		return vgroupService.findVehicleGroupsByCompany(companyid);
	}*/
	
	@RequestMapping(method = RequestMethod.GET, value="/company/{companyId}")
	@ResponseBody
	public String listVehicleGroups(@PathVariable("companyId") String companyid) {
		List<VehicleGroup> grps = vgroupService.findVehicleGroupsByCompany(companyid);
		List<HashMap<Long, String>> simpleGrp = new ArrayList<HashMap<Long, String>>();
		String grpValue = "";
		
		if (grps != null){
			for (VehicleGroup vg : grps){
				HashMap<Long, String> map = new HashMap<Long, String>();
				map.put(vg.getId(), vg.getGroupName());
				
				simpleGrp.add(map);
			}
			
			ObjectMapper mapper = new ObjectMapper();
			
			try {
				grpValue = mapper.writeValueAsString(simpleGrp);
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return grpValue;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/company/{companyId}/list")
	@ResponseBody
	public String listVehicleGroupsWithVehicle(@PathVariable("companyId") String companyid) {
		List<VehicleGroup> grps = vgroupService.findVehicleGroupsByCompany(companyid);
		
		List<GroupVehicle> groupVehicleList = new ArrayList<GroupVehicle>();
		
		String grpValue = "";
		
		if (grps != null){
			for (VehicleGroup vg : grps){
				GroupVehicle gv = new GroupVehicle(); 
				
				SimpleVehicleGroup svg = gv.new SimpleVehicleGroup();
				svg.groupId = vg.getId();
				svg.groupName = vg.getGroupName();
				
				
				List<Vehicle> vehicles = vehicleService.findVehicleByGroupId(vg.getId());
				
				if (vehicles != null){
					Set<SimpleVehicle> svs = new HashSet<SimpleVehicle>();
					
					for (Vehicle v: vehicles){
						SimpleVehicle sv = gv.new SimpleVehicle();
						sv.vehicleId = v.getId();
						sv.vehicleName = v.getName();
						
						Locator l = v.getLocator();
						
						if (l != null)
							sv.locatorId = v.getLocator().getId();
						
						svs.add(sv);
					}
					
					/*gv.group = svg;
					gv.vehicles = svs;*/
					
					groupVehicleList.add(new GroupVehicle(svg, svs));
				}
			}
			
			ObjectMapper mapper = new ObjectMapper();
			
			try {
				grpValue = mapper.writeValueAsString(groupVehicleList);
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return grpValue;
	}
		
	@RequestMapping(method = RequestMethod.GET, value = "new")
	public String createVehicleGroup(Model model,@RequestParam("companyid") String companyid) {
		log.info("Creating a new VehicleGroup in Get Request Method...");
		VehicleGroup vehicleGroup = new VehicleGroup();
		vehicleGroup.setCompany(companyService.findById(new Long(companyid)));
		model.addAttribute("vGroup",vehicleGroup);
		return "vgroup/create";
	}
	
	@RequestMapping(method = RequestMethod.POST,value="/create")
	public String addVehicleGroup(@Valid VehicleGroup vgroup,
			BindingResult bindingResult,HttpSession session) {
		log.info("Adding a new Comapny in Post Request Method...");
/*		if (bindingResult.hasErrors()) {
			return "vgroup/create";
		}*/
		VehicleGroup vehicleGroup = (VehicleGroup)session.getAttribute("vGroup");
		vgroup.setCompany(vehicleGroup.getCompany());
		try {
			vgroupService.create(vgroup);
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.addError(new FieldError("vgroup", "name",
					messageSource.getMessage("vgroup.nameExists", null,
							null)));
			return "vgroup/create";
		}
		return "redirect:/" +"vgroup/search?companyid=" + vehicleGroup.getCompany().getId();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/edit")
	public Model editVehicleGroup(@RequestParam(value = "name", required = true) String name, Model model) {
		model.addAttribute("vGroup", vgroupService.findByName(name));

		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST,value="/edit")
	public String updateVehicleGroup(@Valid VehicleGroup vgroup,
			BindingResult bindingResult,HttpSession session) {
/*		if (bindingResult.hasErrors()) {
			return "vgroup/edit";
		}*/
		VehicleGroup vGroup1 = (VehicleGroup)session.getAttribute("vGroup");
		
		try {
			vGroup1.setGroupName(vgroup.getGroupName());
			vGroup1.setDescription(vgroup.getDescription());
			vgroupService.update(vGroup1);
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.addError(new FieldError("vgroup", "name",
					messageSource.getMessage("vgroup.nameExists", null,
							null)));
			return "vgroup/edit";
		}

		return "redirect:/" + "vgroup/search?companyid=" + vGroup1.getCompany().getId() ;
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/delete")
	public Model deleteVehicleGroup(@RequestParam(value = "name", required = true) String name, Model model) {
		
		VehicleGroup vGroup = vgroupService.findByName(name);
		if((vehicleService.findVehicleByGroupId(vGroup.getId())).isEmpty()){
			model.addAttribute("vGroup", vGroup);
		}else{
			model.addAttribute("failed",
					"There are still vehicles in this group, please remove them first.");
		}
		
		
		
		return model;
	}
	@RequestMapping(method = RequestMethod.POST,value="/delete")
	public String deleteVehicleGroup1(HttpSession session) {
		VehicleGroup vGroup = (VehicleGroup)session.getAttribute("vGroup");
		vGroup.setVehicles(null);
		
		// remove all vehicles from group
		List<Vehicle> vehicles = vehicleService.findVehicleByGroupId(vGroup.getId());
		
		if (vehicles != null){
			for (Vehicle v : vehicles){
				v.setVehiclegroup(null);
			}
		}
		
		try {
			
			vgroupService.delete(vGroup.getGroupName());
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/" + "vgroup/search";
		}
		
		return "redirect:/" +  "vgroup/search?companyid=" + vGroup.getCompany().getId();
	}
	
	/*
	 * Vehicles Assign
	 */
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/assign",method = RequestMethod.GET)
	public Model assignVehicles(
			@RequestParam(value = "groupName", required = true) String groupName,
			Model model) {
		VehicleGroup vGroup = vgroupService.findByName(groupName);
		//log.info("The assigned vehicles in the " + vGroup.getGroupName() + " is " + vGroup.getVehicles().size());
		
		Set vehicleSet = vGroup.getVehicles();
		if(vehicleSet != null && !vehicleSet.isEmpty()){
			for(Iterator it = vehicleSet.iterator(); it.hasNext();){
				Vehicle s = (Vehicle) it.next();
				log.info("vehicle.name=" + s.getName());
			}
		}
		
		model.addAttribute("vGroup", vGroup);
		model.addAttribute("vehicles", vehicleService.findNotAssignedVehiclesByCompany(vGroup.getCompany().getId().toString()));
		
/*		UserAssignRolesCheckboxTableDecorator decorator = new UserAssignRolesCheckboxTableDecorator();
		decorator.setId("name"); // id = name from role
		decorator.setFieldName("user"); // field name is user
		model.addAttribute("userAssignRolesCheckboxDecorator", decorator);*/
		
		return model;
	}
	
	@RequestMapping(value="/assign",method = RequestMethod.POST)
	public String addVehicles(HttpServletRequest request,HttpSession session) {
		
		VehicleGroup vGroup = (VehicleGroup)session.getAttribute("vGroup");
		//clean the vehicles;
		vGroup.getVehicles().clear();
		
		String[] selectedVehicleIDs = request.getParameterValues("available");
		String[] assignedVehicleIDs = request.getParameterValues("assigned");
		
		 
/*		if (selectedVehicleIDs != null) {
			for (int i = 0; i < selectedVehicleIDs.length; i++) {
				log.info("selectedVehicleIDs:" + selectedVehicleIDs[i] + ", ");
				Vehicle detachedInstance = vehicleService.findVehicleById(new Long(selectedVehicleIDs[i]));
				detachedInstance.setVehiclegroup(vGroup);
				
				vehicleService.update(detachedInstance);
				//vGroup.getVehicles().add();
			}
		}*/
		
/*		if (assignedVehicleIDs != null) {
			//Set<Vehicle> vehicles = vGroup.getVehicles();
			
			for (int i = 0; i < assignedVehicleIDs.length; i++) {
				log.info("assignedVehicleIDs:" + assignedVehicleIDs[i] + ", ");
				Vehicle detachedInstance = vehicleService.findVehicleById(new Long(assignedVehicleIDs[i]));
				vGroup.getVehicles().remove(detachedInstance);
				detachedInstance.setVehiclegroup(null);
				vehicleService.update(detachedInstance);
			}
			
		}*/
		if (selectedVehicleIDs != null) {
			//Set<Vehicle> vehicles = vGroup.getVehicles();
			
			for (int i = 0; i < selectedVehicleIDs.length; i++) {
				log.info("selectedVehicleIDs:" + selectedVehicleIDs[i] + ", ");
				Vehicle detachedInstance = vehicleService.findVehicleById(new Long(selectedVehicleIDs[i]));
				detachedInstance.setVehiclegroup(null);
				vehicleService.update(detachedInstance);
			}
			
		}
		if (assignedVehicleIDs != null) {
			for (int i = 0; i < assignedVehicleIDs.length; i++) {
				log.info("assignedVehicleIDs:" + assignedVehicleIDs[i] + ", ");
				Vehicle detachedInstance = vehicleService
						.findVehicleById(new Long(assignedVehicleIDs[i]));
				detachedInstance.setVehiclegroup(vGroup);

				vehicleService.update(detachedInstance);
				vGroup.getVehicles().add(detachedInstance);
			}
			
		}
		
		
		vgroupService.update(vGroup);

		return "redirect:/" +  "vgroup/search?companyid=" + vGroup.getCompany().getId();
	}	

}
