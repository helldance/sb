package com.coordsafe.notification.listener;

import java.text.SimpleDateFormat;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.listener.SessionAwareMessageListener;

import com.coordsafe.notification.entity.EventMessage;

public class MessageListenerImpl implements SessionAwareMessageListener {
	  
	public void onMessage(Message message, Session session) {
		System.out.println("RECEIVER");
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss SSS"); 
			String time = sdf.format(message.getJMSTimestamp());
			System.out.println(message.getJMSDestination() + " on " + time +
					" Redeliveried?= " + message.getJMSRedelivered() + 
					" Priority= " + message.getJMSPriority() +
					" by " + message.getStringProperty("user"));
			System.out.println(message.getJMSMessageID() + "-----------" + message.getJMSReplyTo()); 
			
			
			ObjectMessage om = (ObjectMessage)message;
			EventMessage em = (EventMessage)om.getObject();
			//System.out.println(em.getNs().getCompany().getName() + "___________" + em.getClass());

		} catch (JMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		//TextMessage msg = (TextMessage) message;
			//System.out.println(msg.getText());
			//should adding more to process different messages: 

	}

}