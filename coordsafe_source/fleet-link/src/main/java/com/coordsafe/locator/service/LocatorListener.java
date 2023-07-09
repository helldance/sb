package com.coordsafe.locator.service;

import java.util.Date;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coordsafe.common.entity.TransLocation;
import com.coordsafe.company.service.CompanyService;
import com.coordsafe.locator.entity.ExtraCommand;
import com.coordsafe.locator.entity.GpsLocation;
import com.coordsafe.locator.entity.Locator;
import com.coordsafe.locator.entity.LocatorLocationHistory;
//import com.coordsafe.mqtt.publisher.MqttPublisher;
@Component
public class LocatorListener implements MessageListener {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(LocatorListener.class);
	
	@Autowired
	private LocatorService locatorServie;
	
	@Autowired
	private CompanyService companySvrs;
	
	//@Autowired
	//private GeofenceStatusChecker fenceChecker;
	
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
					
					//MqttPublisher pub = new MqttPublisher("tcp://localhost:1883", "server"){};
				
					//pub.publish("/topic/vehicle-event", new MqttMessage(imei.getBytes()));
					
					return;
				}
				else if (locator.isSpoiled()){
					// is spoiled or unregsitered
					return;
				}
				
				long hid = createLocationHistory (locator.getId(), lat, lng, speed, alt,
						dist, bear, accu, new Date(gpsTime));

				//TODO add info to status update.
				if (hid != 0)
					locatorServie.updateLocation(imei, lat, lng, gpsTime, hid);
				
				//locatorServie.createLocationHistory(mMesg.getString("deviceId"), mMesg.getDouble("lat"), mMesg.getDouble("lng"));
				//locatorServie.updateLocation(mMesg.getString("deviceId"), mMesg.getDouble("lat"), mMesg.getDouble("lng"));
				
			}
			
			else if (message instanceof ObjectMessage){
				//log.info("Instanceof the ObjectMessage");
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

		//log.debug("In the Porcess Location Info from GPS");
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
		boolean gpxFix = location.gpsFix;
		boolean gsmOn = location.gsmOn;
		byte noSatellite = location.noSatellite;
		byte battery = location.battery;
		byte network = location.network;
		
		//log.debug("battery: " + battery);
		
		ExtraCommand exc = new ExtraCommand();
						
		if (battery == 49){ // on battery, this is engine stop 
			exc.powerInternal = true;
			exc.stopSignal = true;
			
			log.info("vehicle is on battery" + imei);
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
			
			//MqttPublisher pub = new MqttPublisher("tcp://localhost:1883", "server"){};
			
			//pub.publish("/topic/vehicle-event", new MqttMessage(imei.getBytes()));
			
			return;
		}
		else if (locator.isSpoiled()){
			// is spoiled or unregsitered
			log.info("======== find spoiled locator: " + imei);
			
			return;
		}
		
		long hid = createLocationHistory (locator.getId(), lat, lng, speed, alt,
				dist, bear, accu, new Date(gpsTime));
		
		//TODO add info to status update.
		if (hid != 0){
			GpsLocation l = new GpsLocation();
			l.setLatitude(lat);
			l.setLongitude(lng);
			l.setSpeed((float) speed);
			l.setAltitude(alt);
			l.setTime(gpsTime);
			
			//locatorServie.updateLocation(imei, lat, lng, gpsTime, hid);
			//locatorServie.updateLocation(imei, l, hid);
			
			locatorServie.updateLocation(imei, l, exc, location.deviceIp, hid);
		}
		
		// check geofence status
		//fenceChecker.checkFenceStatus(locator, new Point(lat, lng));
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
