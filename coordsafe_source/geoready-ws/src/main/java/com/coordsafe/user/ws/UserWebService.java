/**
 * @author Yang Wei
 * @Date Jul 24, 2013
 */
package com.coordsafe.user.ws;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author Yang Wei
 *
 */
@Path("/user")
public interface UserWebService {
	@POST
	@Path("/login")
	public Response login(String name, String password);
	
	@GET
	@Path("/logout")
	public boolean logout(String name);
}
