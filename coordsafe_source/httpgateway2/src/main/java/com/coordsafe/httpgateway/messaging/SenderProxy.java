package com.coordsafe.httpgateway.messaging;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.log4j.Logger;

public class SenderProxy extends Thread {

	private Socket socket;
	private CsMessage message;
	private String textMessage;
	private static Logger logger = Logger.getLogger("SocketThread");
	private final int RETRY = 3;
	private final int RETRY_INTERVAL = 5000;

	public SenderProxy () {
	}

	public void run() {
		boolean retry = true;
		int retryCount = 0;

		while (retry && retryCount < RETRY) {
			try {
				DataOutputStream dos = new DataOutputStream(socket
						.getOutputStream());
				dos.write(textMessage.toString().getBytes());
				dos.flush();
				retry = false;
				logger.info("Successfully send message to "
						+ socket.getInetAddress());

				// InputStreamReader isr = new
				// InputStreamReader(socket.getInputStream());
				// isr.
			} catch (IOException ioEx) {
				ioEx.printStackTrace();
				retryCount++;
				try {
					Thread.sleep((long) RETRY_INTERVAL);
				} catch (InterruptedException e) {
					logger.error("Sleeping is interrupted!");
				}
				logger.error("Unable to send message to "
						+ socket.getInetAddress() + ", retry");
			}
		}

		if (retry) {
			logger.error("Failed to send message to "
							+ socket.getInetAddress());
			try {
				socket.close();
			} catch (IOException ioe) {
				logger.error("Encounter error when closing socket");

			}
		}
	}

	public SenderProxy (Socket socket, CsMessage message) {
		this.socket = socket;
		this.message = message;
	}

	public SenderProxy (Socket socket, String textMessage) {
		this.socket = socket;
		this.textMessage = textMessage;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
