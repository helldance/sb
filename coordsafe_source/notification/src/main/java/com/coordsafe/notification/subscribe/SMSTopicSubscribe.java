package com.coordsafe.notification.subscribe;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.listener.SessionAwareMessageListener;

import com.coordsafe.event.entity.VehicleEvent;
import com.coordsafe.event.entity.WardEvent;
import com.coordsafe.notification.common.SMSServiceByHoiio;
import com.coordsafe.notification.entity.EventMessage;
import com.coordsafe.notification.entity.NotificationSetting;
import com.coordsafe.notification.entity.WardNotificationSetting;

import org.slf4j.*;
@SuppressWarnings("rawtypes")
public class SMSTopicSubscribe implements SessionAwareMessageListener{
	private static final Logger log = LoggerFactory.getLogger(SMSTopicSubscribe.class);
	
	@Resource(name="hoiioSMSService")
	private SMSServiceByHoiio smsService;
	public void onMessage(Message message, Session session) {
	    try {
	        // Get the data from the message
	        ObjectMessage msg = (ObjectMessage) message;
	        EventMessage em = (EventMessage)msg.getObject();
/*	        NotificationSetting ns = em.getNs();
	          
	        if(ns.isSmsEnable()){
	        	VehicleEvent vEvent = (VehicleEvent)em.getEnven();	   
	        	if(ns.getMobile().equals("")){
	        		log.info("SMS mobile number String is null...");
	        		return;
	        	}
	        	List<String> destList = new ArrayList<String>(Arrays.asList(ns.getMobile().split(",")));
	        	if(destList.isEmpty()){
	        		log.info("SMS no target mobile...");
	        		return;
	        	}
	        	
				String status = smsService.sent(destList,vEvent.getMessage());
	        
		        log.info("SMS process ..." +  status);
	        	
	        }*/
	        //need identify if the message should be sent by SMS!!!
	        
/*			SMSServiceByHoiio service = new SMSServiceByHoiio();
			
			em.setMSISDN("+6597256276");
			em.setMessage("Hi, This is a test,这是测试！");*/


	        
	        if(em.getNs() instanceof WardNotificationSetting){
	        	WardNotificationSetting wns = (WardNotificationSetting)em.getNs();
		        if(wns.isSmsEnable()){
		        	WardEvent wEvent = (WardEvent)em.getEnven();
		        	List<String> destList = checkSMSTarget(wns);
		        	
		        	if(destList == null){
		        		log.warn("No SMS Target found!");
		        		return;
		        	}
		        	
		        	log.info("SMS = " + wEvent.getSms());
		        	
					String status = smsService.sent(destList,wEvent.getSms());
		        
			        log.info("SMS process ..." +  status);
		        	
		        }
	        	
	        }
	        else if(em.getNs() instanceof  NotificationSetting){
	        	NotificationSetting ns = (NotificationSetting)em.getNs();
		        if(ns.isSmsEnable()){
		        	VehicleEvent vEvent = (VehicleEvent)em.getEnven();	   
		        	if(ns.getMobile().equals("")){
		        		log.info("SMS mobile number String is null...");
		        		return;
		        	}
		        	List<String> destList = new ArrayList<String>(Arrays.asList(ns.getMobile().split(",")));
		        	if(destList.isEmpty()){
		        		log.info("SMS no target mobile...");
		        		return;
		        	}
		        	
					String status = smsService.sent(destList,vEvent.getMessage());
		        
			        log.info("SMS process ..." +  status);
		        	
		        }
	        	
	        }
	        
	    } catch (Exception ex) {
	        ex.printStackTrace(System.out);
	        //System.exit(1);
	    }
	}
	private List<String> checkSMSTarget(WardNotificationSetting wns) {
		// TODO Auto-generated method stub
		if(wns.getMobile().equals("")){
    		
    		return null;
    	}
    	List<String> destList = new ArrayList<String>(Arrays.asList(wns.getMobile().split(",")));
    	if(destList.isEmpty()){
    		
    		return null;
    	}
    	return destList;
	}



}
