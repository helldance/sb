package com.coordsafe.httpgateway.messaging;

import java.io.IOException;
import java.net.UnknownHostException;

public interface RecipientProxy {
	public void register(MobileEntity recipient);
	public void update(MobileEntity mobileEntity);
	public void remove(MobileEntity mobileEntity);
	public void sendMessage(String recipientId, String msg) throws UnknownHostException, IOException;
	public boolean resendMessage(String recipientId, String msg) throws UnknownHostException, IOException;
	public void broadcastMessage(String encodeEnemy) throws UnknownHostException, IOException;
}

