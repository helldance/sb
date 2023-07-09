package com.coordsafe.httpgateway.messaging;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

import org.apache.log4j.Logger;

public class DispatchMessageSender implements MobileMessageSender, Runnable {

	private static Logger logger = Logger.getLogger("DispatchMessageSender");
	private Thread senderThread;
	private String msg, dest;
	//private DispatchMessageHandler dmhandler = new DispatchMessageHandler();
	
	public DispatchMessageSender (String msg, String dest){
		this.msg = msg;
		this.dest = dest;
	}

	@Override
	public void send(String msg, String dest) {
		// TODO Auto-generated method stub
		try {
			MDTEntityProxy.getInstance().sendMessage(dest, msg);
			//logger.info("Message is sent to: " + dest);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Unknown host");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error transmission");
		}
		
		logger.info("Message is sent to: " + dest);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			MDTEntityProxy.getInstance().sendMessage(dest, msg);
		} 
		catch (SocketException se) {
			// TODO Auto-generated catch block
			
			logger.error(se.getStackTrace());
			
			addTempStore(dest, msg);			
		}
		catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Unknown host");
			
			//addTempStore(dest, msg);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error transmission");
		}
	}
	
	public void addTempStore(String recipient, String msg) {
		Calendar cal = Calendar.getInstance();		
		String key = cal.get(Calendar.YEAR) + "" + cal.get(Calendar.MONTH) + "" + cal.get(Calendar.DATE) + "" + cal.get(Calendar.HOUR_OF_DAY) + "" + cal.get(Calendar.MINUTE) + "" + cal.get(Calendar.SECOND);

		logger.info("Timestamp: " + key);
		
		/*HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("recipient", recipient);
		hm.put("message", msg);
		hm.put("retry", 0);*/
		MessageStore tms = MessageStore.getInstance();
		tms.addEntry(new FailedEntity(key, recipient, msg, key, 0));		
	}
}
