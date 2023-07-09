package com.coordsafe.tcpgateway.tester;

import java.net.InetSocketAddress;
import java.util.Calendar;
import java.util.Date;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
//import org.apache.mina.example.sumup.ClientSessionHandler;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import com.coordsafe.tcpgateway.codec.CoordSafeProtocolCodecFactory;
import com.coordsafe.tcpgateway.handler.ServerMessageHandler;
import com.coordsafe.tcpgateway.locator.AuthenticationMsg;
import com.coordsafe.tcpgateway.locator.CommonPayload;
import com.coordsafe.tcpgateway.locator.GpsLocation;
import com.coordsafe.tcpgateway.locator.GpsPayload;
import com.coordsafe.tcpgateway.locator.LocationMsg;
import com.coordsafe.tcpgateway.locator.VerificationMsg;
import com.coordsafe.tcpgateway.locator.VerificationPayload;
import com.coordsafe.tcpgateway.locator.Version;

public class LocatorSimulator implements Runnable{
	private static final String HOSTNAME = "137.132.179.24"; // localhost

	private static final int PORT = 2222;

	private static final long CONNECT_TIMEOUT = 30 * 1000L; // 30 seconds

	// Set this to false to use object serialization instead of custom codec.
	private static final boolean USE_CUSTOM_CODEC = true;
	
	NioSocketConnector connector;

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws Exception 
	 */
	
	public LocatorSimulator () {
		connector = new NioSocketConnector();

		// Configure the service.
		connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);
		if (USE_CUSTOM_CODEC) {
			connector.getFilterChain().addLast(
					"codec",new ProtocolCodecFilter(new CoordSafeProtocolCodecFactory(true)));
		} else {
			connector.getFilterChain().addLast(
					"codec",new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		}
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.setHandler(new ServerMessageHandler());

	}
	
	public void run () {
		IoSession session;
		
		for (;;) {
			try {
				ConnectFuture future = connector.connect(new InetSocketAddress(
						HOSTNAME, PORT));
				future.awaitUninterruptibly();
				session = future.getSession();
				
				session.getConfig().setUseReadOperation(true);				

				sendAuthMessage(session);
				//sendVerificationMessage(session);
				sendLocationMessage(session);
				
				// disconnect after all msgs sent
				session.getCloseFuture().awaitUninterruptibly();
				//ReadFuture rf = session.read();
				
				//if(rf.getException() != null) {
			    //    throw new Exception(rf.getException().getMessage());
			    //}
				if (session.read().getMessage() != null){
					System.out.println("response recved: " + session.read().getMessage().toString());
				}

				//rf.awaitUninterruptibly();
				
				//connector.dispose();
				
				break;
			} catch (RuntimeIoException e) {
				System.err.println("Failed to connect.");
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		/*if (args.length == 0) {
			System.out.println("Please specify the list of any integers");
			return;
		}

		// prepare values to sum up
		int[] values = new int[args.length];
		for (int i = 0; i < args.length; i++) {
			values[i] = Integer.parseInt(args[i]);
		}*/

		NioSocketConnector connector = new NioSocketConnector();

		// Configure the service.
		connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);
		if (USE_CUSTOM_CODEC) {
			connector.getFilterChain().addLast(
					"codec",
					new ProtocolCodecFilter(new CoordSafeProtocolCodecFactory(
							true)));
		} else {
			connector.getFilterChain().addLast(
					"codec",
					new ProtocolCodecFilter(
							new ObjectSerializationCodecFactory()));
		}
		connector.getFilterChain().addLast("logger", new LoggingFilter());

		connector.setHandler(new ServerMessageHandler());

		IoSession session;
		
		for (;;) {
			try {
				ConnectFuture future = connector.connect(new InetSocketAddress(
						HOSTNAME, PORT));
				future.awaitUninterruptibly();
				session = future.getSession();
				
				session.getConfig().setUseReadOperation(true);				

				sendAuthMessage(session);
				//sendVerificationMessage(session);
				sendLocationMessage(session);
				
				// disconnect after all msgs sent
				session.getCloseFuture().awaitUninterruptibly();
				//ReadFuture rf = session.read();
				
				//if(rf.getException() != null) {
			    //    throw new Exception(rf.getException().getMessage());
			    //}
				if (session.read().getMessage() != null){
					System.out.println("response recved: " + session.read().getMessage().toString());
				}

				//rf.awaitUninterruptibly();
				
				//connector.dispose();
				
				break;
			} catch (RuntimeIoException e) {
				System.err.println("Failed to connect.");
				e.printStackTrace();
				Thread.sleep(5000);
			}
		}

		// wait until the summation is done
		//session.getCloseFuture().awaitUninterruptibly();

		//connector.dispose();
	}

	private static void sendAuthMessage(IoSession session) {
		AuthenticationMsg authMsg = new AuthenticationMsg();
		authMsg.getLocatorMsgHeader().setImeiCode("123456789012345");
		
		//Date now = new Date();
		Date now = new Date();
		
		// System.out.println("Size of Date :" + sizeof now)
		// now.getTime();
		authMsg.getLocatorMsgHeader().setServerTime(now.getTime());
		CommonPayload cp = new CommonPayload();
		cp.setGpsLocation(new GpsLocation(103.21, 30.21));
		//cp.setStatus((short) 0);
		authMsg.setCommonPayload(cp);
		session.write(authMsg);
		//System.out.println("Auth msg sent!");
	}

	private static void sendVerificationMessage(IoSession session) {
		VerificationMsg veriMsg = new VerificationMsg();
		veriMsg.getLocatorMsgHeader().setImeiCode("123456789012345");
		Date now = new Date();

		// System.out.println("Size of Date :" + sizeof now)
		// now.getTime();
		veriMsg.getLocatorMsgHeader().setServerTime(now.getTime());
		CommonPayload cp = new CommonPayload();
		cp.setGpsLocation(new GpsLocation(103.21, 30.21));
		//cp.setStatus((short) 0);
		veriMsg.setCommonPayload(cp);

		VerificationPayload veriPayload = new VerificationPayload(new Version(
				(byte) 1, (byte) 0), new Version((byte) 1, (byte) 0),
				new Version((byte) 1, (byte) 0));

		veriMsg.setVerPayload(veriPayload);
		session.write(veriMsg);

	}
	
	private static void sendLocationMessage(IoSession session) {
		while (true){
			LocationMsg locMsg = new LocationMsg();
			locMsg.getLocatorMsgHeader().setImeiCode("123456789012345");
			
			Calendar c = Calendar.getInstance();
			c.set(2012, 10, 28, 14, 33, 23);
			
			locMsg.getLocatorMsgHeader().setServerTime(c.getTimeInMillis());
			CommonPayload cp = new CommonPayload();
			cp.setGpsLocation(new GpsLocation(1.312831, 103.823949));
			//cp.setStatus((short) 0);
			locMsg.setCommonPayload(cp);
	
			GpsPayload gpsPl = new GpsPayload();
			gpsPl.setHasGpsFix(true);
			gpsPl.setNoSatellite((byte) 4);
			gpsPl.setAltitude(67);
			gpsPl.setAccuracy(4);
			gpsPl.setSpeed(16);
			//gpsPl.setmResults(new float[]{1.22f, 2.33f});
	
			locMsg.setGpsPayload(gpsPl);
			session.write(locMsg);
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
