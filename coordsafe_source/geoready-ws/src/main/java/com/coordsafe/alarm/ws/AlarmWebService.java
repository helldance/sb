package com.coordsafe.alarm.ws;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.MessageContext;

import com.coordsafe.locator.entity.PanicAlarm;

/**
 * @author Yang Wei
 * @Date Jan 8, 2014
 */
public interface AlarmWebService {
	@GET
	@Path("ward/{wardId}/{queryTime}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PanicAlarm> getAlarmByTime (@PathParam("wardId") long wardId, @PathParam("queryTime") String time);
	
	public Response createAlarm (@Context MessageContext ctx, PanicAlarm alarm);
	
	@GET
	@Path("/{alarmId}/acknowledge")
	public Response dimissAlarm (@PathParam("alarmId") long alarmId);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<PanicAlarm> getAlarmInboxByTime (@PathParam("guardianId") long guardianId, @PathParam("queryTime") String time);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getCustomizeInboxByTime (@PathParam("guardianId") long guardianId, @PathParam("queryTime") String time);
	
	@GET
	@Path("guardian/{guardianId}/count/{count}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PanicAlarm> getAlarmByCount (@PathParam("guardianId") long guardianId, @PathParam("count") int count);
	
	@GET
	@Path("/guardian/{guardianId}/region/{region}")
	public Response getAlarmBySection (@PathParam("guardianId") long guardianId, @PathParam("region") String region, @QueryParam("lastMin") long lastMin);
	
	@GET
	@Path("/{guardianId}/acknowledge-all")
	public Response dimissAllAlarm (@PathParam("guardianId") long guardianId);
}
