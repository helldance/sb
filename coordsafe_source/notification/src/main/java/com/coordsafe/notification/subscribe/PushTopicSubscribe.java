package com.coordsafe.notification.subscribe;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;

import com.coordsafe.event.entity.WardEvent;
import com.coordsafe.guardian.entity.Guardian;
import com.coordsafe.notification.common.PushService;
import com.coordsafe.notification.entity.EventMessage;
import com.coordsafe.notification.entity.NotificationSetting;
import com.coordsafe.notification.entity.WardNotificationSetting;
import com.coordsafe.remote.entity.AndroidDeviceToken;
import com.coordsafe.remote.entity.IosDeviceToken;
import com.coordsafe.remote.service.DeviceRegistrationService;
import com.coordsafe.ward.entity.Ward;
import com.notnoop.apns.APNS;
import com.notnoop.apns.PayloadBuilder;
@SuppressWarnings("rawtypes")
public class PushTopicSubscribe implements SessionAwareMessageListener{
	private static final Logger log = LoggerFactory.getLogger(PushTopicSubscribe.class);
	
	@Autowired DeviceRegistrationService deviceTokenSvrs;
	@Autowired PushService pushSvrs;
	
	public void onMessage(Message message, Session session) {
		log.info("RCVED app push event");
		
	    try {
	        // Get the data from the message
	        ObjectMessage msg = (ObjectMessage) message;
	        EventMessage em = (EventMessage)msg.getObject();
	        
	        try {
		        if(em.getNs() instanceof WardNotificationSetting){
		        	WardNotificationSetting wns = (WardNotificationSetting)em.getNs();
			        if(wns.isAppPush()){
			        	WardEvent wEvent = (WardEvent)em.getEnven();
			        	
			        	if (wEvent != null){
				        	Ward w = wEvent.getWard();
				        	
				        	if (w != null){
					        	Set<Guardian> guardians = w.getGuardians();
					        	
					        	List<IosDeviceToken> devices = new ArrayList<IosDeviceToken>();
					        	List<String> tokens = new ArrayList<String>();
					        	
					        	List<AndroidDeviceToken> andevices = new ArrayList<AndroidDeviceToken>();
					        	List<String> andtokens = new ArrayList<String>();
					        	
					        	for (Guardian g : guardians){
					        		devices.addAll(deviceTokenSvrs.findByGuardian(g.getId()));
					        		andevices.addAll(deviceTokenSvrs.findAndroidByGuardian(g.getId()));
					        	}
					        	
					        	for (IosDeviceToken device : devices){
					        		String tkn = device.getToken();
					        		if (!(tkn == null || tkn.isEmpty())){
					        			tokens.add(tkn);
					        		}
					        	}
					        	
					        	for (AndroidDeviceToken andevice : andevices){
					        		String tkn = andevice.getToken();
					        		if (!(tkn == null || tkn.isEmpty())){
					        			andtokens.add(tkn);
					        		}
					        	}
					        	
					        	// construct push payload
					        	Map<String, String> pl = new HashMap<String, String>();
					        	//pl.put("ward", wEvent.getWard().getName());
					        	pl.put("type", wEvent.getType());
					        	
					        	String bodyMsg = "";
					        	
					        	if (wEvent.getType().equals("Panic_Alarm")){
					        		bodyMsg = wEvent.getMessage().substring(0, wEvent.getMessage().indexOf("|"));
					        	}
					        	else {
					        		bodyMsg = wEvent.getMessage();
					        	}
					        	
					        	pl.put("msg",bodyMsg);
					        			
					        	//pl.put("time", wEvent.getEventTime().toString());
					        	//pl.put("place", wEvent.getLocation().toString());
					        	
					        	ObjectMapper mapper = new ObjectMapper();
					        	
					        	String jsonStr = mapper.writeValueAsString(pl);
			
						        log.info("pushing in process ..." + jsonStr + "\nDevices: " + tokens);
					        			        	
					        	//pushSvrs.pushToApns(tokens, APNS.newPayload().alertBody(jsonStr).build());	
						        PayloadBuilder builder = APNS.newPayload().sound("default");
						        builder.alertBody(bodyMsg);
						        pushSvrs.pushToApns(tokens, builder.build());
						        pushSvrs.pushToGcm(andtokens, bodyMsg);
				        	}
				        	else {
				        		log.info("No ward is found");
				        	}
			        	}
			        	
			        }
		        	
		        }
		        else if(em.getNs() instanceof  NotificationSetting){
		        	log.info("Nothing to do");
		        }
	        }
	        catch (Exception e){
	        	e.printStackTrace();
	        }
	        
	    } catch (Exception ex) {
	        ex.printStackTrace(System.out);
	        //System.exit(1);
	    }
	}



}
