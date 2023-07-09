package com.coordsafe.locator.service;

import java.util.Date;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coordsafe.common.entity.TransLocation;
import com.coordsafe.locator.entity.ExtraCommand;
import com.coordsafe.locator.entity.GpsLocation;
import com.coordsafe.locator.entity.Locator;
import com.coordsafe.locator.entity.LocatorLocationHistory;
@Component
public class SzLocatorListener implements MessageListener {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(SzLocatorListener.class);
	@Autowired
	private LocatorService locatorServie;

	@Override
	public void onMessage(Message message){
		log.info("The Locator Listening ..." + message);
		
		try {
			if (message instanceof MapMessage) {
				log.info("Instanceof the MapMessage");
			}
			else if (message instanceof ObjectMessage){
				log.info("Instanceof the ObjectMessage");
				
				ObjectMessage obj = (ObjectMessage) message;
				processLocationInfo((TransLocation)obj.getObject());
			}
			else if (message instanceof TextMessage){
				log.info("Instanceof the TextMessage");
			}
			else {
				log.error("Unrecognized message format");
			}
		} catch (Exception e) {
			log.error("error update location: " + e.getStackTrace() );
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
		boolean gpxFix = location.gpsFix;
		boolean gsmOn = location.gsmOn;
		byte noSatellite = location.noSatellite;
		byte battery = location.battery;
		byte network = location.network;
		
		log.info("battery: " + battery + " ip: " + location.deviceIp);
		
		ExtraCommand exc = new ExtraCommand();
						
		if (battery == 49){ // on battery, this is engine stop 
			exc.powerInternal = true;
			exc.stopSignal = true;
		}
		else if (battery == 48){
			//can we set startSignal here?
		}
		
		LocatorLocationHistory his = new LocatorLocationHistory();
		
		Locator locator = locatorServie.findLocatorByImei(imei);
		
		if (locator == null){			
			log.error("======== find unregistered locator: " + imei);
			return;
		}
		
		his.setLocator_id(locator.getId());
		his.setLat(lat);
		his.setLng(lng);
		his.setSpeed(speed);
		his.setAltitude(alt);
		his.setDistance(dist);
		his.setBearing(bear);
		his.setAccuracy(accu);
		his.setLocation_time(new Date(gpsTime));
		//his.setGpxFix(gpxFix);
		//if (time != null)
			//his.setLocation_time(new Date(time));
		//else
		//	his.setLocation_time(new Date());
		
		long hid = locatorServie.createLocationHistory(his);
		
		//TODO add info to status update.
		if (hid != 0){			
			log.info("created history: " + hid);
			
			GpsLocation l = new GpsLocation();
			l.setLatitude(lat);
			l.setLongitude(lng);
			l.setSpeed((float) speed);
			l.setAltitude(alt);
			l.setTime(gpsTime);
						
			locatorServie.updateLocation(imei, l, exc, location.deviceIp, hid);
		}
		else {
			log.error("create history failed");
		}
	}
}
