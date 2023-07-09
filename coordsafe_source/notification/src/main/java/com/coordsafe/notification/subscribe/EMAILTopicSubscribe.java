package com.coordsafe.notification.subscribe;




import javax.annotation.Resource;

import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;


import org.springframework.jms.listener.SessionAwareMessageListener;

import com.coordsafe.event.entity.VehicleEvent;
import com.coordsafe.event.entity.WardEvent;
import com.coordsafe.notification.common.CoordsafeMailService;
import com.coordsafe.notification.entity.EventMessage;
import com.coordsafe.notification.entity.NotificationSetting;
import com.coordsafe.notification.entity.WardNotificationSetting;

import org.slf4j.*;
@SuppressWarnings("rawtypes")
public class EMAILTopicSubscribe implements SessionAwareMessageListener{
	private static final Logger log = LoggerFactory.getLogger(EMAILTopicSubscribe.class);
	@Resource(name="mailService")
	private CoordsafeMailService mail ;


	public void onMessage(Message message, Session session) {
		log.info("EMAIL Topic Subscriber on message...");
	    try {
	        // Get the data from the message
	        ObjectMessage msg = (ObjectMessage) message;
	        EventMessage em = (EventMessage)msg.getObject();
			
	        //should identify if the message need to be sent using mail!!!
/*	        AbstractNotificationSetting ns = em.getNs();
	        VehicleEvent vEvent = (VehicleEvent)em.getEnven();
	        if(ns.isEmailEnable()){
	        	String[] emailAddress = ns.getEmailaddress().split(",");
	        	
	        	if(emailAddress.length == 0){
	        		log.info("No Email Address in the " + vEvent.getType());
	        		return;
	        	}
		        
		        mail.sendMail("To:".concat(vEvent.getCompany().getName()) + ", Notification from Coordsafe", vEvent.getMessage() , emailAddress);
		        
		        log.info("Coordsafe EMAIL process..." );	        	
	        	
	        }*/
	        /*
			MailService svr = new MailService();
			svr.sentMail();
			
	        
			ApplicationContext context = new ClassPathXmlApplicationContext(
					new String[] { "com/coordsafe/notification/config/commonservices.xml" });
			
			CoordsafeMailService mail = (CoordsafeMailService)context.getBean("mailService");
			*/
	        


	        
	        if(em.getNs() instanceof WardNotificationSetting){
	        	WardNotificationSetting wns = (WardNotificationSetting)em.getNs();
		        WardEvent wEvent = (WardEvent)em.getEnven();
		        if(wns.isEmailEnable()){
		        	String[] emailAddress = wns.getEmailaddress().split(",");
		        	
		        	if(emailAddress.length == 0){
		        		log.info("No Email Address in the " + wEvent.getType());
		        		return;
		        	}
			        if(wEvent.getType().equalsIgnoreCase("ResetPassword"))
				        mail.sendMail("Reset Password from Coordsafe", wEvent.getMessage() , emailAddress);
			        else
			        	mail.sendMail("WARD:".concat(wEvent.getWard().getName()) + ", Notification from Coordsafe", wEvent.getMessage() , emailAddress);
			        
			        log.info("Coordsafe EMAIL process..." );	        	
		        	
		        }
	        	
	        }
	        else if(em.getNs() instanceof  NotificationSetting){
		        NotificationSetting ns = (NotificationSetting)em.getNs();
		        VehicleEvent vEvent = (VehicleEvent)em.getEnven();
		        if(ns.isEmailEnable()){
		        	String[] emailAddress = ns.getEmailaddress().split(",");
		        	
		        	if(emailAddress.length == 0){
		        		log.info("No Email Address in the " + vEvent.getType());
		        		return;
		        	}
			        
			        mail.sendMail("To:".concat(vEvent.getCompany().getName()) + ", Notification from Coordsafe", vEvent.getMessage() , emailAddress);
			        
			        log.info("Coordsafe EMAIL process..." );	        	
		        	
		        }
	        }
	        
	        

	    } catch (Exception ex) {
	        ex.printStackTrace(System.out);
	        //System.exit(1);
	    }
	}



}
