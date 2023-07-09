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
import com.coordsafe.core.rbac.service.UserService;
import com.coordsafe.geofence.entity.Geofence;
import com.coordsafe.geofence.entity.GfThumbnail;
import com.coordsafe.geofence.service.GeofenceService;
import com.coordsafe.geofence.service.GfThumbnailService;
import com.coordsafe.vehicle.entity.Vehicle;
import com.coordsafe.vehicle.service.VehicleService;

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
	private VehicleService vehicleSvrs;
	
	@RequestMapping(value="/zone/{gfId}", method=RequestMethod.GET)
	@ResponseBody 
	public String getSingle (@PathVariable("gfId") long gfId){
		return geofenceService.findGeojsonById(gfId);
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String loadFence (@RequestParam ("geofenceid") long gfId, Model model){
		model.addAttribute("action", "edit");
		model.addAttribute("geometry", getSingle(gfId));
		
		Geofence fence = geofenceService.findById(gfId);
		int type = fence.geometryType;
		//model.addAttribute("type", fence.geometryType == 1? "fence":"route");
		if (type == 1)
			return "geofence/new";
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
		Company company;
		ObjectMapper mapper = new ObjectMapper();
		
		logger.info(reqData);
		
		if (createBy != null){
			try {
				company = userService.findByUsername(createBy).getCompany();
				
				Map<String,Object> reqDataPair = mapper.readValue(reqData, Map.class);
				
				String gjStr = (String) reqDataPair.get("geometry");
				
				GfThumbnail tn = new GfThumbnail();
				tn.setUrl((String) reqDataPair.get("img_url"));
				
				Geofence fence = new Geofence();
				fence.setCompany(company);
				fence.setCreateBy(createBy);
				fence.setCreateDt(new Date());
				fence.setThumbnail(tn);

				//not found class
				//GeometryJSON g = new GeometryJSON();
				
				long tnId = gfThumbnailService.create(tn);
				geofenceService.createGeofenceFromGeoJson(company.getId(), createBy, "", Integer.parseInt(String.valueOf(reqDataPair.get("drawing_type"))), gjStr, tnId);
				
				
				return "OK";
			}
			catch (Exception ex){
				ex.printStackTrace();
			}
		}
		
		//geofenceService.createGeofenceFromGeoJson(companyId, createBy, 0, gjStr);
		
		return "";
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String getContains (Model model, @RequestParam("companyid") String companyid, @RequestParam("type") String type, HttpSession session){
		logger.info("Searching Zones..." + companyid);
		List<Geofence> geofences = geofenceService.findGeofenceByCompany(companyid);
		model.addAttribute("geofences", geofences);
		model.addAttribute("type", type);
		
		return "geofence/search";
	}
	
	@RequestMapping(value="/assign", method=RequestMethod.GET)
	public String getAssign (Model model, @RequestParam("geofenceId") long geofenceId, HttpServletRequest request){
		Company comp = userService.findByUsername(request.getUserPrincipal().getName()).getCompany();
		Geofence gf = geofenceService.findById(geofenceId);
		
		List<Vehicle> all = vehicleSvrs.findVehiclesByCompany(String.valueOf(comp.getId()));
		Set<Vehicle> assigned = gf.getVehicles();
		
		all.removeAll(assigned);
		
		model.addAttribute("allVehicles", all);
		model.addAttribute("assignedVehicles", gf.getVehicles());
		model.addAttribute("geofence", gf);
		
		return "geofence/assignVehicles";
	}	
	
	@RequestMapping(value="/assign",method = RequestMethod.POST)
	public String addVehicles(HttpServletRequest request,HttpSession session) {				
		Geofence geofence = (Geofence)session.getAttribute("geofence");
		
		String[] selectedVehicleIDs = request.getParameterValues("available");
		String[] assignedVehicleIDs = request.getParameterValues("assigned");
		
		/*Set<Vehicle> orignalVehicles = geofence.getVehicles();
		
		if (orignalVehicles == null){
			or
		}*/
		
		// update deselected vehicles
		if (selectedVehicleIDs != null){
			for (String id: selectedVehicleIDs) {
				logger.info("selectedVechileIds:" + id + ", ");
				
				Vehicle v = vehicleSvrs.findVehicleById(Long.valueOf(id));
				
				geofence.getVehicles().remove(v);
				
				v.getFences().remove(geofence);
				
				vehicleSvrs.update(v);
			}
		}
		
		// update assigned vehicles
		if (assignedVehicleIDs != null){
			for (String id2: assignedVehicleIDs) {
				logger.info("assignedWardIDs:" + id2 + ", ");
				
				Vehicle v = vehicleSvrs.findVehicleById(Long.valueOf(id2));
				
				geofence.getVehicles().add(v);
				
				v.getFences().add(geofence);
				
				vehicleSvrs.update(v);
			}
		}
		
		geofenceService.update(geofence);
		
		return "redirect:/" +  "geofence/search?companyid=" + geofence.getCompany().getId() + "&type=fence";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String createZone (@RequestParam("type") String type){
		if (type.equalsIgnoreCase("fence"))
			return "geofence/new";
		else //if (type.equalsIgnoreCase("route"))
			return "route/new";
	}
	
	@RequestMapping(value="/delete", method= RequestMethod.GET)
	public String deleteZone(@RequestParam ("geofenceid") long gfId, HttpServletRequest request){
		logger.info("delete geofence: " + gfId);
		
		Geofence gf = geofenceService.findById(gfId);
		
		geofenceService.deleteById(gfId);
		
		String type = (gf.getGeometryType() == 1)?"route":"fence";
		
		return "redirect:/" +  "geofence/search?companyid=" + gf.getCompany().getId() +  "&type=" + type;
	}
	
	@RequestMapping(value="/within", method=RequestMethod.GET)
	//public String within(Model model,Point point, String companyid, int zoneType){
	
	//getting locator by vehicle 
	//getting location of locator
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
}
