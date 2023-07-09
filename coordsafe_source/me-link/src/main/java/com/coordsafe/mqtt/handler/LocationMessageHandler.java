/**
 * @author Yang Wei
 * @Date Nov 11, 2013
 */
package com.coordsafe.mqtt.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author Yang Wei
 *
 */
public class LocationMessageHandler extends MqttMessageHandler{
	private static MqttMessageHandler handler;
	private static final Log log = LogFactory.getLog(LocationMessageHandler.class);
			
	private LocationMessageHandler (){}

	public static MqttMessageHandler getHandler() {
		if (handler == null)
			return new LocationMessageHandler();
		
		return handler;
	}

	@Override
	public void handleMessage(String topic, MqttMessage message) {
		String [] levelTopics =topic.split("/");
		String user = levelTopics[3]; // "/location/{user}"
		
		// save user location
	}
}
