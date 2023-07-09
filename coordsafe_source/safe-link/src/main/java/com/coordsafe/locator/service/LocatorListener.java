package com.coordsafe.locator.service;

import java.util.Date;
import java.util.Set;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.codehaus.jackson.map.ObjectMapper;
import org.postgis.Point;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coordsafe.common.entity.TransLocation;
import com.coordsafe.company.service.CompanyService;
import com.coordsafe.event.entity.WardEvent;
import com.coordsafe.geofence.entity.Geofence;
import com.coordsafe.geofence.service.GeofenceService;
import com.coordsafe.locator.entity.ExtraCommand;
import com.coordsafe.locator.entity.GpsLocation;
import com.coordsafe.locator.entity.Locator;
import com.coordsafe.locator.entity.LocatorLocationHistory;
import com.coordsafe.notification.producer.TopicMessageProducer;
import com.coordsafe.ward.entity.Ward;
import com.coordsafe.ward.service.WardService;
@Component
public class LocatorListener implements MessageListener {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(LocatorListener.class);
	
	@Autowired
	private LocatorService locatorServie;
	
	@Autowired
	private CompanyService companySvrs;
	
	@Autowired
	private WardService wardService;
	
	@Autowired
	private GeofenceService geofenceService;
	
	@Autowired
	private TopicMessageProducer topicNotificationMessageProducer;
	
    public void locatoionReceived(String message) throws Exception {
    	log.info(message);
    }
	@Override
	public void onMessage(Message message){
		//log.info("The Locator Listening ..." + message);
		try {
			if (message instanceof MapMessage) {
				log.info("Instanceof the MapMessage");
				MapMessage mMesg = (MapMessage) message;
				
				// save full location data
				String imei =  mMesg.getString("deviceId");
				double lat = mMesg.getDouble("lat");
				double lng = mMesg.getDouble("lng");
				
				// Let invalid data in for debugging - 14/03/2013 YW
				/*if (!(lat > 0.1 && lng > 0.1)){
					logger.info("Location is invalid: " + lat + " " + lng );
					return;
				}*/
				
				double speed = mMesg.getDouble("speed");
				double alt = mMesg.getDouble("alt");
				double dist = mMesg.getDouble("dist");
				double bear = mMesg.getDouble("bear");
				double accu = mMesg.getDouble("accu");
				long gpsTime = mMesg.getLong("gpsTime");
				
				Locator locator = locatorServie.findLocatorByImei(imei);
				
				if (locator == null){
					log.error("======== find unregistered locator: " + imei);
				
					// register/log to database but set as unregistered
					Locator newLoc = new Locator(imei, imei, new Date(), true);
					locatorServie.createLocator(newLoc);
					
					log.error("======== created as unregistered locator: " + imei);
					
					return;
				}
				else if (locator.isSpoiled()){
					// is spoiled or unregsitered
					return;
				}
				
				log.info("The Founded locator is " + locator.getImeiCode());
				
				TransLocation transLocation = new TransLocation();
				
				transLocation.setLat(lat);
				transLocation.setLng(lng);
				transLocation.setGpsTime(gpsTime);
				transLocation.setDeviceId(imei);
				transLocation.setSpeed(speed);
				transLocation.setAlt(alt);
				transLocation.setBattery((byte)10);
				transLocation.setDistance(dist);
				transLocation.setAccuracy(accu);
				transLocation.setBearing(bear);
				
				
				processLocationInfo(transLocation);
				
/*				long hid = createLocationHistory (locator.getId(), lat, lng, speed, alt,
						dist, bear, accu, new Date(gpsTime));

				//TODO add info to status update.
				if (hid != 0)
					locatorServie.updateLocation(imei, lat, lng, gpsTime, hid);*/
				
				//locatorServie.createLocationHistory(mMesg.getString("deviceId"), mMesg.getDouble("lat"), mMesg.getDouble("lng"));
				//locatorServie.updateLocation(mMesg.getString("deviceId"), mMesg.getDouble("lat"), mMesg.getDouble("lng"));
				
			}
			
			else if (message instanceof ObjectMessage){
				log.info("Instanceof the ObjectMessage");
				ObjectMessage obj = (ObjectMessage) message;
				processLocationInfo((TransLocation)obj.getObject());
			}
			else if(message instanceof TransLocation){
				log.info("Instanceof the TransLocation");
				processLocationInfo((TransLocation)message);
			}
			else if (message instanceof TextMessage){
				log.info("Instanceof the TextMessage");

				ObjectMapper mapper = new ObjectMapper();
				TextMessage tm = (TextMessage) message;

				TransLocation location = mapper.readValue(tm.getText(), TransLocation.class);
				
				processLocationInfo(location);
			}
			else {
				log.error("Unrecognized message format");
			}
		} catch (Exception e) {
			log.error("error update location: ");
			e.printStackTrace();
		}
	}
	
	private void processLocationInfo(TransLocation location){

		log.info("In the Porcess Location Info from GPS");
		// save full location data
		String imei =  location.deviceId;
		double lat = location.lat;
		double lng = location.lng;			
		double speed = location.speed;
		double alt = location.alt;
		double dist = location.distance;
		double bear = location.bearing;
		double accu = location.accuracy;
		long gpsTime = location.gpsTime;
		byte battery = location.battery;
		
		log.info("battery: " + battery);
		
		ExtraCommand exc = new ExtraCommand();
						
		if (battery == 49){ // on battery, this is engine stop 
			exc.powerInternal = true;
			exc.stopSignal = true;
		}
		else if (battery == 48){
			exc.powerInternal = false;
			exc.stopSignal = false;
		}
		
		Locator locator = locatorServie.findLocatorByImei(imei);
		
		if (locator == null){
			log.error("======== find unregistered locator: " + imei);
		
			// register/log to database but set as unregistered
			Locator newLoc = new Locator(imei, imei, new Date(), true);
			locatorServie.createLocator(newLoc);
			
			log.info("======== created as unregistered locator: " + imei);
			
			return;
		}
		else if (locator.isSpoiled()){
			// is spoiled or unregsitered
			log.info("======== find spoiled locator: " + imei);
			
			return;
		}
		
//		if(location.getLocatorType().equals("ward")){
			long locatorid = locator.getId();
			log.info("The Locator ID = " + locatorid);
			Ward ward = wardService.findByLocatorID(locatorid);
			if(ward == null)
				log.error("The ward is NULL");
			else
				log.info("The WARD is " + ward.getName());
			boolean in = false;
			Set<Geofence> geofences = ward.getGeofences();
			if(geofences.size() > 0 )
				log.info("The WARD's Geofences are " + geofences.size());
			else
				log.info("The WARD has No Geofence! by lat=" + lat + "and lng=" + lng);
			Point point = new Point( );
/*			point.setX(lng);
			point.setY(lat);*/
			point.setX(lat);
			point.setY(lng);
			for(Geofence geofence:geofences){
				log.info("Currently, you are in Geofence checking!!!");
				in = geofenceService.withinWard(point, geofence);
				if(in && geofence.getZoneType().equalsIgnoreCase("dangerous")){
					log.info("in dangerous zone notification");
					WardEvent wEvent = new WardEvent();
					wEvent.setWard(ward);
					//wEvent.setGuardianname(ward.getGuardians());
					wEvent.setType("Dangerous_Zone");
					wEvent.setMessage("The WARD " + ward.getName()+ " has a notification as the ward is in DANGEROUS Zone!!! at " + new java.util.Date());
					topicNotificationMessageProducer.filterWardEventType(wEvent);
				}
				if(!in && geofence.getZoneType().equals("safe")){
					log.info("Not in safe zone notification");
					WardEvent wEvent = new WardEvent();
					wEvent.setWard(ward);
					//wEvent.setGuardianname(ward.getGuardians());
					wEvent.setType("Safe_Zone");
					wEvent.setMessage("The WARD " + ward.getName()+ " has a notification as the ward is out of SAFE Zone!!! at " + new java.util.Date());
					topicNotificationMessageProducer.filterWardEventType(wEvent);
				}
			}
			
			//createLocationHistory (locator.getId(), lat, lng, speed, alt, dist, bear, accu, new Date(gpsTime));
			
	
			
		//}
		
		long hid = createLocationHistory (locator.getId(), lat, lng, speed, alt, dist, bear, accu, new Date(gpsTime));
		
		//TODO add info to status update.
		if (hid != 0){
			GpsLocation l = new GpsLocation();
			l.setLatitude(lat);
			l.setLongitude(lng);
			l.setSpeed((float) speed);
			l.setAltitude(alt);
			l.setTime(gpsTime);
			
			//locatorServie.updateLocation(imei, l, exc, location.deviceIp, hid);
			
			locatorServie.updateLocationForWARD(imei, l, exc, location.deviceIp, hid);
			
		}		
	}
	
	private long createLocationHistory (long locatorId, double lat, double lng, double speed, double alt,
			double dist, double bear, double accu, Date dt){
		LocatorLocationHistory his = new LocatorLocationHistory();
		
		his.setLocator_id(locatorId);
		his.setLat(lat);
		his.setLng(lng);
		his.setSpeed(speed);
		his.setAltitude(alt);
		his.setDistance(dist);
		his.setBearing(bear);
		his.setAccuracy(accu);
		his.setLocation_time(dt);
		
		long hid = locatorServie.createLocationHistory(his);
		
		log.info("created history: " + hid);
		
		return hid;
	}
}
