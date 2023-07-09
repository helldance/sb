package com.coordsafe.notification.producer;


import java.util.List;

import javax.jms.JMSException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import com.coordsafe.common.entity.EventTarget;
import com.coordsafe.event.entity.GenericEvent;
import com.coordsafe.notification.entity.EventMessage;
import com.coordsafe.notification.entity.NotificationSetting;

public class TopicProducer {

	public static void main(String argv[]) throws JMSException {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "com/coordsafe/notification/config/TopicProducer.xml" });
			
			//List<EventTarget> targetList = null;
			GenericEvent event = null ;
			NotificationSetting ns = null;			
			TopicMessageProducer myBean = (TopicMessageProducer) context.getBean("topicMessageProducer");
			//myBean.sendMessages(event, ns);
	        

	}
}
