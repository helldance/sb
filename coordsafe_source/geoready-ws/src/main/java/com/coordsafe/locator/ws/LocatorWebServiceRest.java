package com.coordsafe.locator.ws;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.coordsafe.event.entity.GenericEvent;
import com.coordsafe.event.entity.VehicleEvent;
import com.coordsafe.locator.entity.Locator;
import com.coordsafe.locator.entity.LocatorLocationHistory;
import com.coordsafe.trip.entity.Trip;
import com.coordsafe.trip.entity.TripDetail;

@Path("/")
/*@CrossOriginResourceSharing(
        allowOrigins = "*", 
        allowCredentials = true, 
        exposeHeaders = { "X-custom-3", "X-custom-4" }
   )*/
public interface LocatorWebServiceRest {	
	@GET
	@Path("locator/company/{companyId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Locator> findLocatorByCompany (@PathParam("companyId") long companyCode);
	
	@GET
	@Path("history/{locatorId}/{timeRange}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<LocatorLocationHistory> findLocationHistoryByTime(
			@PathParam("locatorId") String locatorId, @PathParam("timeRange") String timeRange);
	
	@GET
	@Path("location/{locatorId}/{time}")
	@Produces(MediaType.APPLICATION_JSON)
	public LocatorLocationHistory findLocationByTime(
			@PathParam("locatorId") String locatorId, @PathParam("time") String time);
	
	@GET
	@Path("trip/{locatorId}/{timeRange}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Trip> findTripBtwTime(
			@PathParam("locatorId") String locatorId, @PathParam("time") String time);
	
	/*@POST
	@Path("/auth")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login (Identity identity);*/
	
	@POST
	@Path("/login")
	public Response login(@HeaderParam("name") String name, @HeaderParam("password") String password);
	
	@GET
	@Path("trip-detail-old/{locatorId}/{time}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TripDetail> findTripDetailByTime(
			@PathParam("locatorId") String locatorId, @PathParam("time") String time);
	
	@GET
	@Path("trip-detail/{locatorId}/{time}")
	public String findTripDetailByTimeShort(
			@PathParam("locatorId") String locatorId, @PathParam("time") String time, @FormParam("full") boolean full);
	
	@Path("trip-detail/{tripId}")
	@GET
	public String findTripDetailById (@PathParam("tripId") String tripId);
	
	@GET
	@Path("mileage/{locatorId}/{time}")
	public double getMileageByTime(
			@PathParam("locatorId") String locatorId, @PathParam("time") String time);
	
	@GET
	@Path("move/{locatorId}/{time}")
	public double getMovingTimeByTime(
			@PathParam("locatorId") String locatorId, @PathParam("time") String time);
	
	@Path("trip/group/{groupId}/{timeRange}")
	@GET
	public String findGroupTripByTime(@PathParam("groupId") long groupId, @PathParam("timeRange") String timeRange);
	
	@Path("trip/company/{companyId}/{timeRange}")
	@GET
	public String findCompanyTripByTime(@PathParam("companyId") long companyId, @PathParam("timeRange") String timeRange);
	
	@Path("event/company/{companyId}/{timeRange}")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public List<VehicleEvent> findEventByTime(@PathParam("companyId") long companyId, @PathParam("timeRange") String timeRange);
	
	@Path("/zone/{gfId}")
	@GET
	public String getSingle (@PathParam("gfId") long gfId);
	
	@POST
	@Path("/zone")
	@Consumes(MediaType.TEXT_PLAIN)
	public void addSingle (String gjStr);
	
	@Path("vehicle/company/{companyId}")
	@GET
	public String getVehicleByCompany (@PathParam("companyId") String companyId);
	
	@Path("vgroup/company/{companyId}")
	@GET
	public String findVehicleGroupByCompany(@PathParam("companyId") String companyId);

	@Path("locator/company/{companyId}/basic")
	@GET
	String findLocatorByCompanyShort(@PathParam("companyId") long companyCode);

	String findLocatorByCompanyVeryShort(long companyCode);
}
