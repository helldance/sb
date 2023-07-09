package com.coordsafe.httpgateway.messaging;

import org.apache.log4j.Logger;

public class DispatchMessageHandler implements MobileMessageHandler {
	private static Logger logger = Logger.getLogger("CommServer");
	private MobileMessageSender sender;

	@Override
	public void process(CsMessage message) {
		logger.info("Processing dispatch Order ... ");
		MessageEncoder mesgEncoder = MessageEncoderFactory.getMessageEncoder(message.getHeader().getMsgType());
		
		String dest_addr = message.getHeader().getDest();

		logger.info(dest_addr);

		//String[] dest_list = dest_addr.split(",");
		String to_send = mesgEncoder.encode(message);

		//for (String dest : dest_list) {
		sender = new DispatchMessageSender(to_send, dest_addr);
		Thread senderThread = new Thread(sender);
		senderThread.start();
		//sender.send(to_send, dest_addr);

		//}		
		//return mesgEncoder.encode(message);
	}

}
