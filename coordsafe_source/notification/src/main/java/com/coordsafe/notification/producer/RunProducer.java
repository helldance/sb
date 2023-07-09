package com.coordsafe.notification.producer;

import javax.jms.JMSException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.coordsafe.notification.entity.EventMessage;


public class RunProducer {
	public static void main(String[] args) throws JMSException {
		
		EventMessage msg = null;//new EventMessage(1,2,3);

		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "com/coordsafe/notification/config/JMSProducerConfig.xml" });
		MessageProducer myBean = (MessageProducer) context.getBean("simpleMessageProducer");
		myBean.sendMessages(msg);
	}
}