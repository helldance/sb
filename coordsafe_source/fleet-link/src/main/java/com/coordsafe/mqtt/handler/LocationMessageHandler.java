/**
 * @author Yang Wei
 * @Date Nov 11, 2013
 */
package com.coordsafe.mqtt.handler;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.coordsafe.common.entity.MqttGpsLocation;
import com.coordsafe.locator.entity.Locator;
import com.coordsafe.locator.entity.LocatorLocationHistory;
import com.coordsafe.locator.service.LocatorService;

/**
 * @author Yang Wei
 *
 */
public class LocationMessageHandler extends MqttMessageHandler{
	private static MqttMessageHandler handler;
	private static final Log log = LogFactory.getLog(LocationMessageHandler.class);
			
	@Autowired
	private LocatorService locatorSvrs;
	
	private LocationMessageHandler (){}

	public static MqttMessageHandler getHandler() {
		if (handler == null)
			return new LocationMessageHandler();
		
		return handler;
	}

	@Override
	public void handleMessage(String topic, MqttMessage message) {	
		log.info("Instanceof the MqttMessage");

		ObjectMapper mapper = new ObjectMapper();

		MqttGpsLocation location;
		
		try {
			location = mapper.readValue(message.toString(), MqttGpsLocation.class);
			
			processLocationInfo(location);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void processLocationInfo(MqttGpsLocation location) {
		// TODO Auto-generated method stub
		String imei = location.imei;
		
		Locator locator = locatorSvrs.findLocatorByImei(imei);
		
		if (locator == null){
			log.error("======== find unregistered locator: " + imei);
		
			// register/log to database but set as unregistered
			Locator newLoc = new Locator(imei, imei, new Date(), true);
			locatorSvrs.createLocator(newLoc);
			
			log.error("======== created as unregistered locator: " + imei);
			
			return;
		}
		else if (locator.isSpoiled()){
			// is spoiled or unregsitered
			return;
		}
		
		// create location history
		LocatorLocationHistory his = new LocatorLocationHistory();
		
		his.setLocator_id(locator.getId());
		his.setLat(location.latitude);
		his.setLng(location.longitude);
		his.setAccuracy(location.accuracy);
		his.setAltitude(location.altitude);
		his.setLocation_time(location.time);
		
		long hid = locatorSvrs.createLocationHistory(his);

		//TODO add info to status update.
		if (hid != 0)
			locatorSvrs.updateLocation(imei, location.latitude, location.longitude, location.time.getTime(), hid);
	}
}
