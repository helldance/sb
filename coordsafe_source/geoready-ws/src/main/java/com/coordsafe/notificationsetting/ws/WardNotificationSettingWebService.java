package com.coordsafe.notificationsetting.ws;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.MessageContext;

import com.coordsafe.misc.PATCH;
import com.coordsafe.notification.entity.WardNotificationSetting;

/**
 * @author Yang Wei
 * @Date Dec 16, 2013
 */
@Path("/notification-setting")
public interface WardNotificationSettingWebService {
	@GET
	@Path("/guardian/{guardianId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<WardNotificationSetting> getWardNotificationSetting (@PathParam("guardianId") String guardianId);
	
	@PATCH
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateWardNotificationSetting(WardNotificationSetting setting);
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response batchUpdateWardNotificationSetting(List<WardNotificationSetting> settings);
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createWardNotificationSetting(@Context MessageContext ctx, List<WardNotificationSetting> settings);
}
