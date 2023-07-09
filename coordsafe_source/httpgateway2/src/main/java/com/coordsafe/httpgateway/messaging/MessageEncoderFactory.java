package com.coordsafe.httpgateway.messaging;

public class MessageEncoderFactory {
	public static MessageEncoder getMessageEncoder(CsMessageType type) {
		if  (type == CsMessageType.REQ_LOCATION) {
			return new XmlDispatchMessageEncoder();
		}
		
		return new XmlSnapMessageEncoder();
	}
}

