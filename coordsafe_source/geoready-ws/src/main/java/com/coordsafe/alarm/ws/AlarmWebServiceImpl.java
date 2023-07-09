package com.coordsafe.alarm.ws;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.coordsafe.guardian.entity.Guardian;
import com.coordsafe.guardian.service.GuardianService;
import com.coordsafe.locator.entity.PanicAlarm;
import com.coordsafe.locator.service.LocatorService;
import com.coordsafe.ward.entity.Ward;
import com.coordsafe.ward.service.WardService;

/**
 * @author Yang Wei
 * @Date Jan 8, 2014
 */
@Path("/alarm")
public class AlarmWebServiceImpl implements AlarmWebService {
	@Autowired private LocatorService locatorSvrs;
	@Autowired private WardService wardSvrs;
	@Autowired private GuardianService guardianSvrs;
	
	@Override
	@GET
	@Path("ward/{wardId}/{queryTime}")
	@Produces("application/json")
	public List<PanicAlarm> getAlarmByTime(@PathParam("wardId") long wardId,
			@PathParam("queryTime") String time) {
		String [] splitted = time.split(",");
		
		if (splitted.length == 2){		
			Date _dtStart = null, _dtEnd = null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			
			try {
				_dtStart = sdf.parse(splitted[0]);
				_dtEnd = sdf.parse(splitted[1]);
				
				return locatorSvrs.findPanicAlarmByTime(wardId, _dtStart, _dtEnd);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return new ArrayList<PanicAlarm>();
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.alarm.ws.AlarmWebService#createAlarm(com.coordsafe.locator.entity.PanicAlarm)
	 */
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createAlarm(@Context MessageContext ctx, PanicAlarm alarm) {
		 ResponseBuilder builder = Response.accepted();
		 
		 //ObjectMapper mapper = new ObjectMapper();
		 
		 String wardId = ctx.getHttpHeaders().getHeaderString("wardId");
		 
		 //TODO skip separate set. include in json directly
		 alarm.setWard(wardSvrs.findByID(Long.parseLong(wardId)));
		 
		 locatorSvrs.createPanicAlarm(alarm);
		 
		 builder.status(Status.CREATED);
		 builder.entity("Alarm logged");
		 
		 return builder.build();
	}

	@Override
	@GET
	@Path("/{alarmId}/acknowledge")
	public Response dimissAlarm(@PathParam("alarmId") long alarmId) {
		locatorSvrs.acknowledgePanicAlarm(locatorSvrs.findPanicAlarmByID(String.valueOf(alarmId)));
		
		ResponseBuilder builder = Response.ok();
		return builder.build();
	}

	@Override
	@GET
	@Path("guardian2/{guardianId}/{queryTime}")
	@Produces("application/json")
	public List<PanicAlarm> getAlarmInboxByTime(@PathParam("guardianId") long guardianId,
			@PathParam("queryTime") String time) {
		Guardian g = guardianSvrs.get((int) guardianId);
		Set<Ward> wards = new HashSet<Ward>();
		
		List<PanicAlarm> alarms = new ArrayList<PanicAlarm>();
		
		if (g != null){
			wards = g.getWards();
		}
			
		String [] splitted = time.split(",");
		
		if (splitted.length == 2){		
			Date _dtStart = null, _dtEnd = null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			
			try {
				_dtStart = sdf.parse(splitted[0]);
				_dtEnd = sdf.parse(splitted[1]);
				
				for (Ward w : wards){
					alarms.addAll(locatorSvrs.findPanicAlarmByTime(w.getId(), _dtStart, _dtEnd));
				}
					
				return alarms;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return new ArrayList<PanicAlarm>();
	}

	@Override
	@GET
	@Path("/{guardianId}/acknowledge-all")
	public Response dimissAllAlarm(@PathParam("guardianId") long guardianId) {
		Guardian g = guardianSvrs.get((int) guardianId);
		Set<Ward> wards = new HashSet<Ward>();
		
		ResponseBuilder builder = Response.ok();
		
		List<PanicAlarm> alarms = new ArrayList<PanicAlarm>();
		
		if (g != null){
			wards = g.getWards();
		}
		
		for (Ward w : wards){
			alarms.addAll(locatorSvrs.findNonAcknowledgePanicAlarmByWard(w, false));
		}
		
		for (PanicAlarm alarm : alarms){
			locatorSvrs.acknowledgePanicAlarm(alarm);
		}
		
		return builder.build();
	}

	@Override
	@GET
	@Path("guardian/{guardianId}/count/{count}")
	@Produces("application/json")
	public List<PanicAlarm> getAlarmByCount(@PathParam("guardianId") long guardianId, @PathParam("count") int count) {
		Guardian g = guardianSvrs.get((int) guardianId);
		
		if (g != null){
			Set<Ward> wards = g.getWards();
			
			long [] wardIds = new long [wards.size()];
			Iterator<Ward> i = wards.iterator();
			
			for (int x = 0; x < wards.size(); x ++){
				wardIds[x] = i.next().getId();
			}
			
			return locatorSvrs.findPanicAlarmByCount(wardIds, count);
		}
		else {
			return new ArrayList<PanicAlarm>();
		}
		
	}

	@Override
	@GET
	@Path("/guardian/{guardianId}/region/{region}")
	public Response getAlarmBySection(@PathParam("guardianId") long guardianId,
			@PathParam("region") String region, @QueryParam("lastMin") long lastMin) {
		String [] regions = region.split(",");
		
		Guardian g = guardianSvrs.get((int) guardianId);
		
		if (g != null){
			Set<Ward> wards = g.getWards();
			
			long [] wardIds = new long [wards.size()];
			Iterator<Ward> i = wards.iterator();
			
			for (int x = 0; x < wards.size(); x ++){
				wardIds[x] = i.next().getId();
			}
			
			//return locatorSvrs.findPanicAlarmByRegion(wardIds, regions[0], regions[1], lastMin);
			return null;
		}
		else {
			return null;
		}
	}

	@Override
	@GET
	@Path("guardian/{guardianId}/{queryTime}")
	@Produces("application/json")
	public String getCustomizeInboxByTime(@PathParam("guardianId") long guardianId, @PathParam("queryTime") String time) {
		Guardian g = guardianSvrs.get((int) guardianId);
		Set<Ward> wards = new HashSet<Ward>();
		String jsonStr = "";
		
		List<PanicAlarm> alarms = new ArrayList<PanicAlarm>();
		
		if (g != null){
			wards = g.getWards();
		}
			
		String [] splitted = time.split(",");
		
		if (splitted.length == 2){		
			Date _dtStart = null, _dtEnd = null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			
			try {
				_dtStart = sdf.parse(splitted[0]);
				_dtEnd = sdf.parse(splitted[1]);
				
				for (Ward w : wards){
					alarms.addAll(locatorSvrs.findPanicAlarmByTime(w.getId(), _dtStart, _dtEnd));
				}
				
				List<HashMap<String, String>> alarmList = new ArrayList<HashMap<String, String>>();
				
				for (PanicAlarm pa : alarms){
					HashMap<String, String> m = new HashMap<String, String>();
					
					m.put("id", pa.getId() + "");
					
					Ward w = pa.getWard();
					m.put("wardName", w == null?"":w.getName());
					
					m.put("type", pa.getAlarmType());
					m.put("message", pa.getAlarmMessage());
					m.put("lat", pa.getLat() + "");
					m.put("lng", pa.getLng() + "");
					m.put("time", pa.getTime().toString());
					m.put("acknowledge", String.valueOf(pa.isAcknowledged()));
					
					alarmList.add(m);
				}
				
				ObjectMapper mapper = new ObjectMapper();
			
				jsonStr = mapper.writeValueAsString(alarmList);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		
		return jsonStr;
	}

}
