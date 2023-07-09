package com.coordsafe.device.ws;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.coordsafe.guardian.entity.Guardian;
import com.coordsafe.guardian.service.GuardianService;
import com.coordsafe.remote.service.DeviceRegistrationService;

/**
 * @author Yang Wei
 * @Date Feb 3, 2014
 */
@Path("/device")
public class DeviceTokenWebServiceImpl implements DeviceTokenWebService {
	@Autowired DeviceRegistrationService deviceTokenSvrs;
	@Autowired GuardianService guardianSvrs;

	/* (non-Javadoc)
	 * @see com.coordsafe.device.DeviceTokenService#registerDeviceToken(java.lang.String)
	 */
	@Override
	@Path("/register")
	@POST
	public Response registerDeviceToken(@FormParam("token") String token, @Context MessageContext ctx) {
		String requestKey = ctx.getHttpHeaders().getRequestHeader("key").get(0);
		ResponseBuilder builder;
		
		Guardian g = guardianSvrs.getByApiKey(requestKey);
		
		if (!(token == null || token.isEmpty() || token.equals("null"))){
			if (deviceTokenSvrs.findByToken(token) != null){
				deviceTokenSvrs.deregisterDevice(token);
			}
			
			deviceTokenSvrs.registerDevice(token, g);
		
			builder = Response.status(Status.CREATED);
		}
		else {
			builder = Response.status(Status.BAD_REQUEST);
		}
		
		return builder.build();
	}

	@Override
	@Path("/deregister")
	@POST
	public Response deregisterDeviceToken(@FormParam("token") String token) {
		deviceTokenSvrs.deregisterDevice(token);
		
		ResponseBuilder builder = Response.status(Status.OK);
		return builder.build();
	}

	@Override
	@Path("/register/android")
	@POST
	public Response registerAndroidDeviceToken(@FormParam("token") String token,
			@Context MessageContext ctx) {
		String requestKey = ctx.getHttpHeaders().getRequestHeader("key").get(0);
		ResponseBuilder builder;
		
		Guardian g = guardianSvrs.getByApiKey(requestKey);
		
		if (!(token == null || token.isEmpty() || token.equals("null"))){
			if (deviceTokenSvrs.findAndroidByToken(token) != null){
				deviceTokenSvrs.deregisterAndroidDevice(token);
			}
			
			deviceTokenSvrs.registerAndroidDevice(token, g);
		
			builder = Response.status(Status.CREATED);
		}
		else {
			builder = Response.status(Status.BAD_REQUEST);
			builder.entity("Null token");
		}
		
		return builder.build();
	}

	@Override
	@Path("/deregister/android")
	@POST
	public Response deregisterAndroidDeviceToken(@FormParam("token") String token) {
		deviceTokenSvrs.deregisterAndroidDevice(token);
		
		ResponseBuilder builder = Response.status(Status.OK);
		return builder.build();
	}

}
