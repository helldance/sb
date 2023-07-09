package com.coordsafe.httpgateway.messaging;

public interface MessageEncoder {
	public String encode(CsMessage message);
}

