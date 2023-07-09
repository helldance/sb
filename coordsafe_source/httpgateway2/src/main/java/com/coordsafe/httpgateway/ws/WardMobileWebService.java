package com.coordsafe.httpgateway.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.coordsafe.httpgateway.entity.Locator;
import com.coordsafe.httpgateway.entity.PanicAlarm;
import com.coordsafe.httpgateway.entity.Status;
//import com.coordsafe.locator.entity.LocatorLocationHistory;

@Path("/")
public interface WardMobileWebService {	
	@POST
	@Path("/location")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendLocation(Locator locator);
	
	@POST
	@Path("/locator")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticate (Locator locator);
	
	@POST
	@Path("/status")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendStatus (Status status);
	
	@POST
	@Path("/panic")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendPanicAlarm (PanicAlarm alarm);
}
