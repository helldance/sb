package com.coordsafe.messaging;

/**
 * @author Yang Wei
 * @Date Mar 15, 2014
 */
public interface DownstreamMessagingService {
	public void sendByteMessage(ByteMessage byteMsg);
	
	public void sendUpdateCallcenterMessage();
	public void sendUpdateAPNMessage ();
	public void sendSleepCommand();
	public void sendWakeupCommand();
	public void sendFactoryResetCommand();
	
	public void sendRebootCommand();
}
