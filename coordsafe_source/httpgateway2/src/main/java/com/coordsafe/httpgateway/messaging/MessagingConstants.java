package com.coordsafe.httpgateway.messaging;

public interface MessagingConstants {
	static final int PORT_NUM = 8899;
	static final int MAX_DISPATCH_THREAD = 20;
	static final int RETRY_LIMIT = 5;
	static final long RETRY_INTERVAL = 10*1000;
}
