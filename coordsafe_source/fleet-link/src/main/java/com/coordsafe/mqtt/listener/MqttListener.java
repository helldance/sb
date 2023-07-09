/**
 * @author Yang Wei
 * @Date Nov 4, 2013
 */
package com.coordsafe.mqtt.listener;

import java.beans.ConstructorProperties;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import com.coordsafe.mqtt.handler.ChatMessageHandler;
import com.coordsafe.mqtt.handler.LocationMessageHandler;
import com.coordsafe.mqtt.handler.MqttMessageHandler;

/**
 * @author Yang Wei
 *
 */
public class MqttListener implements MqttCallback{
	private static final Logger logger = Logger.getLogger(MqttListener.class); 
	private static String clientId = "server-192-168-1-135";
	
	private String brokerUrl;
	private boolean cleanSession;
  	private String userName;
  	private String password;
	private MqttConnectOptions connOpts;
	private IMqttClient mqttClient;
	private String subTopic;
	private MqttMessageHandler msgHandler;
  	
	@ConstructorProperties({"brokerUrl", "cleanSession", "subTopic"})
	public MqttListener (String brokerUrl, boolean cleanSession, String subTopic){
		this.brokerUrl = brokerUrl;
    	this.cleanSession = cleanSession;
    	this.subTopic = subTopic;
    	
    	connOpts = new MqttConnectOptions();
    	connOpts.setCleanSession(cleanSession);
    	connOpts.setKeepAliveInterval(0);

		// Construct the MqttClient instance
		try {
			//String tmpDir = System.getProperty("java.io.tmpdir");
	    	//MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);
	    	
			//mqttClient = new MqttAsyncClient(this.brokerUrl, clientId, null);
			mqttClient = new MqttClient(this.brokerUrl, clientId, null);
			
			// Mqtt is not used by far - 2014/06
			//connect();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			logger.error("Unable to set up client: "+e.toString());
		}
	}
	
	public void connect (){
		try {
			mqttClient.connect(connOpts);
			
			// Set this wrapper as the callback handler
			mqttClient.setCallback(this);
			mqttClient.subscribe(subTopic, 2);
			
			logger.info("Connected @: " + subTopic + " " + mqttClient.getServerURI());
		} catch (MqttSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void connectionLost(Throwable e) {
		// TODO Auto-generated method stub
		logger.info("connection has lost..reconnecting..");
		
		e.printStackTrace();
		
		connect();
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> " + topic + ", " + message);
		
		String [] levelTopics = topic.split("/");
		
		if (levelTopics.length < 3){
			return;
		}

		//msgHandler.handleMessage(message);
		if (levelTopics[2].equals("chat")){
			msgHandler = ChatMessageHandler.getHandler();
		}
		else if (levelTopics[2].equals("location")){
			msgHandler = LocationMessageHandler.getHandler();
		}
		// match other handlers here
		
		String user = levelTopics[3];
		
		msgHandler.handleMessage(user, message);
	}
	
}
