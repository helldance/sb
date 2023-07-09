package com.coordsafe.httpgateway.messaging;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.log4j.Logger;

public class MDTEntityProxy implements RecipientProxy {
	private static Logger logger = Logger.getLogger("CommServer");
	private static RecipientProxy mobileProxy = new MDTEntityProxy();

	private Hashtable<String, MobileEntity> userTable = new Hashtable<String, MobileEntity>();
	private Hashtable<String, MobileEntity> groupTable = new Hashtable<String, MobileEntity>();
	private Hashtable<String, MobileEntity> entityTable = new Hashtable<String, MobileEntity>();

	//private Hashtable<String, String> FailedQueue = new Hashtable<String, String>();

	private MDTEntityProxy() {

	}

	public static RecipientProxy getInstance() {
		if (mobileProxy == null) {
			mobileProxy = new MDTEntityProxy();
		}
		return mobileProxy;

	}

	@Override
	public synchronized void register(MobileEntity mobileEntity) {
		entityTable.put(mobileEntity.getEntityId(), mobileEntity);
		userTable.put(mobileEntity.getUserId(), mobileEntity);
		groupTable.put(mobileEntity.getGroupId(), mobileEntity);

		logger.info("Entity table size:  " + entityTable.size() + " " + entityTable.get(mobileEntity.getEntityId()));
		logger.info("User table size:  " + userTable.size() + " " + userTable.get(mobileEntity.getUserId()));
		logger.info("Group table size:  " + groupTable.size() + " " + groupTable.get(mobileEntity.getGroupId()));
	}

	@Override
	public synchronized void remove(MobileEntity mobileEntity) {
		if (userTable.containsKey(mobileEntity.getUserId()))
			userTable.remove(mobileEntity.getUserId());
		if (groupTable.containsKey(mobileEntity.getGroupId()))
			groupTable.remove(mobileEntity.getGroupId());
	}

	@Override
	public synchronized void update(MobileEntity mobileEntity) {
		entityTable.put(mobileEntity.getEntityId(), mobileEntity);
		userTable.put(mobileEntity.getUserId(), mobileEntity);
		groupTable.put(mobileEntity.getGroupId(), mobileEntity);
	}

	public MobileEntity getByUserId(String userId) {

		if (userTable.containsKey(userId))
			return userTable.get(userId);
		else
			return null;
	}

	public MobileEntity getByGroupId(String groupId) {
		if (groupTable.containsKey(groupId))
			return groupTable.get(groupId);
		else
			return null;
	}

	public MobileEntity getByEntityId(String entityId) {
		if (entityTable.containsKey(entityId))
			return entityTable.get(entityId);
		else
			return null;
	}

	public Hashtable<String, MobileEntity> getUserTable() {
		return userTable;
	}

	public Hashtable<String, MobileEntity> getGroupTable() {
		return groupTable;
	}

	public Hashtable<String, MobileEntity> getEntityTable() {
		return entityTable;
	}
	
	// broadcast to all recipients
	public void broadcastMessage (String msg) throws UnknownHostException, IOException{
		Enumeration<MobileEntity> enm = entityTable.elements();
		
		while (enm.hasMoreElements()){
			MobileEntity me = enm.nextElement();
			
			if (me.getChannel() == ChannelType.TCPIP) {
				String ip = me.getChannelRefNo();

				new Thread(new SocketSenderThread(ip, msg)).start();
			}
		}
	}
	
	public boolean resendMessage(String recipientId, String msg) throws UnknownHostException, IOException {
		if (groupTable.containsKey(recipientId)) {
			MobileEntity me = groupTable.get(recipientId);
			if (me.getChannel() == ChannelType.TCPIP) {
				String ipAddress = me.getChannelRefNo();

				Socket socket = new Socket(ipAddress, MessagingConstants.PORT_NUM);
				PrintWriter pw = new PrintWriter(socket.getOutputStream());
				pw.println(msg);
				pw.flush();
				socket.close();

				logger.info("Message is sent to :" + ipAddress + ":" + MessagingConstants.PORT_NUM + " in groupTable");

				return true;
			}
		} else if (userTable.containsKey(recipientId)) {
			MobileEntity me = userTable.get(recipientId);
			if (me.getChannel() == ChannelType.TCPIP) {
				String ipAddress = me.getChannelRefNo();

				Socket socket = new Socket(ipAddress, MessagingConstants.PORT_NUM);
				PrintWriter pw = new PrintWriter(socket.getOutputStream());
				pw.println(msg);
				pw.flush();
				socket.close();

				logger.info("Message is sent to :" + ipAddress + ":" + MessagingConstants.PORT_NUM+ " in userTable");

				return true;
			}
		} else if (entityTable.containsKey(recipientId)) {
			MobileEntity me = entityTable.get(recipientId);
			if (me != null) {
				logger.info("Recipient found in entityTable");

				return false;
			}
		} else {
			logger.info("Message resent fail, group table does not contain the recipient.");

			return false;
		}

		return false;
	}

	public void sendMessage(String recipientId, String msg) throws UnknownHostException, IOException{
		if (groupTable.containsKey(recipientId)) {
			MobileEntity me = groupTable.get(recipientId);
			if (me.getChannel() == ChannelType.TCPIP) {

				String ipAddress = me.getChannelRefNo();

				Socket socket = new Socket(ipAddress, MessagingConstants.PORT_NUM);
				//socket.setSoTimeout(MessagingConstants.TCP_TIMEOUT);
				//if (socket == null)
					//throw new SocketException();
				
				PrintWriter pw = new PrintWriter(socket.getOutputStream());
				pw.println(msg);
				pw.flush();
				socket.close();

				logger.info("Message is sent to :" + ipAddress + ":" + MessagingConstants.PORT_NUM + " in groupTable");
			}
		} else if (userTable.containsKey(recipientId)) {
			MobileEntity me = userTable.get(recipientId);
			if (me.getChannel() == ChannelType.TCPIP) {
				String ipAddress = me.getChannelRefNo();

				Socket socket = new Socket(ipAddress, MessagingConstants.PORT_NUM);
				PrintWriter pw = new PrintWriter(socket.getOutputStream());
				pw.println(msg);
				pw.flush();
				socket.close();

				logger.info("Message is sent to :" + ipAddress + ":" + MessagingConstants.PORT_NUM + " in userTable");
			}
		} else if (entityTable.containsKey(recipientId)) {
			MobileEntity me = entityTable.get(recipientId);
			if (me != null) {
				logger.info("Recipient found in entityTable");
			}
		} else {
			//Calendar cal = Calendar.getInstance();
			//String key = cal.YEAR + " " + cal.MONTH + " " + cal.DATE + " " + cal.HOUR_OF_DAY + " " + cal.MINUTE + " " + cal.SECOND;

			//logger.info("Timestamp: " + key);

			addTempStore(recipientId, msg);

			logger.info("Message sent fail, group table does not contain the recipient. \nMessage is stored at failed sent queue");
		}
	}

	public void addTempStore(String recipient, String msg) {
		
		Calendar cal = Calendar.getInstance();		
		String key = cal.get(Calendar.YEAR) + "" + cal.get(Calendar.MONTH) + "" + cal.get(Calendar.DATE) + "" + cal.get(Calendar.HOUR_OF_DAY) + "" + cal.get(Calendar.MINUTE) + "" + cal.get(Calendar.SECOND);

		logger.info("Timestamp: " + key);
		
		/*Hashtable<String, Object> ht = new Hashtable<String, Object>();
		ht.put("id", key);
		ht.put("recipient", recipient);
		ht.put("message", msg);
		ht.put("retry", 0);*/
				
		//TempMessageStore.FailedQueue.add(hm);
		//MessageStore tms = MessageStore.getInstance();
		//tms.addEntry(new FailedEntity(key, recipient, msg, key, 0));
	}

	class SocketSenderThread implements Runnable {
		String dest_ip;
		String msg;

		public SocketSenderThread (String ip, String msg){
			dest_ip = ip;
			this.msg = msg;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Socket socket = new Socket(dest_ip, MessagingConstants.PORT_NUM);

				PrintWriter pw = new PrintWriter(socket.getOutputStream());
				pw.println(msg);
				pw.flush();

				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			logger.info("Message is sent to :" + dest_ip + ":" + MessagingConstants.PORT_NUM + " in groupTable");
		}
		
	}

}
