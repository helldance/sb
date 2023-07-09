package com.coordsafe.locator.service;


import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatusListener implements MessageListener {
	@Autowired
	private LocatorService locatorService;

	public void setLocatorServie(LocatorService locatorServie) {
		this.locatorService = locatorServie;
	}

	private static Logger logger = Logger.getLogger(StatusListener.class);

	public void locatoionReceived(String message) throws Exception {
		logger.info(message);
	}

	@Override
	public void onMessage(Message message) {
		try {
			if (message instanceof MapMessage) {
				MapMessage mMesg = (MapMessage) message;
				logger.info("IMEI: " + mMesg.getString("deviceId"));
				logger.info("IP : " + mMesg.getString("ip"));
				logger.info("BATT : " + mMesg.getInt("battery"));
				locatorService.updateStatus(mMesg.getString("deviceId"),
						mMesg.getString("ip"), mMesg.getBoolean("gsm"),
						mMesg.getBoolean("gps"), mMesg.getInt("battery"),
						mMesg.getInt("network"), mMesg.getLong("gpsTime"));
				// locatorServie.updateLocation(mMesg.getString("deviceId"),
				// mMesg.getDouble("lat"), mMesg.getDouble("lng"));

			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
