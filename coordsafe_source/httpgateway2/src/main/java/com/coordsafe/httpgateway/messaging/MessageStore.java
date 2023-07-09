package com.coordsafe.httpgateway.messaging;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class MessageStore extends Thread {
	private Hashtable<String, FailedEntity> FailedQueue = new Hashtable<String, FailedEntity>();
	private List<FailedEntity> to_del = new ArrayList<FailedEntity>();
	private RecipientProxy proxy = MDTEntityProxy.getInstance();
	private static Logger logger = Logger.getLogger("MessageStore");
	private static MessageStore tms = new MessageStore();

	private MessageStore() {

	}

	public static MessageStore getInstance() {
		if (tms == null)
			return new MessageStore();
		return tms;
	}

	public synchronized void addEntry(FailedEntity failedEntity) {
		FailedQueue.put(failedEntity.getKey(), failedEntity);

		logger.info("MessageStore: added entry");
	}

	public synchronized void removeEntry(FailedEntity failedEntity) {
		if (FailedQueue.containsKey(failedEntity.getKey())) {
			FailedQueue.remove(failedEntity.getKey());
			logger.info("MessageStore: removed entry");
		}
	}

	public int getQueueSize() {
		return this.FailedQueue.size();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (FailedQueue.size() != 0)
				logger.info("Queued: " + FailedQueue.size());
			if (to_del.size() != 0)
				logger.info("Todel: " + to_del.size());
			
			for (int i = 0; i < to_del.size(); i ++){
				tms.removeEntry(to_del.get(i));
			}
			
			to_del.clear();
			
			// check for add entry quest
			for (FailedEntity fe : FailedQueue.values()) {
				int count = fe.getRetry();

				logger.info("Retry No: " + count);

				// update count
				fe.setRetry(++count);

				if (count <= MessagingConstants.RETRY_LIMIT) {
					new ResenderThread(fe).start();
				} else {
					to_del.add(fe);
				}
				//}
			}

			try {
				Thread.sleep(MessagingConstants.RETRY_INTERVAL);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private class ResenderThread extends Thread {
		private FailedEntity failedEntity;

		public ResenderThread(FailedEntity failedEntity) {
			this.failedEntity = failedEntity;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				if (proxy.resendMessage(failedEntity.getRecipient(), failedEntity.getMessage()))
					to_del.add(failedEntity);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
