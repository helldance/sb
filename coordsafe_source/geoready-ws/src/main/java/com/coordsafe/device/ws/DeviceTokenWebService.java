package com.coordsafe.device.ws;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.MessageContext;

/**
 * @author Yang Wei
 * @Date Feb 3, 2014
 */
public interface DeviceTokenWebService {
	public Response registerDeviceToken(String token, @Context MessageContext ctx);
	public Response deregisterDeviceToken(String token);
	
	public Response registerAndroidDeviceToken(String token, @Context MessageContext ctx);
	public Response deregisterAndroidDeviceToken(String token);
}
