package com.coordsafe.guardian.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.coordsafe.event.entity.WardEvent;
import com.coordsafe.geofence.entity.Geofence;
import com.coordsafe.guardian.entity.Guardian;
import com.coordsafe.guardian.security.GuardianUser;
import com.coordsafe.guardian.service.GuardianService;
import com.coordsafe.guardian.service.GuardianUserLoginService;
import com.coordsafe.notification.producer.TopicMessageProducer;
import com.coordsafe.ward.entity.Ward;
import com.coordsafe.ward.form.WardForm;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="/guardian")
public class GuardianController
{
    protected static Logger logger = LoggerFactory.getLogger(GuardianController.class);

    @Resource(name="guardianService")
    private GuardianService guardianService;

    @Resource(name="guardianUserLoginService")
    private GuardianUserLoginService guardianUserLoginService;

	@Autowired
	private TopicMessageProducer topicNotificationMessageProducer;
	
    @RequestMapping(value="/register", method = RequestMethod.GET)
    public String register(ModelMap model)
    {
    	logger.info("Register in GET method...");
        model.addAttribute("guardian", new Guardian());
        //model.addAttribute("roles",guardianUserLoginService.getRoles());
        return "register";
    }
    
    @RequestMapping(value="/changePassword",method = RequestMethod.POST)
    public String changePassword(HttpServletRequest request, Model model){
    	logger.info("Change Password in POST method...");
    	String login = (String)request.getParameter("login");
    	String passwd = (String)request.getParameter("passwd");
    	String repasswd = (String)request.getParameter("confirmPassword");
    	if(!passwd.equals(repasswd)){
    		//throws exception
    		return "redirect:/profile";
    	}
    	Guardian guardianInDB = guardianService.get(login);
    	
        GuardianUser gu = new GuardianUser(guardianInDB.getId(),guardianInDB.getLogin(),guardianInDB.getPasswd());
        
        String passwdEncoded = guardianUserLoginService.getPasswordEncoded(passwd, gu);
        
        guardianInDB.setPasswd(passwdEncoded);
        guardianService.add(guardianInDB);
        model.addAttribute("userinfo", guardianInDB);
        return "guardian/profile";
    	
    }
    
    @RequestMapping(value="/edit",method = RequestMethod.POST)
    public void editProfile(HttpServletRequest request, Model model){
    	logger.info("Edit Profile in POST method...");
    	String login = (String)request.getParameter("login");
    	String newPhone = (String)request.getParameter("phone");
    	String newEmail = (String)request.getParameter("email");
    	
    	Guardian guardian = guardianService.get(login);
    	
    	guardian.setPhone(newPhone);
    	guardian.setEmail(newEmail);
    	
    	guardianService.update(guardian);
    	
    	model.addAttribute("userinfo", guardian);
    	
    }

    @RequestMapping(value = "/register_new", method = RequestMethod.POST)
    public String Add(javax.servlet.http.HttpServletRequest request)
    {
    	logger.info("Register in POST method..." + request.getContextPath());
    	String login = (String)request.getParameter("login");
    	String passwd = (String)request.getParameter("password");
    	String repasswd = (String)request.getParameter("repassword");
    	if(!passwd.equals(repasswd)){
    		//throws exception
    		return "redirect:/register_new";
    	}
    	String email = (String)request.getParameter("email");
    	String phone = (String)request.getParameter("phone");
    	
    	Guardian guardian = new Guardian();
    	guardian.setLogin(login);
    	//guardian.setPasswd(passwd);
    	guardian.setEmail(email);
    	guardian.setPhone(phone);
    	guardian.setAccountNonExpired(true);
    	guardian.setAccountNonLocked(true);
    	guardian.setEnabled(true);
    	guardian.setRole("ROLE_USER");
    	   	
        guardianService.add(guardian);
        Guardian guardianInDB = guardianService.get(guardian.getLogin());
        GuardianUser gu = new GuardianUser(guardianInDB.getId(),guardianInDB.getLogin(),guardianInDB.getPasswd());
        
        String passwdEncoded = guardianUserLoginService.getPasswordEncoded(passwd, gu);
        
        guardianInDB.setPasswd(passwdEncoded);
        guardianService.add(guardianInDB);
        
        return "redirect:/";
    }
    
    @RequestMapping(value="/id/{id}", method = RequestMethod.GET)
    public @ResponseBody List<WardForm> getWardsofGuardian(@PathVariable("id") String id){
    	Set<Ward> wards = guardianService.get(Integer.valueOf(id)).getWards();
    	List<WardForm> listWards = new ArrayList<WardForm>();
    	for(Iterator<Ward> it = wards.iterator();it.hasNext();){
    		
    		Ward ward = (Ward)it.next();
    		logger.info("The ward is " + ward.getName());
    		WardForm wf = new WardForm();
    		
    		Set<Geofence> gfs = ward.getGeofences();
    		if(gfs != null && gfs.size() > 0){
        		for(Iterator<Geofence> gfit = gfs.iterator();gfit.hasNext();){
        			Geofence gf = gfit.next();
        			
        			wf.getGfs().add(gf.getZoneType() + " Zone:" +  gf.getDescription());
        		}
        		
    		}else{
    			wf.getGfs().add("No Zone assigned.");
    		}

    		
    		wf.setName(ward.getName());
    		wf.setDeviceid(ward.getLocator().getImeiCode());
    		wf.setDevicepassword(ward.getLocator().getDevicePassword());
    		wf.setPhotourl(ward.getPhotourl());
    		wf.setPhoto32(ward.getPhoto32());
    		wf.setPhoto64(ward.getPhoto64());
    		wf.setLat(String.valueOf(ward.getLocator().getGpsLocation().getLatitude()));
    		wf.setLon(String.valueOf(ward.getLocator().getGpsLocation().getLongitude()));
    		
    		listWards.add(wf);
    		
    	}
    	return listWards;
    }
    
    @RequestMapping(value="/search", method = RequestMethod.GET)
    public Model getGuardians(HttpServletRequest request, Model model){
    	List<Guardian> guardians = guardianService.getAllGuardians();
    	model.addAttribute("guardians", guardians);
    	return model; 
    }
    

    
	@RequestMapping(method = RequestMethod.GET, value = "/profile/{username}")
	public String viewProfiel(@PathVariable String username, Model model) {
		model.addAttribute("userinfo", guardianService.get(username));
		
		return "guardian/profile";
	}
	
    
	@RequestMapping(method = RequestMethod.POST, value = "/test")
	public void test(HttpServletRequest request) {
    	logger.info("Test in POST method...");

	}
	
    @RequestMapping(method = RequestMethod.POST, value="/forgetpassword")
    public String forgetPassword(HttpServletRequest request){
    	logger.info("Forget Password in POST method...");
    	String email = (String)request.getParameter("email");


    	Guardian guardianInDB = guardianService.getByEmail(email);
    	
    	if(guardianInDB != null){
    		String guardianName = guardianInDB.getLogin();
    		
    		
    		WardEvent wEvent = new WardEvent();
    		wEvent.setType("ResetPassword");
    		wEvent.setBearerName(email);
    		UUID uuid = UUID.randomUUID();
    		guardianInDB.setUuidpwd(uuid.toString());
    		guardianService.update(guardianInDB);
    		logger.info("The request context is " + request.getContextPath() + request.getRequestURL());
    		String url = request.getRequestURL().toString();
    		url = url.replaceAll("forgetpassword", "resetpassword/");
    		logger.info("url=" + url);
    		wEvent.setMessage("If you want to reset your password, Please access " +  url + guardianName + "-" + uuid.toString());
    		
			topicNotificationMessageProducer.filterWardEventType(wEvent);
    	}
    	
        
    	return "redirect:/";
    }
    
    
	@RequestMapping(method = RequestMethod.GET, value = "/resetpassword/{guardianName}")
	public String resetpassword(@PathVariable String guardianName , Model model) {
		logger.info(guardianName);
		String guardianNameAfterUUID = guardianName.substring(0, guardianName.indexOf("-"));
		String uuidStr = guardianName.substring(guardianName.indexOf("-")+1);
		Guardian guardian =  guardianService.get(guardianNameAfterUUID);
		String uuidDB = guardian.getUuidpwd();
		logger.info("uuidDB=" + uuidDB);
		logger.info("uuidStr=" + uuidStr);
		if(uuidDB.equals(uuidStr)){
			
			model.addAttribute("userinfo",guardian);
			
			return "guardian/resetpassword";
			
		}else
			return "redirect:/";
		

	}
	
    @RequestMapping(value="/resetpassword",method = RequestMethod.POST)
    public String resetpassword(HttpServletRequest request){
    	logger.info("Reset Password in POST method...");
    	String login = (String)request.getParameter("login");
    	String passwd = (String)request.getParameter("passwd");
    	String repasswd = (String)request.getParameter("confirmPassword");
/*    	if(!passwd.equals(repasswd)){
    		//throws exception
    		return "redirect:/resetpassword";
    	}*/
    	Guardian guardianInDB = guardianService.get(login);
    	
        GuardianUser gu = new GuardianUser(guardianInDB.getId(),guardianInDB.getLogin(),guardianInDB.getPasswd());
        
        String passwdEncoded = guardianUserLoginService.getPasswordEncoded(passwd, gu);
        
        guardianInDB.setPasswd(passwdEncoded);
		guardianInDB.setUuidpwd("");
        guardianService.add(guardianInDB);
        
        
        return "redirect:/";
    	
    }
	
}
