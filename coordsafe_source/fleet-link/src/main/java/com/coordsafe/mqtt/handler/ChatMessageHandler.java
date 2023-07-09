/**
 * @author Yang Wei
 * @Date Nov 13, 2013
 */
package com.coordsafe.mqtt.handler;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.coordsafe.im.entity.ChatMessage;
import com.coordsafe.im.service.ImService;

/**
 * @author Yang Wei
 *
 */
public class ChatMessageHandler extends MqttMessageHandler{
	private static MqttMessageHandler handler;
	private static final Log log = LogFactory.getLog(ChatMessageHandler.class);
	
	@Autowired
	private ImService chatSvrs;
	
	private ChatMessageHandler(){}
	
	public static MqttMessageHandler getHandler() {
		if (handler == null)
			handler = new ChatMessageHandler();
		
		return handler;
	}

	@Override
	public void handleMessage(String topic, MqttMessage message) {
		String payload = message.toString();
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			ChatMessage chat = mapper.readValue(payload, ChatMessage.class);
			
			log.info("chat.." + chat.getMessageTime());
			
			//store chat message
			chatSvrs.create(chat);
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

}
