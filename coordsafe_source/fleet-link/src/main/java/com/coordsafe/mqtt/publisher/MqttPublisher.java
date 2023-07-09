package com.coordsafe.mqtt.publisher;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.jfree.util.Log;

/**
 * @author Yang Wei
 * @Date Dec 14, 2013
 */
public abstract class MqttPublisher {
	protected static MqttClient mqttClient;
	
	protected MqttPublisher (){}
	
	public MqttPublisher (String brokerUrl, String clientId){
		try {
			mqttClient = new MqttClient(brokerUrl, clientId, null);
			mqttClient.connect();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void publish(String topic, MqttMessage message){
		try {
			if (mqttClient.isConnected()){
				mqttClient.publish(topic, message);
			
				Log.info("publish message to: " + topic);
			}
			else {
				Log.info("mqttClient is not connected");
			}
		} catch (MqttPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
