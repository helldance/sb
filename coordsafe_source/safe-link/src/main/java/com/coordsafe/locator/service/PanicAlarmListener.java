package com.coordsafe.locator.service;

import java.util.Date;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coordsafe.event.entity.WardEvent;
import com.coordsafe.geofence.service.GeofenceService;
import com.coordsafe.locator.entity.Locator;
import com.coordsafe.notification.producer.TopicMessageProducer;
import com.coordsafe.ward.entity.Ward;
import com.coordsafe.ward.service.WardService;

import org.slf4j.*;

@Component
public class PanicAlarmListener implements MessageListener {
	Logger logger = LoggerFactory.getLogger(PanicAlarmListener.class);

	@Autowired
	private LocatorService locatorService;
	
	@Autowired
	private WardService wardService;
	
	@Autowired
	private GeofenceService geofenceService;
	@Autowired
	private TopicMessageProducer topicNotificationMessageProducer;
	
	public void setlocatorService(LocatorService locatorService) {
		this.locatorService = locatorService;
	}
	
    public void locatoionReceived(String message) throws Exception {
    	logger.info(message);
    }
	@Override
	public void onMessage(Message message){
		try {
			if (message instanceof MapMessage) {
				MapMessage mMesg = (MapMessage) message;
				logger.info("IMEI: " + mMesg.getString("deviceId"));
				logger.info("Time : " + mMesg.getString("time"));
				logger.info("LAT : " + mMesg.getDouble("lat"));
				logger.info("LNG : " + mMesg.getDouble("lng"));
				
				locatorService.sendPanicAlarm(mMesg.getString("deviceId"), mMesg.getString("time"), mMesg.getDouble("lat"), mMesg.getDouble("lng"));
				//locatorServie.updateStatus("321456783",mMesg.getString("ip"),mMesg.getBoolean("gsm"),mMesg.getBoolean("gps"),mMesg.getInt("battery"),mMesg.getInt("network"));
				//locatorServie.updateLocation(mMesg.getString("deviceId"), mMesg.getDouble("lat"), mMesg.getDouble("lng"));
				String imei = mMesg.getString("deviceId");
				Locator locator = locatorService.findLocatorByImei(imei);
				
				if (locator == null){
					logger.error("======== find unregistered locator: " + imei);
				
					// register/log to database but set as unregistered
					Locator newLoc = new Locator(imei, imei, new Date(), true);
					locatorService.createLocator(newLoc);
					
					logger.info("======== created as unregistered locator: " + imei);
					
					return;
				}
				else if (locator.isSpoiled()){
					// is spoiled or unregsitered
					logger.info("======== find spoiled locator: " + imei);
					
					return;
				}
				logger.info("Not in safe zone notification");
				
				Ward ward = wardService.findByLocatorID(locator.getId());
				String icon = "http://localhost:8080/safe-link/" + ward.getPhoto32();
				logger.info("icon=" + icon);
				WardEvent wEvent = new WardEvent();
				wEvent.setWard(ward);
				//wEvent.setGuardianname(ward.getGuardians());
				wEvent.setType("Panic_Alarm");
				wEvent.setMessage("The WARD " + ward.getName()+ " has a notification as the ward is sending an Alarm from lat=" 
						+ mMesg.getDouble("lat") 
						+ "lont=" +mMesg.getDouble("lng") + 
						" please check on map http://maps.google.com/?q=" + mMesg.getDouble("lat") + "," + mMesg.getDouble("lng")  + " At " + new java.util.Date()
						
				/*	+ "<hr><img src='http://maps.googleapis.com/maps/api/staticmap?center="+mMesg.getDouble("lat") + "," 
						//+ mMesg.getDouble("lng") +"&zoom=13&size=600x300&maptype=roadmap&markers=color:red%7Clabel:W%7C"+mMesg.getDouble("lat") + "," + mMesg.getDouble("lng") 
						+ mMesg.getDouble("lng") +"&zoom=13&size=600x300&maptype=roadmap&markers=icon:" + icon + "%7C" + mMesg.getDouble("lat") + "," + mMesg.getDouble("lng")
						+"&sensor=false'></img>"*/
						
						);
				topicNotificationMessageProducer.filterWardEventType(wEvent);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
