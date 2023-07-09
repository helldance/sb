/**
 * @author Yang Wei
 * @Date Jul 24, 2013
 */
package com.coordsafe.guardian.ws;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.JMSException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.coordsafe.api.entity.ApiKey;
import com.coordsafe.api.service.ApiService;
import com.coordsafe.common.entity.LatLng;
import com.coordsafe.common.service.PasswordGenerator;
import com.coordsafe.event.entity.GenericEvent;
import com.coordsafe.event.entity.WardEvent;
import com.coordsafe.guardian.entity.Guardian;
import com.coordsafe.guardian.security.GuardianUser;
import com.coordsafe.guardian.service.GuardianService;
import com.coordsafe.guardian.service.GuardianUserLoginService;
import com.coordsafe.notification.common.PushService;
import com.coordsafe.notification.entity.EventMessage;
import com.coordsafe.notification.entity.WardNotificationSetting;
import com.coordsafe.notification.producer.TopicMessageProducer;
import com.coordsafe.safedistance.entity.Safedistance;
import com.coordsafe.task.SafedistanceTask;

/**
 * @author Yang Wei
 *
 */
@Path("/guardian")
public class GuardianWebServiceImpl implements GuardianWebService {
	private static Log log = LogFactory.getLog(GuardianWebServiceImpl.class);
	
	private static final String USER_NONEXIST = "User does not exist";
	private static final String USER_EXIST = "User already exists";
	private static final String USER_NOKEY = "Unauthorized user, please request an API key to gain access";
	private static final String PASS_OK = "password match";
	private static final String PASS_FAIL = "Wrong password";
	private static final String USER_LOCKED = "You failed 5 attempts, account is locked";
	private static final String USER_CREATION_FAIL = "Failed to create user";
	private static final String PASS_UPDATE_OK = "Password successfully updated";
	private static final String PASS_SENT = "Your new password has been sent to: ";
	
	private ExecutorService execSvrs = Executors.newFixedThreadPool(5);
	
	@Autowired
	private GuardianService guardianSvrs;
	
	@Autowired
	private ApiService apiKeySvrs;
	
	@Autowired
	private GuardianUserLoginService guardianUserLoginService;
	
	@Autowired
	private PushService pushSvrs;
	
	/*@Autowired
	private CoordsafeMailService mailSvrs ;*/
	
	/*@Autowired
	private SMSServiceByHoiio smsSvrs;*/
	
	@Autowired
	private TopicMessageProducer producer;

	/* (non-Javadoc)
	 * @see com.coordsafe.user.ws.UserWebService#login()
	 */
	@Override
	@POST
	@Path("/login")
	public Response login(@HeaderParam("name") String name, @HeaderParam("password") String password){
		ResponseBuilder builder = Response.status(Status.OK);
		builder.entity(false);
			
		Guardian guardian = guardianSvrs.getByNameOrEmail(name);
		
		if (guardian == null){
			builder.entity(USER_NONEXIST);
		}
		
		else {
			ApiKey key = guardian.getKey();	
		
			if (key == null){
				log.info(USER_NOKEY);
				
				builder.entity(USER_NOKEY);
			}
		
			else {// set API key as logged in		
				String passwdEncoded = guardianUserLoginService.getPasswordEncoded(password,
						new GuardianUser(guardian.getId(), guardian.getLogin(), guardian.getPasswd()));
				
				if (passwdEncoded.equals(guardian.getPasswd())){
					log.info("password match");
					
					ObjectMapper mapper = new ObjectMapper();
					String jsonStr = null;
					
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("id", String.valueOf(guardian.getId()));
					map.put("username", guardian.getLogin());
		
					//pull all contact info
					map.put("email", guardian.getEmail());
					map.put("phone", guardian.getPhone());
					map.put("key", key.getKey());
					
					//put role, company info
					map.put("role", guardian.getRole());
					
					if (guardian.getCompany() != null)
						map.put("company", String.valueOf(guardian.getCompany().getId()));
					
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
					
					key.setLoggedIn(true);
					
					apiKeySvrs.update(key);
					
					builder.entity(jsonStr);
				}
				else {
					if (guardian.getRetryCount() >= 4){
						guardian.setAccountNonLocked(false);
						
						guardianSvrs.update(guardian);
						
						builder.entity(USER_LOCKED);
					}
					else {
						guardian.setRetryCount(guardian.getRetryCount() + 1);
						
						builder.entity(PASS_FAIL);
					}
				}
			}
		}
		
		return builder.build();	
	}

	@Override
	@GET
	@Path("/logout")
	public boolean logout(@HeaderParam("name") String name) {
		Guardian guardian = guardianSvrs.get(name);
		
		if (guardian == null){
			return false;
		}
		else {
			ApiKey key = guardianSvrs.get(name).getKey();
		
			if (key != null){
				key.setLoggedIn(false);
				
				return true;
			}
			else {
				return false;
			}
		}		
	}

	@Override
	@POST
	@Path("/{guardianName}")
	public Response changePass(@QueryParam("action") String action,
			@PathParam("guardianName") String guardianName,
			@HeaderParam("oldPass") String oldPass,
			@HeaderParam("newPass") String newPass) {
		Guardian guardian = guardianSvrs.get(guardianName);
		ResponseBuilder builder = Response.status(Status.OK);
		builder.entity(false);
		
		if (guardian == null){
			builder.encoding(USER_NONEXIST);
		}
		else {
			GuardianUser guser = new GuardianUser(guardian.getId(), guardian.getLogin(), guardian.getPasswd());
		
			String passwdEncoded = guardianUserLoginService.getPasswordEncoded(oldPass, guser);
			
			if (passwdEncoded.equals(guardian.getPasswd())){
				log.info(PASS_OK);
				
				guardian.setPasswd(guardianUserLoginService.getPasswordEncoded(newPass, guser));
				
				guardianSvrs.update(guardian);	
				
				builder.entity(PASS_UPDATE_OK);
			}
			else {
				builder.entity(PASS_FAIL);
			}
		}
		
		return builder.build();
	}

	@Override
	@GET
	@Path("/{guardianName}")
	public Response forgetPass(@QueryParam("action") String action,
			@PathParam("guardianName") String guardianName) {
		log.info(guardianName + " request forget password");
		
		if (action.equals("forgetpass")){
			ResponseBuilder builder = Response.accepted();
			
			Guardian g = guardianSvrs.get(guardianName);
			
			if (g != null){
				GuardianUser guser = new GuardianUser(g.getId(),g.getLogin(),g.getPasswd());
				
				String newPass = PasswordGenerator.getMixAlphaNumericPassword(6);
				String passwdEncoded = guardianUserLoginService.getPasswordEncoded(newPass, guser);
				
				log.info(newPass);
				
				g.setPasswd(passwdEncoded);
				
				guardianSvrs.update(g);
				
				// send notification email or sms
				/*GenericEvent event = new GenericEvent(){
					private static final long serialVersionUID = 1L;
				};*/
				
				GenericEvent event = new WardEvent();
				event.setType("ResetPassword");
				event.setMessage("You have sent a reset password request, and your new password is: <b>" + newPass + "</b>, " +
						"you can change it after login");
				
				WardNotificationSetting wns = new WardNotificationSetting();				
				wns.setEventType("ResetPassword");
				
				StringBuilder sb = new StringBuilder();
				
				if (g.getEmail() != null && !g.getEmail().isEmpty()){
					//mailSvrs.sendMail("Reset password", "Your new password for MeLink is: " + newPass, new String [] {g.getEmail()});
					wns.setEmailEnable(true);
					wns.setEmailaddress(g.getEmail());
					
					sb.append(PASS_SENT + g.getEmail());
				}
				else if (g.getPhone() != null && !g.getPhone().isEmpty()){
					//smsSvrs.sent(Arrays.asList(new String [] {g.getPhone()}), "Your new password for MeLink is: " + newPass);
					wns.setSmsEnable(true);
					wns.setMobile(g.getPhone());
					
					sb.append("\n" + PASS_SENT + g.getPhone());
				}
				else {
					// user do not register any email or password, send back directly
					builder.entity(newPass);
					
					//pushSvrs.pushToGcm();
				}
				
				try {
					producer.sendMessages(new EventMessage(event, wns));
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
				builder.entity(sb.toString());
				builder.status(Status.OK);
			}
			else {
				builder.entity("Invalid user");
				builder.status(Status.BAD_REQUEST);
			}
			
			return builder.build();
		}
		
		return null;
	}

	@Override
	@POST
	@Path("/register")
	public Response register(@FormParam("name") String name,
			@FormParam("password") String passwd,
			@FormParam("email") String email, @FormParam("phone") String phone,
			@QueryParam("corporate") boolean corperate) {
		ResponseBuilder builder;
		
		Guardian g = new Guardian();
		
		g.setLogin(name);
		//g.setPasswd(passwd);
		g.setEmail(email);
		g.setPhone(phone);
    	g.setAccountNonExpired(true);
    	g.setAccountNonLocked(true);
    	g.setEnabled(true);
    	
    	// user can register individual or corporate role
    	if (corperate){
    		g.setRole("ROLE_TEACHER");
    	}
    	else {
    		g.setRole("ROLE_USER");
    	}
    	g.setRetryCount(0);		
    	
		//create an API key and assign to new user
		ApiKey key = apiKeySvrs.signupKey(null, 99, null, email);	
		
		log.info("create new key for " + name + ": " + key.getKey());
		
		g.setKey(key);
		
    	//log.info(name + " " + passwd + " " + email + " " + phone);
    	
		// TODO: catch hibernate key exist exception and return to requester
		guardianSvrs.add(g);
		
		Guardian g2 = guardianSvrs.get(name);
		
		if (g2 == null){
			builder = Response.status(Status.SEE_OTHER);
			builder.entity(USER_CREATION_FAIL);
			
			return builder.build();
		}
		
		GuardianUser guser = new GuardianUser(g2.getId(),g2.getLogin(),g2.getPasswd());
		
		String passwdEncoded = guardianUserLoginService.getPasswordEncoded(passwd, guser);
		
		g2.setPasswd(passwdEncoded);
		
		guardianSvrs.update(g2);
	
		builder = Response.status(Status.CREATED);
		return builder.build();
	}

	@Override
	@Path("/{guardianId}")
	@PUT
	public Response updateProfile(@PathParam("guardianId") long guardianId,
			@FormParam("email") String email, @FormParam("phone") String phone) {
		// TODO Auto-generated method stub
		log.info("update guardian profile..");
		
		Guardian g = guardianSvrs.get((int) guardianId);

		if (email != null && !email.isEmpty()){
			g.setEmail(email);
		}
		if (phone != null && !phone.isEmpty()){
			g.setPhone(phone);
		}
		
		guardianSvrs.update(g);
		
		ResponseBuilder builder = Response.status(Status.OK);
		return builder.build();
	}

	@Override
	@POST
	@Path("/{guardianId}/location")
	public Response updateLocation(@FormParam("lat") double lat, @FormParam("lng") double lng, @PathParam("guardianId") long guardianId) {
		Guardian g = guardianSvrs.get((int) guardianId);
		
		log.info(g.getLogin() + " update location: " + lat + ", " + lng);
		
		g.setLocation(new LatLng(lat, lng));
		
		guardianSvrs.update(g);
		
		// check distance;
		Safedistance dist = g.getSafedistance();
		
		if (dist != null){
			new SafedistanceTask(dist, producer).run();
		}
		
		ResponseBuilder builder = Response.status(Status.OK);
		return builder.build();		
	}
	
	@Override
	@GET
	@Path("/{guardianId}/location")
	public Response updateLocation2(@PathParam("guardianId") long guardianId, @QueryParam("lat") double lat, @QueryParam("lng") double lng) {
		Guardian g = guardianSvrs.get((int) guardianId);
		
		log.info(g.getLogin() + " update location: " + lat + ", " + lng);
		
		if (lat != 0 && lng != 0){
			// device has not get gps fix, discard values
			g.setLocation(new LatLng(lat, lng));
			
			guardianSvrs.update(g);
			
			// check distance;
			Safedistance dist = g.getSafedistance();
			
			if (dist != null){
				//new SafedistanceTask(dist, producer).run();
				execSvrs.execute(new SafedistanceTask(dist, producer));
			}
		}
				
		ResponseBuilder builder = Response.status(Status.OK);
		return builder.build();		
	}
}
