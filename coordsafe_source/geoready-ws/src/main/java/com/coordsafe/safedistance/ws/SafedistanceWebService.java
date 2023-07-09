package com.coordsafe.safedistance.ws;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.MessageContext;

/**
 * @author Yang Wei
 * @Date Feb 3, 2014
 */
public interface SafedistanceWebService {
	public Response addSafedistance(@Context MessageContext ctx, @FormParam("duration") int mins, @FormParam("distance") double distance,
			@FormParam("wardId") String ward);
	public Response deactivateSafedistance(long guardianId);
}
