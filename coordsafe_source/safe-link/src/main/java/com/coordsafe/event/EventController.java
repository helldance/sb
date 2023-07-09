/**
 * 
 */
package com.coordsafe.event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coordsafe.config.PortalAppConfig;
import com.coordsafe.event.entity.GenericEvent;
import com.coordsafe.event.service.EventService;
import com.coordsafe.vehicle.entity.Vehicle;
import com.coordsafe.vehicle.entity.VehicleGroup;
import com.coordsafe.vehicle.service.VehicleGroupService;
import com.coordsafe.vehicle.service.VehicleService;

@Controller
@RequestMapping("/event")
public class EventController {
	private static final Logger logger = Logger.getLogger(EventController.class);
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private VehicleGroupService vehicleGroupService;
	
	@RequestMapping(value="/company/{companyId}/{timeRange}", method=RequestMethod.GET)
	@ResponseBody
	public List<GenericEvent> listByTime (@PathVariable("companyId") long companyId, @PathVariable("timeRange") String timeRange){		
		Date _dtStart = null, _dtEnd = null;
		SimpleDateFormat sdf = new SimpleDateFormat(PortalAppConfig.SIMPLE_DATE_FORMAT);
		
		if (timeRange != null){
			String [] timeslice = timeRange.split(",");
		
			String stm = timeslice[0];
			String etm = timeslice[1];
			
			try {
				_dtStart = sdf.parse(stm);
				_dtEnd = sdf.parse(etm);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return eventService.findEventbyTime(companyId, _dtStart, _dtEnd);
	}
	
	@RequestMapping(value="/company/{companyId}", method=RequestMethod.GET)
	@ResponseBody
	public List<GenericEvent> listByCount (@PathVariable("companyId") long companyId, 
			@RequestParam(value = "count", required = true) int count){
		return eventService.findEventbyCount(companyId, count);
	}
	
	@RequestMapping(value="/trip/{tripId}", method=RequestMethod.GET)
	@ResponseBody
	public List<GenericEvent> listByTrip (@PathVariable("tripId") long tripId){
		logger.info("find event by trip: " + tripId);
		return eventService.findEventbyTrip(tripId);
	}
	
	@RequestMapping(value="/company/{companyId}/page", method=RequestMethod.GET)
	public String listMain (@PathVariable("companyId") long companyId, 
			@RequestParam(value = "timeRange", required = false) String timeRange, Model model){
		Date _dtStart = null, _dtEnd = null;
		SimpleDateFormat sdf = new SimpleDateFormat(PortalAppConfig.SDF_DATE);
		
		if (timeRange != null && timeRange != ""){
			String [] timeslice = timeRange.split(",");
		
			String stm = timeslice[0];
			String etm = timeslice[1];
			
			try {
				_dtStart = sdf.parse(stm);
				_dtEnd = sdf.parse(etm);
				// search is beyond cur date
				_dtEnd = new Date(_dtEnd.getTime() + 24*3600*1000);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {//default will retrieve last 3 days
			Calendar c = Calendar.getInstance();
			c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE) + 1);
			_dtEnd = c.getTime();
			
			c.setTimeInMillis(c.getTimeInMillis() - 24 * 3600 * 1000);
			_dtStart = c.getTime();
		}
		
		// return group with vechile
		List<VehicleGroup> vg = vehicleGroupService.findVehicleGroupsByCompany(String.valueOf(companyId));
		
		/*if (vg != null){
			for (VehicleGroup g : vg){
				
			}
		}*/
		
		//JSONObject jsonObject = JSONObject.fromObject(vg);  
		
		List<Vehicle> vehicles = vehicleService.findVehiclesByCompany(String.valueOf(companyId));
		
		model.addAttribute("events", eventService.findEventbyTime(companyId, _dtStart, _dtEnd));
		model.addAttribute("vehicles", vehicles);
		model.addAttribute("vehiclegroups", vg);
		// also return time range
		model.addAttribute("startdate", _dtStart.getTime());
		model.addAttribute("enddate", _dtEnd.getTime());
		
		return "event/search";
	}
}
