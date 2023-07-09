package com.coordsafe.tcpgateway.handler;

import java.net.SocketAddress;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coordsafe.tcpgateway.locator.AuthenticationMsg;
import com.coordsafe.tcpgateway.locator.GpsLocation;
import com.coordsafe.tcpgateway.locator.GpsPayload;
import com.coordsafe.tcpgateway.locator.LocationMsg;
import com.coordsafe.tcpgateway.locator.VerificationMsg;

public class LocatorMessageHandler implements IoHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(LocatorMessageHandler.class); 
	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1)
			throws Exception {
		

	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		//System.out.println("\nServer: message recieved from client: \nHeader + CommonPayload: " + message.toString());
		
		/*for (byte b : message.toString().getBytes()){
			System.out.print(b + " ");
		}*/
		
		if (message instanceof AuthenticationMsg) {
			//System.out.println("Authenticating Locator ...");
			
			//update data to backend server
			
			// send response back to client
			SocketAddress remoteAddr = session.getRemoteAddress();
			
			//WriteFuture wf = session.write("ACK");
			//wf.awaitUninterruptibly();
			
			/*if (wf.isWritten()){
				System.out.println("ACK sent");
			}*/
						
			session.write("ACK");
		}
		
		else if (message instanceof VerificationMsg) {
			//System.out.println("Processing Verification Message");
		}
		
		else if (message instanceof LocationMsg) {
			//System.out.println(((LocationMsg)message).getGpsPayload().toString());
			
			String imei = ((LocationMsg) message).getLocatorMsgHeader().getImeiCode();
			
			GpsLocation loc = ((LocationMsg) message).getCommonPayload().getGpsLocation();
			double lat = loc.getLatitude();
			double lng = loc.getLongitude();
			
			GpsPayload gpl = ((LocationMsg) message).getGpsPayload();
			float speed = gpl.getSpeed();
			double altitude = gpl.getAltitude();
			
			String url = "tcp://localhost:61616?wireFormat.maxInactivityDuration=0";
			String locationQ = "jms/LocationUpdate";
			
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
			
			try {
				Connection conn = connectionFactory.createConnection();
				conn.start();
				
				Session sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
				Destination dest = sess.createTopic(locationQ);
				MessageProducer producer = sess.createProducer(dest);
				
				MapMessage mmsg = sess.createMapMessage();
				mmsg.setString("deviceId", imei);
				mmsg.setDouble("lat", lat);
				mmsg.setDouble("lng", lng);
				mmsg.setDouble("alt", altitude);
				mmsg.setFloat("speed", speed);
				
				producer.send(mmsg);		
				
				conn.close();
			} catch (JMSException e) {
				System.out.println(e.getStackTrace());
			}						
		}
		
		else {
			
		}
		
		//session.getCloseFuture().awaitUninterruptibly();
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("Server: message sent back '" + message.toString() + "'");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		SocketAddress remoteAddress = session.getRemoteAddress();
	    //server.removeClient(remoteAddress);
		
		System.out.println("Server: session closed");
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		SocketAddress remoteAddress = session.getRemoteAddress();
	    //server.addClient(remoteAddress);
		//System.out.println("Server: session created");
	}

	@Override
	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("Server: session idle");
	}

	@Override
	public void sessionOpened(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Server: session opened");
	}

}
