package com.coordsafe.httpgateway.messaging;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

public class JmsDispatchMessageAdapter implements MobileMessageAdapter, MessageListener {
	private static Logger logger = Logger.getLogger("CommServer");
	private List<MobileMessageHandler> handlers = new ArrayList<MobileMessageHandler>();
	private final String DEFAULT_TOPIC_NAME = "JMS/Dispatch";
	private ConnectionFactory cf;
	private String topicName;
	private static final String springContext = "spring-commserver.xml";
	private ApplicationContext context;
	//public MessageConsumer mc;

	public JmsDispatchMessageAdapter() {
		this.topicName = DEFAULT_TOPIC_NAME;
		initializeJMS();
	}

	public JmsDispatchMessageAdapter(String topicName) {
		this.topicName = topicName;
		initializeJMS();
	}

	@Override
	public void registerHandler(MobileMessageHandler mesgHandler) {
		handlers.add(mesgHandler);
	}

	@Override
	public void onMessage(Message m) {
		logger.info("Dispatch order message received.....");
		ObjectMessage msg = (ObjectMessage) m;
		try {
			Object object = msg.getObject();
			if (object instanceof CsDispatchOrderMessage || object instanceof CsSnapMessage) {
				for (MobileMessageHandler mmh : handlers) {
					mmh.process((CsMessage) object);
				}
			}
		} catch (JMSException ex) {
			ex.printStackTrace();
		}
	}

	private void initializeJMS() {
		context = ContextLoader.getCurrentWebApplicationContext();
		try {
			cf = (ConnectionFactory)context.getBean("connectionFactory");
			Connection conn = cf.createConnection();
			conn.start();
			Session sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination dest = sess.createTopic(topicName);
			MessageConsumer mc = sess.createConsumer(dest);
			mc.setMessageListener(this);
		} catch (JMSException je) {
			je.printStackTrace();
		}
	}
}
