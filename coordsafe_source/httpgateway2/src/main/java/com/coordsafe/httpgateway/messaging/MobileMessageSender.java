package com.coordsafe.httpgateway.messaging;

public interface MobileMessageSender extends Runnable{
	public void send (String message, String dest);
}
