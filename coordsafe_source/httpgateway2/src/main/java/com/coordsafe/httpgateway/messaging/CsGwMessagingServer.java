package com.coordsafe.httpgateway.messaging;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

// CoordSafe Gateway Messaging Server
public class CsGwMessagingServer implements MobileMessageHandler, Runnable{
	private ServerSocket serverSocket;
	private final int PORT = 1334;
	private Hashtable<String,Socket> clientList = new Hashtable<String,Socket>();
	private static Logger logger = Logger.getLogger("CsGwMessagingServer");
	private ExecutorService execSrvc;
	private JmsDispatchMessageAdapter dispatcher;

	public CsGwMessagingServer() {		
		// Create thread pool
		execSrvc = Executors.newFixedThreadPool(MessagingConstants.MAX_DISPATCH_THREAD);

		MobileMessageAdapter mma = new JmsDispatchMessageAdapter();
		MobileMessageAdapter snapAdapter = new JmsSnapMessageAdapter();
		mma.registerHandler(this);
		snapAdapter.registerHandler(this);
	}

	public void run () {
		logger.info("Starting GW Messaging Server ... ");
		
		try {
			// creates server socket
			if (!openServerSocket()){
				logger.error("failed to open server socket!!!");
				return;
			}
			
			logger.info("GW Messaging Server Started");
			
			Socket client = null;
			while (true) {
				client = serverSocket.accept();
				
				System.out.println("Connected!\nClient: " + client.toString());
				
				// get input and output stream
				InputStream is = client.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				client.getInputStream().read();
				
				MobileEntity mu = (MobileEntity)ois.readObject(); 
				System.out.println("///" + mu.getEntityId());
				clientList.put(mu.getEntityId(),client);
			}

		} catch (IOException ioEx) {
			ioEx.printStackTrace();
			//openServerSocket();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		
		dispatcher = new JmsDispatchMessageAdapter();
	}
	
	private boolean openServerSocket (){
		try {
			serverSocket = new ServerSocket(PORT);
			serverSocket.setSoTimeout(0); // Server socket always on waiting for clients
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public void process(CsMessage message) {
		/*logger.info(message.getHeader());
		Header header = message.getHeader();
		switch (header.getType()) {
		case DISPATCH_ORDER:
			sendDispatchOrder(message);
			break;
		case BROADCAST_MESG:
			sendBroadcastMessage(message);
			break;
		case SNAP_MESG:
			sendSnapMessage(message);
			break;
		default:
			break;
		}*/
	}

	private void sendBroadcastMessage(CsMessage message) {
		Iterator itr = clientList.values().iterator();
		
		for (;itr.hasNext();) {
			Socket socket = (Socket)itr.next();
			// dos.writeBytes(toSend);
			try {
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				dos.write(message.toString().getBytes());
				dos.flush();
			} catch (IOException ioEx) {
				logger.error("Unable to send message to " + socket.getInetAddress());
			}
		}
	}

	private void sendBroadcastMessage(String message) {
		Iterator itr = clientList.values().iterator();
		
		for (;itr.hasNext();) {
			Socket socket = (Socket)itr.next();

			SenderProxy sp = new SenderProxy(socket, message);
			sp.start();
			
/*			boolean retry = true;
			int retryCount = 0;
			
			
			while (retry && retryCount < 3)
				try {
					DataOutputStream dos = new DataOutputStream(socket
							.getOutputStream());
					dos.write(message.toString().getBytes());
					dos.flush();
					retry = false;
				} catch (IOException ioEx) {

					retryCount++;
					try {
						Thread.sleep((long) 5000);
					} catch (InterruptedException e) {
						logger.error("Sleeping is interrupted!");
					}
					logger.error("Unable to send message to "
							+ socket.getInetAddress());
				}
*/		}
	}
}
