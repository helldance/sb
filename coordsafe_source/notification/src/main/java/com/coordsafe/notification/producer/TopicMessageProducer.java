package com.coordsafe.notification.producer;


import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;

import org.slf4j.*;
import com.coordsafe.company.entity.Company;
import com.coordsafe.event.entity.GenericEvent;
import com.coordsafe.event.entity.VehicleEvent;
import com.coordsafe.event.entity.WardEvent;
import com.coordsafe.notification.entity.EventMessage;
import com.coordsafe.notification.entity.NotificationSetting;
import com.coordsafe.notification.entity.WardNotificationSetting;
import com.coordsafe.notification.service.NotificationSettingService;
import com.coordsafe.notification.service.WardNotificationSettingService;
import com.coordsafe.ward.entity.Ward;
import com.coordsafe.ward.service.WardService;


public class TopicMessageProducer {
	
	private static final Logger log = LoggerFactory.getLogger(TopicMessageProducer.class);
	protected JmsTemplate jmsTemplate;
	@Autowired
	private NotificationSettingService notificationSettingService;
	@Autowired
	private WardNotificationSettingService wardNotificationService;
	@Autowired
	private WardService wardService;
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	public boolean filterWardEventType(GenericEvent event){
		
		
		WardEvent wEvent = (WardEvent)event;
		String eventType = wEvent.getType();
		Ward ward = wEvent.getWard();
		if(eventType.equalsIgnoreCase("ResetPassword")){
			WardNotificationSetting wns = new WardNotificationSetting();
			
			wns.setEventType(eventType);
			wns.setEmailEnable(true);
			//get email 
			wns.setEmailaddress(wEvent.getBearerName());
			wns.setSmsEnable(false);
			try {
				this.sendMessages(new EventMessage(wEvent,wns));
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		
		
		WardNotificationSetting wns = wardNotificationService.filterWardEventType(eventType, ward);
		
		if(wns != null){
			try {
				//If the type is Panic_Alarm, no necessary to check the time slot!
				if(wEvent.getType().equalsIgnoreCase("Panic_Alarm")){
					log.info("Panic Alarm Notification Message is sending to Queue...");
					this.sendMessages(new EventMessage(wEvent, wns));
					return true;
				}
				if(wEvent.getType().equalsIgnoreCase("Circle_Event")){
					log.info("Circle Event Notification Message is sending to Queue...");
					this.sendMessages(new EventMessage(wEvent, wns));
					return true;
				}
				log.info("Checking if the time slot is more 5 minutes...");
				Date currentDate = new Date();
				Date lastDate = ward.getNotificationDate();
				if(lastDate == null){
					ward.setNotificationDate(currentDate);
					wardService.update(ward);
					log.info("Notification Message is sending to Topic Queue...");
					this.sendMessages(new EventMessage(wEvent, wns));
				}else{
					
					long between = currentDate.getTime() - lastDate.getTime();
/*					long day = between / (24 * 60 * 60 * 1000);
				    long hour = (between / (60 * 60 * 1000) - day * 24);
					long bwtmin = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);*/
					long bwtmin = between / (1000*60);
					log.info("The time slot is "  + bwtmin + "minutes");
					if(bwtmin < 5 ){
						log.info("The time slot is less 5 minutes...it only is "  + bwtmin);
						
						// all other notifications can not be sent?
					}else{
						
						ward.setNotificationDate(currentDate);
						wardService.update(ward);
						log.info("Notification Message is sending to Topic Queue...");
						this.sendMessages(new EventMessage(wEvent, wns));
						
					}
					
				}


				
			} catch (JMSException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			} 
			return true;
		}else{
			log.info("No notification setting found for ward: " + ward.getName());
			return false;
		}
	}
	
	public boolean filterEventType(GenericEvent event) {
		log.info("In the Notification Message Producer Filter process...");
		VehicleEvent vEvent = (VehicleEvent)event;
		
		Company company = vEvent.getCompany();
		String eventType = vEvent.getType();
		NotificationSetting ns = notificationSettingService.filterEventType(eventType,company);
		if(ns != null){
			try {
				log.info("Notification Message is sending to Topic Queue...");
				this.sendMessages(new EventMessage(vEvent, ns));
			} catch (JMSException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			return true;
		}else
			return false;
		
		
	}
	public void sendMessages(EventMessage em) throws JMSException {
		log.info("PRODUCER...");
		
		MessagePostProcessor postProcessor = new MessagePostProcessor() {
			public Message postProcessMessage(Message message) throws JMSException {
					message.setJMSPriority(8);
					message.setStringProperty("user", "coordsafe");
					message.setJMSReplyTo(jmsTemplate.getDefaultDestination());
					return message;
			}
		};
		
		
		jmsTemplate.convertAndSend(em,postProcessor);
		log.info("Producered");
	}
}