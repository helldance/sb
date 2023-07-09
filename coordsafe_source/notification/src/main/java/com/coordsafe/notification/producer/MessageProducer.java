package com.coordsafe.notification.producer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;

import com.coordsafe.notification.entity.EventMessage;

public class MessageProducer {
	@Autowired
	protected JmsTemplate jmsTemplate;
/*
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}*/
	public void sendMessages(EventMessage msg) throws JMSException {
		System.out.println("PRODUCER");
/*		MessageCreator messageCreator = new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage("Hello World: Spring - JMS");
				//Message message = session.createObjectMessage();
				message.setStringProperty("user", "howang");
				System.out.println(message.getJMSDestination());
				return message;
			}
		};*/
		//jmsTemplate.send(messageCreator);
		
		MessagePostProcessor postProcessor = new MessagePostProcessor() {
			public Message postProcessMessage(Message message) throws JMSException {
					message.setJMSPriority(8);
					message.setStringProperty("user", "wanghongbin");
					message.setJMSReplyTo(jmsTemplate.getDefaultDestination());
					return message;
			}
		};
		
		
		jmsTemplate.convertAndSend(msg,postProcessor);
	}
}