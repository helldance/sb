package com.coordsafe.httpgateway.service;

import java.io.IOException;
import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import com.coordsafe.common.entity.TransLocation;
import com.coordsafe.httpgateway.entity.Locator;
import com.coordsafe.httpgateway.entity.PanicAlarm;
import com.coordsafe.httpgateway.entity.Status;

@Service
public class WardMobileServiceImpl implements WardMobileService {

	@Override
	public boolean sendLocation(Locator locator) {
		ApplicationContext ctx = ContextLoader
				.getCurrentWebApplicationContext();
		ConnectionFactory factory = (ConnectionFactory) ctx
				.getBean("connectionFactory");
		Connection conn = null;
		try {
			conn = factory.createConnection();
			conn.start();

			Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//Destination dest = session.createTopic("jms/LocationUpdateText");
			Destination dest = session.createQueue("jms/LocationUpdateText");
			
			TransLocation location = new TransLocation();
			
			location.deviceId = locator.imei;
			location.lat = locator.gpsData.latitude;
			location.lng = locator.gpsData.longitude;
			location.alt = locator.gpsData.altitude;
			location.gpsFix = locator.gpsData.hasAccuracy;
			//location.noSatellite = locator.;
			location.gpsTime = locator.gpsData.time;
			//location.gsmOn = cpl.getGprs() == 2? true: false;
			location.accuracy = locator.gpsData.accuracy;
			//location.battery = locator.gpsData.;
			location.bearing = locator.gpsData.bearing;
			location.distance = locator.gpsData.mDistance;
			//location.network = cpl.getGprs();
			location.speed = locator.gpsData.speed;
			
			// add device Ip
			//location.deviceIp = locator.;
			
			ObjectMapper mapper = new ObjectMapper();
			String value = mapper.writeValueAsString(location);
							
			MessageProducer producer = session.createProducer(dest);
			
			TextMessage textMessage = session.createTextMessage(value);
			
			producer.send(textMessage);
			
			conn.close();
			
			return true;

		} catch (JMSException e) {
			System.out.println("error sending location to queue");
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (JMSException je) {
					je.printStackTrace();
				}

		}

		return false;
	}

	@Override
	public boolean authenticate(Locator locator) {
		// backend service - create locator record.
		/*ApplicationContext ctx = ContextLoader
				.getCurrentWebApplicationContext();
		LocatorWebService locatorWs = (LocatorWebService) ctx
				.getBean("locatorWSClient");

		if (locatorWs.authenticate(locator.imei)) {
			return true;
		} else {
			return false;
		}*/
		
		return false;
	}

	@Override
	public boolean sendPanicAlarm(PanicAlarm alarm) {
		System.out.println("alarm received");

		ApplicationContext ctx = ContextLoader
				.getCurrentWebApplicationContext();
		ConnectionFactory factory = (ConnectionFactory) ctx
				.getBean("connectionFactory");

		Connection conn=null;
		try {
			conn = factory.createConnection();
			conn.start();

			Session session = conn.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			Destination dest = session.createTopic("jms/PanicAlarm");
			MessageProducer producer = session.createProducer(dest);

			MapMessage mmsg = session.createMapMessage();
			mmsg.setString("deviceId", alarm.imei);
			mmsg.setString("time", alarm.alarm_time);
			mmsg.setDouble("lat", alarm.location.latitude);
			mmsg.setDouble("lng", alarm.location.longitude);

			System.out.println(alarm.location.latitude + " // "
					+ alarm.location.longitude);

			producer.send(mmsg);

			return true;
		} catch (JMSException e) {
			e.printStackTrace();
			System.out.println("error sending alarm to queue");
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (JMSException je) {
					je.printStackTrace();
				}

		}

		return false;
	}

	@Override
	public boolean sendStatus(Status status) {
		// send to mq.
		ApplicationContext ctx = ContextLoader
				.getCurrentWebApplicationContext();
		ConnectionFactory factory = (ConnectionFactory) ctx
				.getBean("connectionFactory");

		Connection conn=null;
		try {
			conn = factory.createConnection();
			conn.start();

			Session session = conn.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			Destination dest = session.createTopic("jms/StatusUpdate");
			MessageProducer producer = session.createProducer(dest);

			MapMessage mmsg = session.createMapMessage();
			mmsg.setString("deviceId", status.deviceInfo.imei);
			mmsg.setBoolean("gps", status.deviceInfo.isGpsOn);
			mmsg.setBoolean("gsm", status.deviceInfo.isGsmOn);
			mmsg.setInt("battery", status.deviceInfo.batteryLeft);
			mmsg.setInt("network", status.deviceInfo.networkAvailability);
			mmsg.setString("ip", status.deviceInfo.ip);
			mmsg.setLong("gpsTime", new Date().getTime());

			producer.send(mmsg);

			return true;
		} catch (JMSException e) {
			e.printStackTrace();
			System.out.println("error sending status to queue");
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (JMSException je) {
					je.printStackTrace();
				}

		}

		return false;
	}
}
