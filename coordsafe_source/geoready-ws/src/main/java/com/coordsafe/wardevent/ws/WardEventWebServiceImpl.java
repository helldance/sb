package com.coordsafe.wardevent.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;

import com.coordsafe.wardevent.entity.WardEvent;
import com.coordsafe.wardevent.service.WardEventService;

/**
 * @author Yang Wei
 * @Date Jan 6, 2014
 */
@Path("/wardevent")
public class WardEventWebServiceImpl implements WardEventWebService {
	@Autowired
	private WardEventService wardEventSvrs;
	
	@Override
	@GET
	@Path("/{wardId}/{queryTime}")
	@Produces("application/json")
	public List<WardEvent> getEventByTime(@PathParam("wardId") long wardId, @PathParam("queryTime") String time) {
		String [] splitted = time.split(",");
		
		if (splitted.length == 2){				
			//return wardEventSvrs.findByTime(wardId, splitted[0], splitted[1]);
			return wardEventSvrs.findByWardId(wardId);
		}
		else {
			return new ArrayList<WardEvent>();
		}
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.wardevent.ws.WardEventWebService#createEvent()
	 */
	@Override
	@Consumes("application/json")
	@POST
	public Response createEvent(WardEvent event) {
		ResponseBuilder builder = Response.accepted();
		
		wardEventSvrs.create(event);
		
		builder.status(Status.CREATED);
		builder.entity("Event is logged");
		return builder.build();
	}

}
