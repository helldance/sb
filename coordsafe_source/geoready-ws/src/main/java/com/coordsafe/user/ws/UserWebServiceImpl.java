/**
 * @author Yang Wei
 * @Date Jul 24, 2013
 */
package com.coordsafe.user.ws;

import java.io.IOException;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.exception.UserException;
import com.coordsafe.core.rbac.service.UserService;

/**
 * @author Yang Wei
 *
 */
@Path("/user")
public class UserWebServiceImpl implements UserWebService {
	
	@Autowired
	private UserService userService;

	/* (non-Javadoc)
	 * @see com.coordsafe.user.ws.UserWebService#login()
	 */
	@Override
	@POST
	@Path("/login")
	public Response login(@HeaderParam("name") String name, @HeaderParam("password") String password){
		ResponseBuilder builder = Response.status(Status.OK);
		builder.entity(false);
			
		try {
			User user = userService.login(name, password);
			if  (user != null){
				ObjectMapper mapper = new ObjectMapper();
				String jsonStr = null;
				
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("id", String.valueOf(user.getId()));
				map.put("login_name", user.getUsername());
				//map.put("password", user.getPassword());
				map.put("nick_name", user.getNickName());
				map.put("company", user.getCompany().getName());
				map.put("company_id", String.valueOf(user.getCompany().getId()));
				map.put("contact", user.getContact());
				if (user.getUserInformation()!= null){
					map.put("addr", user.getUserInformation().getHomeAddress());
				}
				if (user.getApiKey()!= null){
					map.put("key", user.getApiKey().getKey());
				}
				
				try {
					jsonStr = mapper.writeValueAsString(map);
				} catch (JsonGenerationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				builder.entity(jsonStr);
			}
		} catch (UserException e) {
			e.printStackTrace();
		}
		
		return builder.build();	
	}

	@Override
	@GET
	@Path("/logout")
	public boolean logout(@PathParam("name") String name) {
		// TODO Auto-generated method stub
		User user = userService.findByUsername(name);
		//user.
		try {
			userService.updateUser(user);
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
}
