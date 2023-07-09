package com.coordsafe.wardevent.ws;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.coordsafe.wardevent.entity.WardEvent;

/**
 * @author Yang Wei
 * @Date Dec 13, 2013
 */
@Path("/wardevent")
public interface WardEventWebService {
	@GET
	@Path("/{wardId}/{queryTime}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<WardEvent> getEventByTime (@PathParam("wardId") long wardId, @PathParam("queryTime") String time);
	
	public Response createEvent (WardEvent event);
}
