package com.coordsafe.im.ws;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.coordsafe.im.entity.InstantMessage;

/**
 * @author Yang Wei
 * @Date Nov 21, 2013
 */
@Path("/message")
public interface MessageWebService {
	@Path("/circle/{cirlceId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<InstantMessage> getCircleMessagesByTime (@PathParam("circleId") long circleId, 
			@HeaderParam("startDt") String startDt, @HeaderParam("endDt") String endDt);
	
	@Path("/guardian/{guardianId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<InstantMessage> getGuardianMessageByTime (@PathParam("guardianId") long guardianId, 
			@HeaderParam("startDt") String startDt, @HeaderParam("endDt") String endDt);
}
