package com.coordsafe.notificationsetting.ws;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
import org.springframework.beans.factory.annotation.Autowired;

import com.coordsafe.guardian.entity.Guardian;
import com.coordsafe.guardian.service.GuardianService;
import com.coordsafe.misc.PATCH;
import com.coordsafe.notification.entity.WardNotificationSetting;
import com.coordsafe.notification.service.WardNotificationSettingService;
import com.coordsafe.ward.entity.Ward;

/**
 * @author Yang Wei
 * @Date Dec 16, 2013
 */
public class WardNotificationSettingWebServiceImpl implements
		WardNotificationSettingWebService {
	private static Log log = LogFactory.getLog(WardNotificationSettingWebServiceImpl.class);
	
	@Autowired
	private WardNotificationSettingService wardNotificationSvrs;
	
	@Autowired
	private GuardianService guardianSvrs;
	
	/* (non-Javadoc)
	 * @see com.coordsafe.notificationsetting.ws.WardNotificationSettingWebService#getWardNotificationSetting(java.lang.String)
	 */
	@Override
	@GET
	@Path("/guardian/{guardianId}")
	@Produces("application/json")
	public List<WardNotificationSetting> getWardNotificationSetting(
			@PathParam("guardianId") String guardianId) {
		Guardian g = guardianSvrs.get(Integer.parseInt(guardianId));
		
		if (g != null){
			Set<Ward> wards = g.getWards();
			
			Iterator<Ward> iterator = wards.iterator();
			
			if (iterator.hasNext()){
				Ward w = wards.iterator().next();

				return wardNotificationSvrs.findByWardName(w.getName());
			}
		}
		
		return new ArrayList<WardNotificationSetting>();
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.notificationsetting.ws.WardNotificationSettingWebService#updateWardNotificationSetting(long)
	 */
	@Override
	@PATCH
	@Consumes("application/json")
	public Response updateWardNotificationSetting(WardNotificationSetting setting) {		
		wardNotificationSvrs.update(setting);
		
		ResponseBuilder builder = Response.status(Status.OK);
		builder.entity("Notification setting is updated");
		
		return builder.build();
	}

	@Override
	@PUT
	@Consumes("application/json")
	public Response batchUpdateWardNotificationSetting(
			List<WardNotificationSetting> settings) {
		log.info("update group notifications..");
		
		for (WardNotificationSetting setting : settings){
			wardNotificationSvrs.update(setting);
		}
		
		ResponseBuilder builder = Response.status(Status.OK);
		builder.entity("Notification settings are updated");
		
		return builder.build();
	}

	@Override
	@POST
	@Consumes("application/json")
	public Response createWardNotificationSetting(@Context MessageContext ctx, List<WardNotificationSetting> settings) {
		String guardianId = ctx.getHttpHeaders().getHeaderString("guardianId");
		ResponseBuilder builder = Response.ok();
		
		Guardian g = guardianSvrs.get(Integer.parseInt(guardianId));
		
		if (g != null){
			String guardianPhone = g.getPhone();
			Set<Ward> wards = g.getWards();
			
			// if guardian has no ward?? create virtual ward?
			
			for (Ward w : wards){
				// remove old settings
				wardNotificationSvrs.delete(String.valueOf(w.getId()));
				
				for (WardNotificationSetting s: settings){
					log.info("Create notification setting for " + w.getName() + " " + s.getDescription());
					
					//s = s.clone();
					
					s.setWard(w);
					wardNotificationSvrs.create(s);
					
					// update missing field -> phone number (DELETE)
					// update guardian phone number (REPLEACE)
					
					if ((guardianPhone == null || guardianPhone.isEmpty()) &&
							s.getMobile() != null && !s.getMobile().isEmpty()){
						log.info("update guardian phone with setting update");
						
						g.setPhone(s.getMobile());
						
						guardianSvrs.update(g);
					}
				}
			}			
			
			builder.status(Status.CREATED);
			
			return builder.build();
		}
		else {
			builder.entity("Guardian not found");
			builder.status(Status.MOVED_PERMANENTLY);
			
			return builder.build();
		}
	}

}
