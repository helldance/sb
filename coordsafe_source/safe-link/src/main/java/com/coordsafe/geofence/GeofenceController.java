/**
 * 
 */
package com.coordsafe.geofence;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.postgis.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.coordsafe.company.entity.Company;
import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.service.UserService;
import com.coordsafe.geofence.entity.Geofence;
import com.coordsafe.geofence.entity.GfThumbnail;
import com.coordsafe.geofence.service.GeofenceService;
import com.coordsafe.geofence.service.GfThumbnailService;
import com.coordsafe.guardian.entity.Guardian;
import com.coordsafe.guardian.service.GuardianService;
import com.coordsafe.vehicle.entity.Vehicle;
import com.coordsafe.vehicle.entity.VehicleGroup;
import com.coordsafe.ward.entity.Ward;
import com.coordsafe.ward.exception.WardException;
import com.coordsafe.ward.service.WardService;

/**
 * @author Yang Wei
 *
 */
@Controller
@RequestMapping("/geofence")
@SessionAttributes("geofence")
public class GeofenceController {
	private static final Log logger = LogFactory.getLog(GeofenceController.class);
	//User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	@Autowired
	private GeofenceService geofenceService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GfThumbnailService gfThumbnailService;
	
	@Autowired
	private GuardianService guardianService;
	
	@Autowired
	private WardService wardService;
	
	@RequestMapping(value="/zone/{gfId}", method=RequestMethod.GET)
	@ResponseBody 
	public String getSingle (@PathVariable("gfId") long gfId){
		return geofenceService.findGeojsonById(gfId);
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String loadFence (@RequestParam ("geofenceid") long gfId, Model model){
		String polygonJSON = getSingle(gfId);
		logger.info("The Polygon info is " + polygonJSON);
		model.addAttribute("action", "edit");
		model.addAttribute("geometry", polygonJSON);
		
		Geofence fence = geofenceService.findById(gfId);
		int type = fence.geometryType;
		//model.addAttribute("type", fence.geometryType == 1? "fence":"route");
		// 0 means fence;1 means route;
		if (type == 0)
			return "geofence/edit";
		else
			return "route/new";
	}
	
	@RequestMapping(value="/edit/{gfId}", method=RequestMethod.POST)
	public @ResponseBody String updateFence (@PathVariable("gfId") long gfId, @RequestBody String gjStr){		
		geofenceService.updateGeofenceFromGeoJson(gfId, gjStr);
		
		return "OK";
	}
	
	@RequestMapping(value="/zone/add", method=RequestMethod.POST)
	public @ResponseBody String addSingle (@RequestBody String reqData, HttpServletRequest request){		
		String createBy = request.getUserPrincipal().getName();
		//Company company;
		ObjectMapper mapper = new ObjectMapper();
		
		logger.info(reqData);
		
		if (createBy != null){
			try {
				//company = userService.findByUsername(createBy).getCompany();
				//Guardian guardian = guardianService.get(createBy);
				@SuppressWarnings("unchecked")
				Map<String,Object> reqDataPair = mapper.readValue(reqData, Map.class);
				
				String gjStr = (String) reqDataPair.get("geometry");
				
				GfThumbnail tn = new GfThumbnail();
				tn.setUrl((String) reqDataPair.get("img_url"));
				
				String zoneType = (String) reqDataPair.get("zoneType");
				
				String zoneName = (String) reqDataPair.get("zoneName");
				
				String drawing_type = reqDataPair.get("drawing_type").toString();
				
				Geofence fence = new Geofence();
				//fence.setCompany(company);
				fence.setCreateBy(createBy);
				fence.setCreateDt(new Date());
				fence.setThumbnail(tn);

				//not found class
				//GeometryJSON g = new GeometryJSON();
				logger.info(reqDataPair.get("drawing_type") + " " + zoneType);
				long tnId = gfThumbnailService.create(tn);
				//1 means company id is coordsafe
				geofenceService.createGeofenceFromGeoJson(1, createBy, Integer.parseInt(drawing_type), gjStr, tnId,zoneType,zoneName);
				
				
				return "OK";
			}
			catch (Exception ex){
				ex.printStackTrace();
			}
		}
		
		//geofenceService.createGeofenceFromGeoJson(companyId, createBy, 0, gjStr);
		
		return "FAILED";
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String getContains (Model model, @RequestParam("createby") String createby, @RequestParam("type") String type, HttpSession session){
		logger.info("Searching Zones for..." + createby);
		List<Geofence> geofences = geofenceService.findGeofenceByCreateFor(createby);
		logger.info("The zones are..." + geofences.size());

		model.addAttribute("geofences", geofences);
		model.addAttribute("type", type);
		
		return "geofence/search";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String createZone (@RequestParam("type") String type){
		if (type.equalsIgnoreCase("fence"))
			return "geofence/new";
		else //if (type.equalsIgnoreCase("route"))
			return "route/new";
	}
	
/*	@RequestMapping(value="/delete", method= RequestMethod.GET)
	public String deleteZone(@RequestParam ("geofenceid") long gfId, HttpServletRequest request){
		logger.info("delete geofence: " + gfId);
		
		Geofence gf = geofenceService.findById(gfId);
		
		geofenceService.deleteById(gfId);
		
		String type = (gf.getGeometryType() == 1)?"route":"fence";
		
		return "redirect:/" +  "geofence/search?createby=" + request.getUserPrincipal().getName() +  "&type=" + type;
	}*/
	///////////////////////
	@RequestMapping(method = RequestMethod.GET,value="/delete")
	public Model deleteZone(@RequestParam ("geofenceid") long gfId, HttpServletRequest request, Model model) {
		
		logger.info("Zone Deleting...");
		model.addAttribute("zone", geofenceService.findById(gfId));
		//model.addAttribute("name", geofenceService.findById(gfId).get);
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST,value="/delete")
	public String deleteVehicle1(@RequestParam(value = "geofenceid", required = true) String gid,HttpServletRequest request) {
		
		try {
			geofenceService.deleteById(Long.valueOf(gid));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		return "redirect:/" +  "geofence/search?createby=" + request.getUserPrincipal().getName() +  "&type=fence";
	}

	/////////////////
	
	@RequestMapping(value="/within", method=RequestMethod.GET)
	public String within(Model model){
		Point point = new Point( );
		point.setX(103.80557388067245);
		point.setY(1.3199592157121878);
		
		String companyid = "1";
		
		int zoneType = 1;
		
		List<Geofence> geofences = geofenceService.within(point, companyid, zoneType);
		for(Iterator<Geofence> it = geofences.iterator(); it.hasNext();){
			Geofence gf = it.next();
			logger.info(gf.getGeometry().toText());
		}

		model.addAttribute("geofences", geofences);
		return "geofence/within";
		
	}
	
	
	
	/*
	 * Ward Assign
	 */
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/assign",method = RequestMethod.GET)
	public Model assignVehicles(
			@RequestParam(value = "guardianName", required = true) String guardianName,@RequestParam(value = "geofenceid", required = true) String geofenceid,
			Model model) {
		Guardian guardian = guardianService.get(guardianName);
		Geofence geofence = geofenceService.findById(new Long(geofenceid));
		Set<Ward> assignedwards = geofence.getWards();
		Set<Ward> allwards = guardian.getWards();
		for(Ward ward:assignedwards){
			allwards.remove(ward);
		}
		
		//log.info("The assigned vehicles in the " + vGroup.getGroupName() + " is " + vGroup.getVehicles().size());
		

		if(allwards != null && !allwards.isEmpty()){
			for(Iterator it = allwards.iterator(); it.hasNext();){
				Ward s = (Ward) it.next();
				logger.info("ward.name=" + s.getName());
			}
		}
		
		model.addAttribute("guardian", guardian);
		model.addAttribute("geofence", geofence);
		model.addAttribute("assignedwards", assignedwards);
		model.addAttribute("availablewards", allwards);
		
/*		UserAssignRolesCheckboxTableDecorator decorator = new UserAssignRolesCheckboxTableDecorator();
		decorator.setId("name"); // id = name from role
		decorator.setFieldName("user"); // field name is user
		model.addAttribute("userAssignRolesCheckboxDecorator", decorator);*/
		
		return model;
	}
	
	@RequestMapping(value="/assign",method = RequestMethod.POST)
	public String addVehicles(HttpServletRequest request,HttpSession session) {
		
		Geofence geofence = (Geofence)session.getAttribute("geofence");
		
/*		geofence.setWards(null);
		geofenceService.update(geofence);*/

		
		String[] selectedVehicleIDs = request.getParameterValues("available");
		String[] assignedVehicleIDs = request.getParameterValues("assigned");
		

		if (selectedVehicleIDs != null) {
			//Set<Vehicle> vehicles = vGroup.getVehicles();
			
			for (int i = 0; i < selectedVehicleIDs.length; i++) {
				logger.info("selectedWardIDs:" + selectedVehicleIDs[i] + ", ");
				Ward detachedInstance = wardService.findByID(new Long(selectedVehicleIDs[i]));
				geofence.getWards().remove(detachedInstance);
				//detachedInstance.setVehiclegroup(null);
				//geofenceService.update(geofence);
			}
			
		}
		if (assignedVehicleIDs != null) {
			for (int i = 0; i < assignedVehicleIDs.length; i++) {
				logger.info("assignedWardIDs:" + assignedVehicleIDs[i] + ", ");
				Ward detachedInstance = wardService.findByID(new Long(assignedVehicleIDs[i]));
				geofence.getWards().add(detachedInstance);
				//detachedInstance.setVehiclegroup(null);
				//geofenceService.update(geofence);
			}
			
		}
		
		
		geofenceService.update(geofence);
		return "redirect:/" +  "geofence/search?createby=" + request.getUserPrincipal().getName()+"&type=fence";
	}	
}
