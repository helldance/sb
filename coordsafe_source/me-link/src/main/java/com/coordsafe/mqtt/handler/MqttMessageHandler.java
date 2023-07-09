/**
 * @author Yang Wei
 * @Date Nov 9, 2013
 */
package com.coordsafe.mqtt.handler;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author Yang Wei
 *
 */
public abstract class MqttMessageHandler {	
	private static MqttMessageHandler handler;
	
	public abstract void handleMessage (String topic, MqttMessage message);

	/**
	 * @return the handler
	 */
	public static MqttMessageHandler getHandler() {
		return handler;
	}

}
