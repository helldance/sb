package com.coordsafe.locator.ws;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.beans.factory.annotation.Autowired;

import com.coordsafe.config.PortalAppConfig;
import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.exception.UserException;
import com.coordsafe.core.rbac.service.UserService;
import com.coordsafe.event.entity.GenericEvent;
import com.coordsafe.event.entity.VehicleEvent;
import com.coordsafe.event.service.EventService;
import com.coordsafe.geofence.entity.GfThumbnail;
import com.coordsafe.geofence.service.GeofenceService;
import com.coordsafe.geofence.service.GfThumbnailService;
import com.coordsafe.locator.entity.DeviceStatus;
import com.coordsafe.locator.entity.GpsLocation;
import com.coordsafe.locator.entity.Locator;
import com.coordsafe.locator.entity.LocatorLocationHistory;
import com.coordsafe.locator.entity.SimpleDeviceStatusFilter;
import com.coordsafe.locator.entity.SimpleGpsLocationFilter;
import com.coordsafe.locator.entity.SimpleLocationHistoryFilter;
import com.coordsafe.locator.entity.SimpleLocatorFilter;
import com.coordsafe.locator.service.LocatorService;
import com.coordsafe.locator.service.LocatorServiceImpl;
import com.coordsafe.trip.entity.Trip;
import com.coordsafe.trip.entity.TripDetail;
import com.coordsafe.trip.service.TripService;
import com.coordsafe.vehicle.entity.VehicleGroup;
import com.coordsafe.vehicle.service.VehicleGroupService;
import com.coordsafe.vehicle.service.VehicleService;

public class LocatorWebServiceRestImpl implements LocatorWebServiceRest{
	private static Logger logger = Logger.getLogger(LocatorServiceImpl.class);
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	@Autowired 
	private LocatorService locatorService;
	@Autowired
	private UserService userService;
	@Autowired
	private TripService tripService;
	@Autowired
	private GeofenceService geofenceService;
	@Autowired
	private GfThumbnailService gfThumbnailService;
	@Autowired
	private VehicleService vehicleSvrs;
	@Autowired
	private EventService eventSvrs;
	@Autowired
	private VehicleGroupService vgSvrs; 
	
	@Override
	@GET
	@Path("locator/company/{companyId}")
	@Produces("application/json")
	public List<Locator> findLocatorByCompany(@PathParam("companyId") long companyCode) {
		logger.info("find by company.." + companyCode);

		return locatorService.findLocatorByAssignTo(companyCode);
	}	
	
	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("locator/company/{companyId}/basic")
	public String findLocatorByCompanyShort(@PathParam("companyId") long companyCode){
		List<Locator> locators = locatorService.findLocatorByAssignTo(companyCode);
		ObjectMapper mapper = new ObjectMapper();
		
		// construct simplelocatorfilter as defined in Locator class
		//String [] filterProps = {"id", "label", "latitude", "longitude", "lastStatusUpdate"};
		//FilterProvider filters = new SimpleFilterProvider().addFilter
				//("SimpleLocatorFilter", SimpleBeanPropertyFilter.filterOutAllExcept(filterProps));

		// use mix-in annotation instead of filter
		SerializationConfig serializationConfig = mapper.getSerializationConfig();  
		
		// define mix-in annotations
		serializationConfig.addMixInAnnotations(Locator.class, SimpleLocatorFilter.class);  
		serializationConfig.addMixInAnnotations(GpsLocation.class, SimpleGpsLocationFilter.class);  
		serializationConfig.addMixInAnnotations(DeviceStatus.class, SimpleDeviceStatusFilter.class);  
		
		String jsonStr = "";
		
		try {
			//jsonStr = mapper.filteredWriter(filters).writeValueAsString(locators);
			jsonStr = mapper.writer().writeValueAsString(locators);
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
		
		return jsonStr;
	}
	
	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("locator/company/{companyId}/short")
	public String findLocatorByCompanyVeryShort(@PathParam("companyId") long companyCode){
		List<Locator> locators = locatorService.findLocatorByAssignTo(companyCode);
		List<HashMap<String, String>> shortLocators = new ArrayList<HashMap<String, String>>();
		
		ObjectMapper mapper = new ObjectMapper();
		
		for (Locator l : locators){
			HashMap<String, String> map = new HashMap<String, String>();
			
			map.put("id", "" + l.getId());
			map.put("imei", l.getImeiCode());
			map.put("label", l.getLabel());
			
			shortLocators.add(map);
		}
		
		String jsonStr = "";
		
		try {
			jsonStr = mapper.writeValueAsString(shortLocators);
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
		
		return jsonStr;
	}
	
	@Override
	@GET
	@Path("history/{locatorId}/{timeRange}")
	@Produces("application/json")
	public List<LocatorLocationHistory> findLocationHistoryByTime(
			@PathParam("locatorId") String locatorId, @PathParam("timeRange") String timeRange) {
		// TODO Auto-generated method stub	
		String [] timeslice = timeRange.split(",");
		
		String stm = timeslice[0];
		String etm = timeslice[1];
		
		logger.info("find history.." + locatorId + " " + stm + " " + etm);
		
		return locatorService.findLocationHistoryByTime(locatorId, stm, etm);
	}

	@Override
	@GET
	@Path("location/{locatorId}/{time}")
	@Produces("application/json")
	public LocatorLocationHistory findLocationByTime(
			@PathParam("locatorId") String locatorId,
			@PathParam("time") String time) {

		return locatorService.findLocationByTime(locatorId, time);
	}

	/*@Override
	@POST
	@Path("/auth")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(Identity identity) {
		System.out.println(identity.getName() + identity.getPassword());
		
		ResponseBuilder builder = Response.status(Status.OK);
		builder.entity(false);
			
		try {
			if  (userService.login(identity.getName(), identity.getPassword()) != null){
				builder.entity(true);
			}
		} catch (UserException e) {
			e.printStackTrace();
		}
		
		return builder.build();		
	}*/

	@Override
	@POST
	@Path("/login")
	public Response login(@HeaderParam("name") String name,
			@HeaderParam("password") String password) {
		
		ResponseBuilder builder = Response.status(Status.OK);
		builder.entity(false);
			
		try {
			User user = userService.login(name, password);
			if  (user != null){
				//HashMap<String, String> map = new HashMap<String, String>();
				
				//map.put("name", user.getUsername());
				//map.put("company", user.getCompanyName());
				
				//TODO: returns api key to user
				
				builder.entity(user.getUsername() + "/" + user.getCompany().getName() + "/" + user.getApiKey().getKey());
			}
		} catch (UserException e) {
			e.printStackTrace();
		}
		
		return builder.build();	
	}

	@Override
	@GET
	@Path("trip/{locatorId}/{timeRange}")
	@Produces("application/json")
	public List<Trip> findTripBtwTime(@PathParam("locatorId") String locatorId,
			@PathParam("timeRange") String timeRange) {
		String [] timeslice = timeRange.split(",");
		
		String stm = timeslice[0];
		String etm = timeslice[1];
		
		logger.info("find trip.." + locatorId + " " + stm + " " + etm);
		
		return tripService.findTripBtwTime(Long.parseLong(locatorId), stm, etm);
	}

	@Override
	@GET
	@Path("trip-detail-old/{locatorId}/{time}")
	@Produces("application/json")
	public List<TripDetail> findTripDetailByTime(
			@PathParam("locatorId") String locatorId,
			@PathParam("time") String time) {
		String [] timeslice = time.split(",");
		
		String stm = timeslice[0];
		String etm = timeslice[1];
		
		return tripService.findTripDetailBtwTime(Long.parseLong(locatorId), stm, etm);
	}

	@Override
	@GET
	@Path("trip-detail/{locatorId}/{time}")
	public String findTripDetailByTimeShort(
			@PathParam("locatorId") String locatorId,
			@PathParam("time") String time, @FormParam("full") boolean full) {
		String [] timeslice = time.split(",");
		
		String stm = timeslice[0];
		String etm = timeslice[1];
		
		List<TripDetail> tripDetails = tripService.findTripDetailBtwTime(Long.parseLong(locatorId), stm, etm);
		
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

	@Override
	@GET
	@Path("mileage/{locatorId}/{time}")
	public double getMileageByTime(@PathParam("locatorId") String locatorId,
			@PathParam("time") String time) {
		String [] timeslice = time.split(",");
		
		String stm = timeslice[0];
		String etm = timeslice[1];
		
		return tripService.getMileageByTime(Long.parseLong(locatorId), stm, etm);
	}

	@Override
	@GET
	@Path("move/{locatorId}/{time}")
	public double getMovingTimeByTime(@PathParam("locatorId") String locatorId,
			@PathParam("time") String time) {
		String [] timeslice = time.split(",");
		
		String stm = timeslice[0];
		String etm = timeslice[1];
		
		return tripService.getMovingTimeByTime(Long.parseLong(locatorId), stm, etm);
	}
	
	@Override
	@Path("trip/group/{groupId}/{timeRange}")
	@GET
	public String findGroupTripByTime(@PathParam("groupId") long groupId, @PathParam("timeRange") String timeRange) {
		String [] timeslice = timeRange.split(",");
		
		String stm = timeslice[0];
		String etm = timeslice[1];
		
		return tripService.findGroupTripByTime(groupId, stm, etm);
	}

	@Override
	@Path("/zone/{gfId}")
	@GET
	public String getSingle(@PathParam("gfId") long gfId) {
		// TODO Auto-generated method stub
		return geofenceService.findGeojsonById(gfId);
	}

	@Override
	@POST
	@Path("/zone")
	@Consumes("text/plain")
	public void addSingle(String reqData) {
		// TODO Auto-generated method stub
		logger.info("///\\" + reqData);
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {			
			Map<String,Object> reqDataPair = mapper.readValue(reqData, Map.class);
			
			String gjStr = (String) reqDataPair.get("geometry");
			
			GfThumbnail tn = new GfThumbnail();
			tn.setUrl((String) reqDataPair.get("img_url"));
			
			long tnId = gfThumbnailService.create(tn);
			
			geofenceService.createGeofenceFromGeoJson(1, "web", "", 0, gjStr, tnId);
			
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}

	@Override
	@Path("vehicle2/company/{companyId}")
	@GET
	public String getVehicleByCompany(@PathParam("companyId") String companyId) {
		// TODO Auto-generated method stub
		return vehicleSvrs.findMinimalVehicleByCompany(companyId);
	}

	@Override
	@Path("trip/company/{companyId}/{timeRange}")
	@GET
	public String findCompanyTripByTime(@PathParam("companyId") long companyId,
			@PathParam("timeRange") String timeRange) {
		List<Locator> locators = locatorService.findLocatorByAssignTo(companyId);
		List<Trip> trips = new ArrayList<Trip>();
		
		Date _dtStart = null, _dtEnd = null;
		
		if (timeRange != null && timeRange != ""){
			String [] timeslice = timeRange.split(",");
		
			String stm = timeslice[0];
			String etm = timeslice[1];
			
			try {
				_dtStart = sdf.parse(stm);
				_dtEnd = sdf.parse(etm);
				_dtEnd = new Date(_dtEnd.getTime() + 24*3600*1000);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {//default will retrieve last 1 days
			Calendar c = Calendar.getInstance();
			c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
			_dtEnd = c.getTime();
			
			c.setTimeInMillis(c.getTimeInMillis() - 24 * 3600 * 1000);
			_dtStart = c.getTime();
		}		
			
		for (Locator l : locators){
			List<Trip> singleTrips = tripService.findByTime(l.getId(), _dtStart, _dtEnd);
			trips.addAll(singleTrips);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = null;
		
		try {
			jsonStr = mapper.writeValueAsString(trips);
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
	
		return jsonStr;
	}

	@Override
	@Path("event/company/{companyId}/{timeRange}")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public List<VehicleEvent> findEventByTime(@PathParam("companyId") long companyId,
			@PathParam("timeRange") String timeRange) {
		Date _dtStart = null, _dtEnd = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		if (timeRange != null){
			String [] timeslice = timeRange.split(",");
		
			String stm = timeslice[0];
			String etm = timeslice[1];
			
			try {
				_dtStart = sdf.parse(stm);
				_dtEnd = sdf.parse(etm);
				_dtEnd = new Date(_dtEnd.getTime() + 24*3600*1000);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		List<VehicleEvent> events = eventSvrs.findVehicleEventbyTime(companyId, _dtStart, _dtEnd);
		
		logger.info("event :" + events.size());
		
		return events;
	}

	@Override
	@Path("vgroup/company/{companyId}")
	@GET
	public String findVehicleGroupByCompany(
			@PathParam("companyId") String companyId) {
		List<VehicleGroup> grps = vgSvrs.findVehicleGroupsByCompany(companyId);
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

	@Override
	@Path("trip-detail/{tripId}")
	@GET
	public String findTripDetailById(@PathParam("tripId") String tripId) {
		// TODO Auto-generated method stub
		List<LocatorLocationHistory> history = locatorService.findLocationHistoryByTrip(Long.parseLong(tripId));
		
		logger.info("find history size ///" + history.size());
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = "";
		
		SerializationConfig serializationConfig = mapper.getSerializationConfig();  
		
		// define mix-in annotations
		serializationConfig.addMixInAnnotations(LocatorLocationHistory.class, SimpleLocationHistoryFilter.class);  
		
		try {
			jsonStr = mapper.writer().writeValueAsString(history);
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
		
		return jsonStr;
	}
}