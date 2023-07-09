/**
 * TripController.java
 * May 31, 2013
 * Yang Wei
 */
package com.coordsafe.trip.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coordsafe.locator.entity.Locator;
import com.coordsafe.locator.entity.LocatorLocationHistory;
import com.coordsafe.locator.entity.SimpleLocationHistoryFilter;
import com.coordsafe.locator.service.LocatorService;
import com.coordsafe.trip.entity.Trip;
import com.coordsafe.trip.entity.TripDetail;
import com.coordsafe.trip.entity.TripSummary;
import com.coordsafe.trip.service.TripService;
import com.coordsafe.vehicle.entity.Vehicle;
import com.coordsafe.vehicle.entity.VehicleGroup;
import com.coordsafe.vehicle.service.VehicleGroupService;
import com.coordsafe.vehicle.service.VehicleService;

/**
 * @author Yang Wei
 *
 */
@Controller
@RequestMapping("/trip")
public class TripController {
	private static final Logger logger = Logger.getLogger(TripController.class);
	private final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	private final SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
	
	@Autowired
	private TripService tripService;
	
	@Autowired
	private LocatorService locatorService;
	
	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private VehicleGroupService vehicleGroupService;
		
	@RequestMapping(value="/company/{companyId}/{timeRange}", method=RequestMethod.GET)
	public String listTrip (@PathVariable("companyId") long companyId, @PathVariable("timeRange") String timeRange, Model model){
		List<Locator> locators = locatorService.findLocatorByAssignTo(companyId);
		List<Trip> trips = new ArrayList<Trip>();
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		List<VehicleGroup> vehiclegroups = new ArrayList<VehicleGroup>();
		
		Date _dtStart = null, _dtEnd = null;
		
		if (timeRange != null && timeRange != ""){
			String [] timeslice = timeRange.split(",");
		
			String stm = timeslice[0];
			String etm = timeslice[1];
			
			try {
				_dtStart = sdf2.parse(stm);
				_dtEnd = sdf2.parse(etm);
				_dtEnd = new Date(_dtEnd.getTime() + 24*3600*1000);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {//default will retrieve last 3 days
			Calendar c = Calendar.getInstance();
			c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
			_dtEnd = c.getTime();
			
			c.setTimeInMillis(c.getTimeInMillis() - 3 * 24 * 3600 * 1000);
			_dtStart = c.getTime();
		}		
			
		for (Locator l : locators){
			List<Trip> singleTrips = tripService.findByTime(l.getId(), _dtStart, _dtEnd);
			trips.addAll(singleTrips);
		}
		
		vehicles = vehicleService.findVehiclesByCompany(String.valueOf(companyId));
		vehiclegroups = vehicleGroupService.findVehicleGroupsByCompany(String.valueOf(companyId));
		
		model.addAttribute("trips", trips);
		model.addAttribute("vehiclegroups", vehiclegroups);
		model.addAttribute("vehicles", vehicles);
		// also return time range
		model.addAttribute("startdate", _dtStart.getTime());
		model.addAttribute("enddate", _dtEnd.getTime());
	
		return "trip/search";
	}
	
	@RequestMapping(value="/company/{companyId}", method=RequestMethod.GET)
	public String listTripLast3Days (@PathVariable("companyId") long companyId, Model model){
		List<Locator> locators = locatorService.findLocatorByAssignTo(companyId);
		List<Trip> trips = new ArrayList<Trip>();
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		List<VehicleGroup> vehiclegroups = new ArrayList<VehicleGroup>();
		
		Date _dtStart = null, _dtEnd = null;
		
		Calendar c = Calendar.getInstance();
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE) + 1);
		_dtEnd = c.getTime();
		
		c.setTimeInMillis(c.getTimeInMillis() - 3 * 24 * 3600 * 1000);
		_dtStart = c.getTime();
		
		logger.info("last 3 days trip: " + _dtStart.toString() + _dtEnd.toString());
		
		for (Locator l : locators){
			List<Trip> singleTrips = tripService.findByTime(l.getId(), _dtStart, _dtEnd);
			trips.addAll(singleTrips);
		}
		
		vehicles = vehicleService.findVehiclesByCompany(String.valueOf(companyId));
		vehiclegroups = vehicleGroupService.findVehicleGroupsByCompany(String.valueOf(companyId));
		
		model.addAttribute("trips", trips);
		model.addAttribute("vehiclegroups", vehiclegroups);
		model.addAttribute("vehicles", vehicles);
		// also return time range
		model.addAttribute("startdate", _dtStart.getTime());
		model.addAttribute("enddate", _dtEnd.getTime());
	
		return "trip/search";
	}

	
	@RequestMapping(value="/edit/{tripId}", method=RequestMethod.GET)
	public String editTrip (@PathVariable("tripId") long tripId, Model model){	
		Trip trip = tripService.findById(tripId);
		model.addAttribute(trip);
		
		return "trip/edit";
	}
	
	@RequestMapping(value="/{tripId}", method=RequestMethod.GET)
	public @ResponseBody Trip getTrip (@PathVariable("tripId") long tripId){
		return tripService.findById(tripId);
	}
	
	@RequestMapping(value="/{tripId}", method=RequestMethod.POST)
	public @ResponseBody Trip updateTrip (@RequestParam(value = "fuel_manual") double fuel, 
			@RequestParam(value = "mileage_manual") double mileage, @PathVariable long tripId){
		Trip trip = tripService.findById(tripId);
		
		trip.setMileage_manual(mileage);
		trip.setFuel_manual(fuel);
		
		tripService.update(trip);
		
		return trip;
	}
	
	/*@RequestMapping(method = RequestMethod.POST, value="/{tripId}")
	public void editVehicle(@PathVariable("tripId") long tripId, Trip trip) {
		logger.info("Edit trip: " + tripId);

		tripService.update(trip);			
	}*/
	
	@RequestMapping(value="/day/{locatorId}", method=RequestMethod.GET)
	@ResponseBody
	public List<Trip> getDayTrips (@PathVariable("locatorId") long locatorId, Model model){
		Date dayNow = new Date();		
		Date dayStart = null;
		
		try {
			dayStart = sdf.parse(sdf.format(dayNow));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		List<Trip> trips = tripService.findByTime(locatorId, dayStart, dayNow);
		
		model.addAllAttributes(trips);
		
		return trips;
	}
	
	@RequestMapping(value="/week/{locatorId}", method=RequestMethod.GET)
	@ResponseBody
	public List<Trip> getWeekTrips (@PathVariable("locatorId") long locatorId, Model model){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		
		List<Trip> trips = tripService.findByTime(locatorId, c.getTime(), new Date());
		
		model.addAllAttributes(trips);
		
		return trips;
	}
	
	@RequestMapping(value="/month/{locatorId}", method=RequestMethod.GET)
	@ResponseBody
	public List<Trip> getMonthTrips (@PathVariable("locatorId") long locatorId, Model model){
		Calendar c = Calendar.getInstance();
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 0, 0, 0);
		
		List<Trip> trips = tripService.findByTime(locatorId, c.getTime(), new Date());
		
		model.addAllAttributes(trips);
		
		return trips;
	}

	@RequestMapping(value="/summary/{locatorId}", method=RequestMethod.GET)
	public @ResponseBody List<TripSummary> getTripSummaryByVehicle (@PathVariable("locatorId") long locatorId){
		Calendar c = Calendar.getInstance();
		
		c.set(c.get(Calendar.YEAR), 0, 1, 0, 0, 0);		
		List<Trip> year_trips = tripService.findByTime(locatorId, c.getTime(), new Date());
		
		c = Calendar.getInstance();
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 0, 0, 0);		
		List<Trip> month_trips = tripService.findByTime(locatorId, c.getTime(), new Date());
		
		c = Calendar.getInstance();
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 0, 0, 0);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);	
		List<Trip> week_trips = tripService.findByTime(locatorId, c.getTime(), new Date());
				
		List<TripSummary> summary = new ArrayList<TripSummary>();
		summary.add(buildSummaryForTrips(year_trips));
		summary.add(buildSummaryForTrips(month_trips));
		summary.add(buildSummaryForTrips(week_trips));
		
		return summary;
	}
	
	private TripSummary buildSummaryForTrips (List<Trip> trips){
		TripSummary sum_trip = new TripSummary();
		int mile = 0, dur = 0, cnt_speed = 0, cnt_brake = 0;
				
		for (Trip trip : trips){
			mile += trip.getMileage();
			dur += trip.getMovingTime();
			//if (trip.getTripStartTime() != 0 || trip.getTripEndTime() != 0)
				//dur += trip.getTripEndTime().getTime() - trip.getTripStartTime().getTime();
			cnt_speed += trip.getSpeeding();
			cnt_brake += trip.getJameBreak();
		}
		
		sum_trip.mileage = mile;
		sum_trip.tripCount = trips.size();
		sum_trip.duration = dur;
		sum_trip.brakeCount = cnt_brake;
		sum_trip.speedCount = cnt_speed;
		
		return sum_trip;		
	}
	
	@RequestMapping(value="/locator/{locatorId}/{timeRange}", method=RequestMethod.GET)
	@ResponseBody
	public List<Trip> findTripByTime(@PathVariable("locatorId") long locatorId, @PathVariable("timeRange") String timeRange) {
		String [] timeslice = timeRange.split(",");
		
		String stm = timeslice[0];
		String etm = timeslice[1];
		
		Date _dtStart = null, _dtEnd = null;
		
		try {
			_dtStart = sdf.parse(stm);
			_dtEnd = sdf.parse(etm);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tripService.findByTime(locatorId, _dtStart, _dtEnd);
	}
	
	@RequestMapping(value="/group/{groupId}/{timeRange}", method=RequestMethod.GET)
	@ResponseBody
	public String findGroupTripByTime(@PathVariable("groupId") long groupId, @PathVariable("timeRange") String timeRange) {
		String [] timeslice = timeRange.split(",");
		
		String stm = timeslice[0];
		String etm = timeslice[1];
		
		return tripService.findGroupTripByTime(groupId, stm, etm);
	}
	
	@RequestMapping(value="detail/{locatorId}/{time}", method=RequestMethod.GET)
	@ResponseBody
	public String findTripDetailByTime(
			@PathVariable("locatorId") String locatorId, @PathVariable("time") String time, @RequestParam(value = "full", required = false) boolean full){
		
		String [] timeslice = time.split(",");
		
		String stm = timeslice[0];
		String etm = timeslice[1];
		//In safe-link, the locatorId is the deviceId (device Imei code);
		List<TripDetail> tripDetails = tripService.findTripDetailBtwTime(locatorService.findLocatorByImei(locatorId).getId(), stm, etm);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = "";
		
		try {
			if (full){
				jsonStr = mapper.writeValueAsString(tripDetails);
			}
			else{
				// use mix-in annotation instead of filter
				SerializationConfig serializationConfig = mapper.getSerializationConfig();  
				
				// define mix-in annotations
				serializationConfig.addMixInAnnotations(LocatorLocationHistory.class, SimpleLocationHistoryFilter.class);  
				
				jsonStr = mapper.writer().writeValueAsString(tripDetails);
			}
		} catch (JsonGenerationException e) {
			logger.error(e.getStackTrace());
		} catch (JsonMappingException e) {
			logger.error(e.getStackTrace());
		} catch (IOException e) {
			logger.error(e.getStackTrace());
		}
		
		return jsonStr;
	}

	@RequestMapping(value="detail/{locatorId}/curOrLast", method=RequestMethod.GET)
	@ResponseBody
	public String findCurOrLastTripDetail(@PathVariable("locatorId") long locatorId, @RequestParam(value = "full", required = false) boolean full){
		TripDetail td = tripService.findCurOrLastTripDetail(locatorId);
		
		logger.info("find trip details history count: " + td.history.size());
		
		List<TripDetail> tripDetails = new ArrayList<TripDetail> ();
		
		tripDetails.add(td);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = "";
		
		try {
			if (full){
				jsonStr = mapper.writeValueAsString(tripDetails);
			}
			else{
				// use mix-in annotation instead of filter
				SerializationConfig serializationConfig = mapper.getSerializationConfig();  
				
				// define mix-in annotations
				serializationConfig.addMixInAnnotations(LocatorLocationHistory.class, SimpleLocationHistoryFilter.class);  
				
				jsonStr = mapper.writer().writeValueAsString(tripDetails);
			}
		} catch (JsonGenerationException e) {
			logger.error(e.getStackTrace());
		} catch (JsonMappingException e) {
			logger.error(e.getStackTrace());
		} catch (IOException e) {
			logger.error(e.getStackTrace());
		}
		
		return jsonStr;
	}		
}
