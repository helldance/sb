/**
 * @author Yang Wei
 * @Date Nov 20, 2013
 */
package com.coordsafe.geofence.ws;

import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.coordsafe.geofence.entity.Geofence;
import com.coordsafe.ward.entity.Ward;

/**
 * @author Yang Wei
 *
 */
@Path("/geofence")
public interface GeofenceWebService {
	@Path("/guardian/{guardianName}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String findByOwnerName(@PathParam("guardianName") String guardianName);
	
	@Path("/{geofenceId}/json")
	@Produces("application/json")
	@GET
	public String getJsonForm (@PathParam("geofenceId") long geofenceId);
	
	@Path("/{geofenceId}")
	@DELETE
	public Response deleteById(@PathParam("geofenceId") long geofenceId);
	
	@Path("/{geofenceId}/{action}")
	@POST
	public Response manageGeofence(@PathParam("geofenceId") long geofenceId, @PathParam("action") String action, 
			@HeaderParam("wardId") long wardId);
	
	@Path("/ward/{wardId}/{action}")
	@POST
	public Response manageGroupGenfenceforWard(@PathParam("wardId") long wardId, @PathParam("action") String action, 
			@HeaderParam("geofenceIds") long [] gfIds);
	
	@Path("/{geofenceId}/group/{action}")
	@POST
	public Response manageGeofenceforGroupOfWard(@PathParam("geofenceId") long geofenceId, @PathParam("action") String action, 
			@HeaderParam("wardId") String wardIds);
	
	@Path("/{geofenceId}/user")
	@GET
	public Response listGeofenceUser(@PathParam("geofenceId") long geofenceId);
	
	@Path("/ward/{wardId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserGeofences(@PathParam("wardId") long wardId);
	
	@Path("/circle")
	@POST
	public Response createCircularGeofence(@HeaderParam("key") String key, 
			@FormParam("lat") double lat, @FormParam("lng") double lng, @FormParam("radius") String radius, @FormParam("thumbnail") String thumbnail,
			@FormParam("name") String name);
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createGeofence(Geofence geofence);
	
	@Path("/{geofenceId}")
	@PUT
	public Response updateCircularGeofence(@FormParam("lat") double lat, @FormParam("lng") double lng, @FormParam("radius") String radius, 
			@FormParam("thumbnail") String thumbnail,
			@FormParam("name") String name, @PathParam("geofenceId") long geofenceId);
	
	@GET
	@Path("/limit")
	public int getUserLimit(@HeaderParam("guardianId") long guardianId);
}
