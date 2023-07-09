/**
 * @author Yang Wei
 * @Date Jul 24, 2013
 */
package com.coordsafe.guardian.ws;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * @author Yang Wei
 *
 */
@Path("/guardian")
public interface GuardianWebService {
	@POST
	@Path("/login")
	public Response login(String name, String password);
	
	@GET
	@Path("/logout")
	public boolean logout(String name);
	
	@POST
	@Path("/register")
	public Response register(@FormParam("name") String name, @FormParam("password") String passwd,
			@FormParam("email") String email, @FormParam("phone") String phone, @QueryParam("corporate") boolean corperate);
	
	@PUT
	@Path("/{guardianId}")
	public Response updateProfile(@PathParam("guardianId") long guardianId, 
			@FormParam("email") String email, @FormParam("phone") String phone);
	
	@POST
	@Path("/{guardianName}")
	public Response changePass(@QueryParam("action") String action, @PathParam("guardianName") String guardianName,
			@HeaderParam("oldPass") String oldPass, @HeaderParam("newPass") String newPass);
	
	@GET
	@Path("/{guardianName}")
	public Response forgetPass(@QueryParam("action") String action, @PathParam("guardianName") String guardianName);
	
	@POST
	@Path("/{guardianId}/location")
	public Response updateLocation(@FormParam("lat") double lat, @FormParam("lng") double lng, @PathParam("guardianId") long guardianId);

	public Response updateLocation2(long guardianId, double lat, double lng);
}
