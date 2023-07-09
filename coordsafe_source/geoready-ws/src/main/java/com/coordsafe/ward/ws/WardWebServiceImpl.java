package com.coordsafe.ward.ws;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.plexus.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.coordsafe.common.SimpleWardHistoryFilter;
import com.coordsafe.constants.Constants;
import com.coordsafe.geofence.entity.Geofence;
import com.coordsafe.geofence.service.GeofenceService;
import com.coordsafe.guardian.entity.Guardian;
import com.coordsafe.guardian.service.GuardianService;
import com.coordsafe.img.IMGService;
import com.coordsafe.locator.entity.Locator;
import com.coordsafe.locator.entity.LocatorLocationHistory;
import com.coordsafe.locator.service.LocatorService;
import com.coordsafe.notification.service.WardNotificationSettingService;
import com.coordsafe.ward.entity.Ward;
import com.coordsafe.ward.service.WardService;

/**
 * @author Yang Wei
 * @Date Dec 7, 2013
 */
public class WardWebServiceImpl implements WardWebService {
	private static Log log = LogFactory.getLog(WardWebServiceImpl.class);
	private ResponseBuilder builder = Response.status(Status.ACCEPTED);
	
	private static final String DEV_IN_USE = "Device already in use"; 
	private static final String DEV_INVALID = "Device not found";
	private static final String WARD_NONEXIST = "Ward does not exsit"; 
	
	String tmp = "resources" + File.separator + "wardphotos" + File.separator;
	
	@Autowired
	private WardService wardSvrs;
	
	@Autowired
	private GuardianService guardianSvrs;
	
	@Autowired
	private IMGService imgSvrs;
	
	@Autowired
	private LocatorService locatorSvrs;
	
	@Autowired
	private WardNotificationSettingService wardNotifSvrs;
	
	@Autowired
	private GeofenceService geofenceSvrs;
		
	@Override
	@POST
	@Consumes("multipart/form-data")
	public Response addWard(@Context MessageContext ctx, @Multipart(value = "name", required = true) String name, 
			@Multipart(value = "imei", required = false) String imei, 
			@Multipart(value = "extension", required = false) String extension, 
			@Multipart(value = "photo", required = false) byte [] imgData) {	
		Ward w = new Ward();
		w.setName(name);
				
		String requestKey = ctx.getHttpHeaders().getRequestHeader("key").get(0);
		
		Guardian g = guardianSvrs.getByApiKey(requestKey);
		
		Locator l = locatorSvrs.findLocatorByImei(imei);
		
		if (l == null){
			builder.entity(DEV_INVALID);
			
			// device is optional, 
			//builder.status(Status.SEE_OTHER);
			
			//return builder.build();
		}
		else {
			// device is already in use
			Ward curWard = wardSvrs.findByLocatorID(l.getId());
			
			if (curWard != null){				
				// if user is individual, device can not be reallocated directly
				if (g.getRole().equals("ROLE_USER")){
					builder.entity(DEV_IN_USE);
					
					builder.status(Status.CONFLICT);
					
					return builder.build();
				}
				else {
					curWard.setLocator(null);
					
					wardSvrs.update(curWard);
					
					w.setLocator(l);
				}
			}
			else {
				w.setLocator(l);
			}
		}
		
		if (imgData == null || imgData.length == 0){
			log.info("No image uploaded..");
		}
		else if (extension == null){
			log.info("No file extension specified");
		}
		else {
			File f = this.byteToFile(ctx, 0, extension, imgData);
			
			try {
				String url_100 = tmp + imgSvrs.cutImage(f, 100, 100);
				String url_32 = tmp + imgSvrs.cutImage(f, 32, 32);
				String url_gray = tmp + imgSvrs.getGrayPicture(f);
				
				w.setPhoto32(url_32);
				w.setPhoto64(url_100);
				w.setPhoto32Grey(url_gray);
				w.setPhotourl(tmp + f.getName());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Set<Guardian> gs = w.getGuardians();
		gs.add(g);
		
		w.setGuardians(gs);
		
		w.setStatus("offline");

		wardSvrs.create(w);
		
		Set<Ward> ws = g.getWards();
		ws.add(w);
		
		g.setWards(ws);
		
		guardianSvrs.update(g);
		
		// copy notification setting from other guardian wards?
		
		builder.entity(" Ward created");
		builder.status(Status.CREATED);
		
		return builder.build();
	}

	@Override
	@DELETE
	@Path("/{wardId}")
	public Response removeWard(@Context MessageContext ctx, @PathParam("wardId") long wardId) {
		Ward w = wardSvrs.findByID(wardId);
		
		String requestKey = ctx.getHttpHeaders().getRequestHeader("key").get(0);
		
		Guardian g = guardianSvrs.getByApiKey(requestKey);
		
		if (w != null) {
			if (g.getWards().contains(w)){
				log.debug("remove ward from guardian..");
				
				g.getWards().remove(w);
				
				guardianSvrs.update(g);
			}
			
			//delete the geofence and ward table - 20140310
			Set<Geofence> gfs = w.getGeofences();
			for(Geofence gf : gfs){
				gf.getWards().remove(w);
				
				geofenceSvrs.update(gf);
			}
			
			// delete ward photos
			
			// delete wardNotification
			wardNotifSvrs.delete(""+wardId);
			
			wardSvrs.delete(w);
			
			builder = Response.status(Status.OK);
			builder.entity("Ward is deleted");
		}
		else {
			log.info("Ward to delete does not exist");
			
			builder = Response.status(Status.MOVED_PERMANENTLY);
			builder.entity(WARD_NONEXIST);
		}
		
		return builder.build();
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.ward.ws.WardWebService#updateWard(com.coordsafe.ward.entity.Ward)
	 */
	@Override
	@PUT
	@Consumes("application/json")
	public void updateWard(Ward ward) {
		wardSvrs.update(ward);
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.ward.ws.WardWebService#updateWardPic(long, java.io.InputStream)
	 */
	@Override
	@POST
	@Consumes("multipart/form-data")
	@Path("/{wardId}/photo")
	public Response updateWardPic(@Context MessageContext ctx, @PathParam("wardId") long wardId, @Multipart("extension") String extension, @Multipart("photo") byte [] imgData) {	
		Ward w = wardSvrs.findByID(wardId);
		
		if (w == null){
			builder.entity("Ward does not exist");
			
			return builder.build();
		}

		File f = this.byteToFile(ctx, wardId, extension, imgData);
		
		log.info("update ward photo.." + f.getAbsolutePath());
		
		try {
			String url_100 = tmp + imgSvrs.cutImage(f, 100, 100);
			String url_32 = tmp + imgSvrs.cutImage(f, 32, 32);
			String url_gray = tmp + imgSvrs.getGrayPicture(f);
			
			w.setPhoto32(url_32);
			w.setPhoto64(url_100);
			w.setPhoto32Grey(url_gray);
			w.setPhotourl(tmp + f.getName());
			
			wardSvrs.update(w);
			
			builder = Response.status(Status.OK);
			
			return builder.build();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return builder.build();
	}

	@Override
	@GET
	@Path("/list/{type}")
	@Produces("application/json")
	public Response listGuardianWards(@HeaderParam("guardianId") long guardianId,  @PathParam("type") String type) {
		// TODO Auto-generated method stub
		Guardian g = guardianSvrs.get((int) guardianId);
		ResponseBuilder builder = Response.ok();
		
		Set<Ward> wards = null;
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = "";
		
		if (g == null){
			builder.status(Status.BAD_REQUEST);
			builder.entity("Guardian not found");
			
			return builder.build();
		}
		
		wards = g.getWards();
		
		try {
			if (type != null && type.equals("full")) {

				jsonStr = mapper.writeValueAsString(wards);

				builder.entity(jsonStr);
			} else {
				/*// return basic ward info
				// use mix-in annotation instead of filter
				SerializationConfig serializationConfig = mapper.getSerializationConfig();

				// define mix-in annotations
				serializationConfig.addMixInAnnotations(Ward.class,SimpleWardFilter.class);

				jsonStr = mapper.writer().writeValueAsString(wards);*/
				List<HashMap<String, String>> custWards = new ArrayList<HashMap<String, String>>();
				
				for (Ward w : wards){
					HashMap<String, String> map = new HashMap<String, String>();
					
					map.put("id", "" + w.getId());
					map.put("name", w.getName());
					map.put("photo64", w.getPhoto64());
					map.put("photo32", w.getPhoto32());
					map.put("status", w.getStatus());
					
					Locator l = w.getLocator();
					
					map.put("locatorId", l == null? "0" : "" + l.getId());
					map.put("imei", l == null? "0": l.getImeiCode());
					map.put("lastUpdate", l == null? "0":String.valueOf(l.getLastLocationUpdate()));
					
					custWards.add(map);
				}
				
				jsonStr = mapper.writeValueAsString(custWards);
				
				builder.entity(jsonStr);
			}
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
		
		return builder.build();
	}

	@Override
	@GET
	@Path("/location")
	@Produces("application/json")
	public Response refreshWards(@HeaderParam("key") String key) {
		// TODO Auto-generated method stub
		Set<Ward> wards = guardianSvrs.getByApiKey(key).getWards();
		List<HashMap<String, String>> locs = new ArrayList<HashMap<String, String>>();
		
		for (Ward w : wards){
			HashMap<String, String> map = new HashMap<String, String>();
			
			Locator l = w.getLocator();
			
			map.put("ward_id", String.valueOf(w.getId()));
			
			if (l != null){
				map.put("lat", String.valueOf(w.getLocator().getGpsLocation().getLatitude()));
				map.put("lng", String.valueOf(w.getLocator().getGpsLocation().getLongitude()));
				// lastUpdate is null
				map.put("time", w.getLocator().getLastLocationUpdate().toString());
			}
			else {
				map.put("lat", "0.0");
				map.put("lng", "0.0");
				map.put("time", new Date().toString());
			}
			
			locs.add(map);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(mapper.writeValueAsString(locs));
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
		
		ResponseBuilder builder = Response.status(Status.OK);
		builder.entity(sb.toString());
		
		return builder.build();
	}
	
	private File byteToFile (MessageContext ctx, long wardId, String extension, byte [] bytes){
		String path = ctx.getServletContext().getRealPath("") + File.separator + "resources" + File.separator + "wardphotos";
		
		File f_parent = new File(path);
		
		if (!f_parent.exists()){
			f_parent.mkdirs();
		}
		
		File f = new File(path + File.separator + wardId + extension);

		
		String otherPath = f.getParentFile().getParentFile().getParentFile().getParent() 
				+ File.separator + Constants.SAFELINK_PATH + File.separator + "resources" + File.separator + "wardphotos";
		
		log.info("update ward photo.." + f.getAbsolutePath() + ", " + otherPath);
		
		try {
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(bytes);
			
			fos.close();
			
			FileUtils.copyFileToDirectory(f.getAbsolutePath(), otherPath);
			
			return f;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	@Path("/{wardId}")
	@PUT
	public Response updateWard(@Context MessageContext ctx, @PathParam("wardId") long wardId, @FormParam("name") String name,
			@FormParam("imei") String imei) {
		Ward w = wardSvrs.findByID(wardId);
		
		String requestKey = ctx.getHttpHeaders().getRequestHeader("key").get(0);
		
		Guardian g = guardianSvrs.getByApiKey(requestKey);
		
		if (w != null){
			if (imei != null && !imei.isEmpty()){
				Locator l = locatorSvrs.findLocatorByImei(imei);
				
				if (l != null){				
					Ward curWard = wardSvrs.findByLocatorID(l.getId());
					
					if (curWard != null){				
						// if user is individual, device can not be reallocated directly
						if (g.getRole().equals("ROLE_USER")){
							builder.entity(DEV_IN_USE);
							builder.status(Status.CONFLICT); // 409
						}
						else {
							curWard.setLocator(null);
							
							w.setLocator(l);
							
							wardSvrs.update(curWard);
							
							builder.entity("Ward is updated");
							builder.status(Status.OK);
						}
					}
					else {
						w.setLocator(l);
						
						builder.entity("Ward is updated");
						builder.status(Status.OK);
					}
				}
				else {
					builder.entity(DEV_INVALID);
				}
			}
			if (name != null && !name.isEmpty()){
				w.setName(name);
				builder.status(Status.OK);
			}
			
			wardSvrs.update(w);	
			
			return builder.build();
		}
		
		builder.entity(WARD_NONEXIST);
		builder.status(Status.BAD_REQUEST);
		
		return builder.build();
	}

	@Override
	@POST
	@Path("/{wardId}/deviceByImei")
	public Response updateWardDeviceByImei(@PathParam("wardId") long wardId, @HeaderParam("imei") String imei) {
		Ward w = wardSvrs.findByID(wardId);
		
		if (w != null){
			if (imei != null){
				if (imei.isEmpty()){
					log.info("imei is empty, remove device.. ");
					
					w.setLocator(null);
				}
				else {
					Locator l = locatorSvrs.findLocatorByImei(imei);
					
					if (l == null){
						builder.entity(DEV_INVALID);
						builder.status(Status.PRECONDITION_FAILED); // 412
						
						return builder.build();
					}
				
					Ward oldWard = wardSvrs.findByLocatorID(l.getId());
					
					if (oldWard != null){
						oldWard.setLocator(null);
						
						wardSvrs.update(oldWard);
					}
					
					w.setLocator(l);
					
					wardSvrs.update(w);
					
					builder.status(Status.OK);
					builder.entity("Ward is updated");
					
					return builder.build();
				}
			}
			else {
				builder.entity("IMEI is blank.. nothing to do");
				builder.status(Status.BAD_REQUEST);
				
				return builder.build();
			}
		}
		
		builder.entity(WARD_NONEXIST);
		builder.status(Status.PRECONDITION_FAILED);
		
		return builder.build();
	}
	
	@Override
	@POST
	@Path("/{wardId}/deviceById")
	public Response updateWardDeviceById(@PathParam("wardId") long wardId, @HeaderParam("locatorId") String locatorId) {
		Ward w = wardSvrs.findByID(wardId);
		
		if (w != null){
			if (locatorId != null){
				if (locatorId.isEmpty()){
					log.info("locatorId is empty, remove device.. ");
					
					w.setLocator(null);
					
					wardSvrs.update(w);
					
					builder.entity("Device is removed");
					builder.status(Status.OK);
					
					return builder.build();
				}
				else {
					Locator l = locatorSvrs.findLocatorById(Long.parseLong(locatorId));
					
					if (l == null){
						builder.entity(DEV_INVALID);
						builder.status(Status.PRECONDITION_FAILED); // 412
						
						return builder.build();
					}
					// remove locator from previous ward
					Ward oldWard = wardSvrs.findByLocatorID(l.getId());
					
					if (oldWard != null){
						oldWard.setLocator(null);
						
						wardSvrs.update(oldWard);
					}
					
					w.setLocator(l);
					
					wardSvrs.update(w);
					
					builder.status(Status.OK);
					builder.entity("Ward is updated");
					
					return builder.build();
				}
			}
			else {
				builder.entity("LocatorId is blank.. nothing to do");
				builder.status(Status.BAD_REQUEST);
				
				return builder.build();
			}
		}
		
		builder.entity(WARD_NONEXIST);
		builder.status(Status.NOT_FOUND);
		
		return builder.build();
	}

	@Override
	@GET
	@Path("/{wardId}/history/{timeRange}")
	@Produces("application/json")
	public String findWardHistoryByTime(@PathParam("wardId") long wardId,
			@PathParam("timeRange") String timeRange) {
		String [] timeslice = timeRange.split(",");
		
		String stm = timeslice[0];
		String etm = timeslice[1];
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");	
		
		Date _dtStart = null;
		Date _dtEnd = null;
		
		try {
			_dtStart = sdf.parse(stm);
			_dtEnd = sdf.parse(etm);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		log.debug("find Ward history: " + wardId + " " + _dtStart + " " + _dtEnd);
		
		List<LocatorLocationHistory> his = locatorSvrs.findHistoryByWardId(wardId, _dtStart, _dtEnd);
		
		List<LocatorLocationHistory> reduced = new ArrayList<LocatorLocationHistory>();
			
		//evenly pick only 20 points by time
		int size = his.size();
		
		log.info("find history: " + size);
			
		if (size <= 20){
			reduced.addAll(his);
		}
		
		else {
			//int stepSize = size/20;
			
			//evenly pick every five mins;
			int stepSize = 5*4;
			int pickCnt = size/stepSize;
			
			log.debug("pick " + pickCnt + " item, step: " + stepSize);
		
			Iterator<LocatorLocationHistory> i = his.iterator();
			
			for (int x = 0; x < pickCnt; x ++){ 				
				reduced.add(i.next());
				
				for (int y = 0; y < stepSize - 1; y ++){
					i.next();
				}
			}
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = "";
		
		try {
			// use mix-in annotation instead of filter
			SerializationConfig serializationConfig = mapper.getSerializationConfig();  
			
			// define mix-in annotations
			serializationConfig.addMixInAnnotations(LocatorLocationHistory.class, SimpleWardHistoryFilter.class);  
			
			jsonStr = mapper.writer().writeValueAsString(reduced);
		} catch (JsonGenerationException e) {
			log.error(e.getStackTrace());
		} catch (JsonMappingException e) {
			log.error(e.getStackTrace());
		} catch (IOException e) {
			log.error(e.getStackTrace());
		}
		
		return jsonStr;
	}

	@Override
	@GET
	@Path("{wardId}/location")
	@Produces("application/json")
	public Response getWardLocation(@HeaderParam("key") String key,
			@PathParam("wardId") long wardId) {
		Ward w = wardSvrs.findByID(wardId);
		
		ResponseBuilder builder = Response.status(Status.OK);
		
		if (w == null || w.getLocator() == null){
			builder.entity("Ward does not exist");
			
			return builder.build();
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		Locator l = w.getLocator();
		
		map.put("ward_id", String.valueOf(w.getId()));
		
		if (l != null){
			map.put("lat", String.valueOf(w.getLocator().getGpsLocation().getLatitude()));
			map.put("lng", String.valueOf(w.getLocator().getGpsLocation().getLongitude()));
			map.put("time", w.getLocator().getLastLocationUpdate().toString());
		}
		else {
			map.put("lat", "0.0");
			map.put("lng", "0.0");
			map.put("time", new Date().toString());
		}
		
		ObjectMapper mapper = new ObjectMapper();
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(mapper.writeValueAsString(map));
			
			builder.entity(sb.toString());
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
		
		return builder.build();
	}

	@Override
	@POST
	@Path("/devices/allocate")
	public Response randomAssignDevice(@HeaderParam("guardianId") long guardianId) {
		Guardian g = guardianSvrs.get((int) guardianId);
		
		ResponseBuilder builder = Response.ok();
		
		HashMap<String, String> deviceMap = new HashMap<String, String>();
		
		if (g != null && g.getCompany() != null){
			Set<Ward> wards = g.getWards();
			
			List<Locator> locators = locatorSvrs.findLocatorByAssignTo(g.getCompany().getId());
			
			int size = Math.min(wards.size(), locators.size());
			
			// random assign locators to wards
			Iterator<Ward> iterator = wards.iterator();
			
			for (int x = 0; x < size; x ++){
				Ward w = iterator.next();
				
				Locator l = locators.get(x);
				
				w.setLocator(l);
				
				wardSvrs.update(w);
				
				deviceMap.put(w.getName(), l.getImeiCode());
				
				log.debug("Assign locator to ward: " + l.getLabel() + " " + w.getName());
			}
			
			ObjectMapper om = new ObjectMapper();
			StringBuilder sb = new StringBuilder();
			
			try {
				sb.append(om.writeValueAsString(deviceMap));
				
				builder.entity(sb.toString());
				
				return builder.build();
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
			
			return null;
		}
		else {
			builder.status(Status.BAD_REQUEST);
			builder.entity("Guardian not found");
			
			return builder.build();
		}		
	}

	@Override
	@GET
	@Path("/{wardId}")
	@Produces("application/json")
	public String getSingleWard(@PathParam("wardId") long wardId) {
		Ward w = wardSvrs.findByID(wardId);
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			return mapper.writeValueAsString(w);
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
		
		return "";
	}

}
