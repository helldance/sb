package com.coordsafe.messaging;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * @author Yang Wei
 * @Date Mar 15, 2014
 */
@Service
public class DownStreamMessageServiceImpl implements DownstreamMessagingService {
	private Socket sock;
	private String host = "137.132.145.228";
	private int port = 2227;
	
	private static Log log = LogFactory.getLog(DownStreamMessageServiceImpl.class);

	@Override
	public void sendByteMessage(ByteMessage byteMsg) {
		try {
			if (sock == null || sock.isInputShutdown()){
				log.info("create socket connection to tcp..");
				
				sock = new Socket(host, port);
			}
		    
		    sock.getOutputStream().write(byteMsg.allBytes());
		    
		    log.info("send output stream to TCP..");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sock = null;
			//sock.close();
		}
		
	}
	
	public void estabConnection (){
		try {
			sock = new Socket(host, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void sendUpdateCallcenterMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendUpdateAPNMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendSleepCommand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendWakeupCommand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendFactoryResetCommand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendRebootCommand() {
		// TODO Auto-generated method stub
		
	}

}
