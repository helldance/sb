package com.coordsafe.notification.listener;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TopicSubscribe{

	public static void main(String[] args) throws JMSException {
		
		System.out.println("LISTNER STARTED");
		
		ApplicationContext context = 
				new ClassPathXmlApplicationContext(new String[] 
						{ "com/coordsafe/notification/config/TopicSubscribe.xml" });
	}

}
