package com.coordsafe.notification.listener;

import javax.jms.JMSException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RunListener {
	public static void main(String[] args) throws JMSException {
		
		System.out.println("LISTNER STARTED");
		
		ApplicationContext context = 
				new ClassPathXmlApplicationContext(new String[] { "com/coordsafe/notification/config/JMSListenerConfig.xml" });
	}
}