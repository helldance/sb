package com.coordsafe.geofence.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.coordsafe.geofence.entity.Geofence;
import com.coordsafe.geofence.entity.GfThumbnail;
import com.coordsafe.geofence.service.GeofenceService;
import com.coordsafe.geofence.service.GfThumbnailService;
import com.coordsafe.guardian.entity.Guardian;
import com.coordsafe.guardian.service.GuardianService;
import com.coordsafe.ward.entity.Ward;
import com.coordsafe.ward.service.WardService;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderStatus;
import com.google.code.geocoder.model.LatLng;

/**
 * @author Yang Wei
 * @Date Dec 7, 2013
 */
public class GeofenceWebServiceImpl implements GeofenceWebService {
	private static Log log = LogFactory.getLog(GeofenceWebServiceImpl.class);
	private ResponseBuilder builder;
	
	final Geocoder geocoder = new Geocoder();
	
	@Autowired
	private GeofenceService geofenceSvrs;
	
	@Autowired
	private GuardianService guardianSvrs;
	
	@Autowired
	private WardService wardSvrs;
	
	@Autowired
	private GfThumbnailService tnSvrs;
	
	/* (non-Javadoc)
	 * @see com.coordsafe.geofence.ws.GeofenceWebService#findByOwnerId(long)
	 */
	@Override
	@Path("/guardian/{guardianName}")
	@GET
	@Produces("application/json")
	public String findByOwnerName(@PathParam("guardianName") String guardianName) {
		List<Geofence> unsortedFences = geofenceSvrs.findGeofenceByCreateBy(guardianName);
		
		return sortGeofenceData(unsortedFences);
	}

	private String sortGeofenceData(Collection<Geofence> unsortedFences) {
		List<HashMap<String, String>> sortedFences = new ArrayList<HashMap<String, String>>();
		
		ObjectMapper mapper = new ObjectMapper();
		
		for (Geofence gf : unsortedFences){
			HashMap<String, String> map = new HashMap<String, String>();
			
			map.put("id", gf.getId() + "");
			map.put("description", gf.getDescription()== null?"":gf.getDescription());
			map.put("circleRadius", gf.getCircleRadius() + "");
			map.put("url", gf.getThumbnail() != null?(gf.getThumbnail().getUrl() == null? "":gf.getThumbnail().getUrl()):"");
			map.put("createBy", gf.getCreateBy());
			map.put("createDt", String.valueOf(gf.getCreateDt()));
			
			// read center coordinates from another call.
			String geoJson = geofenceSvrs.findGeojsonById(gf.getId());
			
			try {
				HashMap<String, Object> circle = new HashMap<String, Object>();
				
				circle = mapper.readValue(geoJson, HashMap.class);
				
				HashMap<String, Object> coord = (HashMap<String, Object>) circle.get("geometry");
				
				String coordinates = coord.get("coordinates").toString();
								
				String [] latLng = coordinates.substring(1, coordinates.length() - 1).split(",");
				
				String lat = latLng[0].trim();
				String lng = latLng[1].trim();
				
				map.put("lat", ""  + lat);
				map.put("lng", ""  + lng);
				
				// add reverse geocoded result
				/*if (lat != null && lng != null){
					GeocoderRequest gcReq = new GeocoderRequestBuilder().
							setLocation(new LatLng(lat, lng)).getGeocoderRequest();
					GeocodeResponse geocoderResponse = geocoder.geocode(gcReq);
					 
					log.info(geocoderResponse.getResults());
					 
					if (geocoderResponse.getStatus() == GeocoderStatus.OK){
						//TODO discard country and zipcode
						map.put("address", geocoderResponse.getResults().get(0).getFormattedAddress());
					}
					else {
						map.put("address", "Fail to retrieve address");
					}
				}
				else {
					map.put("address", "Invalid address");
				}*/
				
				map.put("address", gf.getAddress());
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			sortedFences.add(map);
		}
				
		try {
			return mapper.writeValueAsString(sortedFences);
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

	/* (non-Javadoc)
	 * @see com.coordsafe.geofence.ws.GeofenceWebService#deleteById(long)
	 */
	@Override
	@Path("/{geofenceId}")
	@DELETE
	public Response deleteById(@PathParam("geofenceId") long geofenceId) {
		Geofence g = geofenceSvrs.findById(geofenceId);
			
		if (g != null){
			geofenceSvrs.delete(g);
			
			builder = Response.status(Status.OK);
		}
		else {
			log.info("geofence to delete does not exist");
			
			builder = Response.status(Status.MOVED_PERMANENTLY);
			builder.entity("geofence does not exist");
		}
		
		return builder.build();
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.geofence.ws.GeofenceWebService#deleteById(long, java.lang.String, long)
	 */
	@Override
	@Path("/{geofenceId}/{action}")
	@POST
	public Response manageGeofence(@PathParam("geofenceId") long geofenceId,
			@PathParam("action") String action,	@HeaderParam("wardId") long wardId) {
		Geofence g = geofenceSvrs.findById(geofenceId);
		Ward w = wardSvrs.findByID(wardId);
		
		if (g == null){
			log.info("geofence is invalid");
			
			builder = Response.status(Status.MOVED_PERMANENTLY);
			builder.entity("Geofence not exist");
			
			return builder.build();
		}
		else if (w == null){
			log.info("ward is invalid");
			
			builder = Response.status(Status.MOVED_PERMANENTLY);
			builder.entity("Ward not exist");
			
			return builder.build();
		}
		else {
			//
			Set<Ward> wards = g.getWards();
			
			log.debug(String.format("Geofence %s has %s wards",  geofenceId, wards.size()));
						
			Set<Geofence> gfSet = w.getGeofences();
			
			log.debug(String.format("Ward %s has %s geofences",  wardId, gfSet.size()));
			
			if (action.equals("add")){
				// check if geofence is already added?
				if (gfSet.contains(g)){
					builder.entity("Geofence is already assigned");					
				}
				else {
					gfSet.add(g);
				
					wards.add(w);
				}
			}
			else if (action.equals("remove")){
				gfSet.remove(g);
				
				wards.remove(w);
			}
			else {
				log.info("action is invalid");
				
				builder = Response.status(Status.NOT_ACCEPTABLE);
				
				return builder.build();
			}
			
			log.debug("after add | remove: " + gfSet.size());
			
			w.setGeofences(gfSet);
			g.setWards(wards);
			
			geofenceSvrs.update(g);			
			wardSvrs.update(w);
			
			builder = Response.status(Status.OK);
		}
		
		return builder.build();
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.geofence.ws.GeofenceWebService#listGeofenceUser(long)
	 */
	@Override
	@Path("/{geofenceId}/user")
	@Produces("application/json")
	@GET
	public Response listGeofenceUser(@PathParam("geofenceId") long geofenceId) {
		Geofence g = geofenceSvrs.findById(geofenceId);
		
		builder = Response.status(Status.OK);
		
		if (g != null){
			//return g.getWards();
			List<HashMap<String, String>> custWards = new ArrayList<HashMap<String, String>>();
			
			for (Ward w : g.getWards()){
				HashMap<String, String> map = new HashMap<String, String>();
				
				// ideally only id is needed
				map.put("id", "" + w.getId());
				map.put("name", w.getName());
				map.put("photo64", w.getPhoto64());
				map.put("photo32", w.getPhoto32());
				map.put("status", w.getStatus());
				
				//Locator l = w.getLocator();
				
				//map.put("locatorId", l == null? "0" : "" + l.getId());
				//map.put("imei", l == null? "0": l.getImeiCode());
				
				custWards.add(map);
			}
			
			ObjectMapper mapper = new ObjectMapper();
			
			String jsonStr = "";
			
			try {
				jsonStr = mapper.writeValueAsString(custWards);
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
			
			builder.entity(jsonStr);		
		}
		else {
			builder.entity("Geofence not exist");
			builder.status(Status.BAD_REQUEST);
		}
		
		return builder.build();
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.geofence.ws.GeofenceWebService#getUserGeofences(long)
	 */
	@Override
	@Path("/ward/{wardId}")
	@GET
	@Produces("application/json")
	public String getUserGeofences(@PathParam("wardId") long wardId) {
		Ward w = wardSvrs.findByID(wardId);
		
		if (w != null){
			return sortGeofenceData(w.getGeofences());
		}
		
		return "";
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.geofence.ws.GeofenceWebService#createCircularGeofence(double[][], double)
	 */
	@Override
	@Path("/circle")
	@POST
	public Response createCircularGeofence(@HeaderParam("key") String key, 
			@FormParam("lat") double lat, @FormParam("lng") double lng, @FormParam("radius") String radius, @FormParam("thumbnail") String thumbnail,
			@FormParam("name") String name) {
		ResponseBuilder resBuilder = Response.accepted();
		
		Guardian g = guardianSvrs.getByApiKey(key);
		
		if (g != null){
		
			if (lat == 0.0 || lng == 0.0 || radius == null || radius.isEmpty()){
				resBuilder.entity("Missing either \"latitude\" or \"longitude\" or \"radius\" value");
				resBuilder.status(Status.BAD_REQUEST);
				
				return resBuilder.build();
			}
			
			StringBuilder strBuilder = new StringBuilder();
			
			GfThumbnail tn = new GfThumbnail();
			tn.setUrl(thumbnail);
			
			long tnId = tnSvrs.create(tn);		
			
			// create formatted address
			String formattedAddr = geoformatAddr(lat, lng);

			strBuilder.append("{\"type\": \"Point\", \"coordinates\":[")
				.append(lat).append(", ")
				.append(lng).append("]}");
			
			String geometry = strBuilder.toString();
			
			log.info("Geometry JSON: " + geometry);
			
			geofenceSvrs.createGeofenceFromGeoJson(1, g.getLogin(), formattedAddr, 2, geometry, tnId, "circle", name, radius, "" + g.getId());
			
			resBuilder.status(Status.CREATED);
		}
		
		return resBuilder.build();
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.geofence.ws.GeofenceWebService#createGeofence(com.coordsafe.geofence.entity.Geofence)
	 */
	@Override
	@POST
	@Consumes("application/json")
	public Response createGeofence(Geofence geofence) {
		// TODO Auto-generated method stub
		builder = Response.status(Status.FORBIDDEN);
		
		builder.entity("Not implemented");
		
		return builder.build();
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.geofence.ws.GeofenceWebService#updateCircularGeofence(double[][], double, long)
	 */
	@Override
	@Path("/{geofenceId}")
	@PUT
	public Response updateCircularGeofence(
			@FormParam("lat") double lat, @FormParam("lng") double lng, @FormParam("radius") String radius, 
			@FormParam("thumbnail") String thumbnail,
			@FormParam("name") String name, @PathParam("geofenceId") long geofenceId) {
		ResponseBuilder resBuilder = Response.accepted();
		
		Geofence g = geofenceSvrs.findById(geofenceId);
		
		GfThumbnail tn = new GfThumbnail();
		tn.setUrl(thumbnail);
		
		//long tnId = tnSvrs.create(tn);
		
		g.setThumbnail(tn);
		
		if (name != null && !name.isEmpty()){
			g.setDescription(name);
		}
		
		if (radius != null){
			g.setCircleRadius(Double.valueOf(radius));
		}
		
		if (lat != 0.0 && lng != 0.0){
			g.setAddress(geoformatAddr(lat, lng));
			
			StringBuilder strBuilder = new StringBuilder();
			
			strBuilder.append("{\"type\": \"Point\", \"coordinates\":[")
				.append(lat).append(", ")
				.append(lng).append("]}");
			
			String geometry = strBuilder.toString();
					
			geofenceSvrs.updateGeofenceFromGeoJson(geofenceId, geometry);
		}

		geofenceSvrs.update(g);
		
		resBuilder.status(Status.OK);
		
		return resBuilder.build();
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.geofence.ws.GeofenceWebService#getUserLimit(long)
	 */
	@Override
	@GET
	@Path("/limit")
	public int getUserLimit(@HeaderParam("guardianId") long guardianId) {
		// TODO Auto-generated method stub
		Guardian g = guardianSvrs.get((int) guardianId);
		
		return (g == null) ?0: g.getZoneLimit();
	}

	@Override
	@Path("/{geofenceId}/json")
	@Produces("application/json")
	@GET
	public String getJsonForm(@PathParam("geofenceId") long geofenceId) {
		// TODO Auto-generated method stub
		return geofenceSvrs.findGeojsonById(geofenceId);
	}

	@Override
	@Path("/ward/{wardId}/{action}")
	@POST
	public Response manageGroupGenfenceforWard(
			@PathParam("wardId") long wardId,
			@PathParam("action") String action,
			@HeaderParam("geofenceIds") long[] gfIds) {
		if (wardSvrs.findByID(wardId) == null){
			builder.entity("Ward does not exist");
			builder.status(Status.NOT_FOUND);
			
			log.info("Set geofences for: " + gfIds.length + " geofences");
			
			return builder.build();
		}
		
		StringBuilder sb = new StringBuilder("Following geofences not found: ");
		
		for (Long gfId : gfIds){
			if (geofenceSvrs.findById(gfId) == null){
				sb.append(gfId).append(", ");
			}
			else {
				this.manageGeofence(gfId, action, wardId);
			}
		}
		
		builder.status(Status.OK);
		builder.entity(sb.toString());
		
		return builder.build();
	}

	@Override
	@Path("/{geofenceId}/group/{action}")
	@POST
	public Response manageGeofenceforGroupOfWard(
			@PathParam("geofenceId") long geofenceId, @PathParam("action") String action,
			@HeaderParam("wardId") String wardIds) {
		// TODO Auto-generated method stub
		log.info("Set geofence for " + wardIds);
		
		String [] wds = wardIds.split("/");
		
		for (String wid : wds){
			boolean result = this.setGeofenceToWard(geofenceId, Long.parseLong(wid), action);
			
			log.info("Group add: " + wds.length);
		}
		
		ResponseBuilder builder = Response.ok();
		
		return builder.build();
	}
	
	boolean setGeofenceToWard (long geofenceId, long wardId, String action){
		Geofence g = geofenceSvrs.findById(geofenceId);
		Ward w = wardSvrs.findByID(wardId);
		
		if (g == null){
			log.info("geofence is invalid");

			return false;
		}
		else if (w == null){
			log.info("ward is invalid");
			
			return false;
		}
		else {
			//
			Set<Ward> wards = g.getWards();
			
			log.debug(String.format("Geofence %s has %s wards",  geofenceId, wards.size()));
						
			Set<Geofence> gfSet = w.getGeofences();
			
			log.debug(String.format("Ward %s has %s geofences",  wardId, gfSet.size()));
			
			if (action.equals("add")){
				// check if geofence is already added?
				if (gfSet.contains(g)){
					builder.entity("Geofence is already assigned");					
				}
				else {
					gfSet.add(g);
				
					wards.add(w);
				}
			}
			else if (action.equals("remove")){
				gfSet.remove(g);
				
				wards.remove(w);
			}
			else {
				log.info("action is invalid");
				
				return false;
			}
			
			log.debug("after add | remove: " + gfSet.size());
			
			w.setGeofences(gfSet);
			g.setWards(wards);
			
			geofenceSvrs.update(g);			
			wardSvrs.update(w);
			
			return true;
		}
	}
	
	private String geoformatAddr (double lat, double lng){
		// create formatted address

		GeocoderRequest gcReq = new GeocoderRequestBuilder().
				setLocation(new LatLng(String.valueOf(lat), String.valueOf(lng))).getGeocoderRequest();
		GeocodeResponse geocoderResponse = geocoder.geocode(gcReq);
		 
		log.info(geocoderResponse.getResults());
		
		String formattedAddr = "";
		 
		if (geocoderResponse.getStatus() == GeocoderStatus.OK){
			//TODO discard country and zipcode
			formattedAddr = geocoderResponse.getResults().get(0).getFormattedAddress();
		}
		else {
			formattedAddr = "Sorry, if Google does not have the address, neither do we";
		}
		
		return formattedAddr;
	}
}
