package com.coordsafe.tcpgateway.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.coordsafe.tcpgateway.codec.*;
import com.coordsafe.tcpgateway.handler.LocatorMessageHandler;

public class CommServer {
	private static final int PORT = 2222;
	private SocketAcceptor acceptor = null;

	public CommServer() {
		acceptor = new NioSocketAcceptor();
	}

	public boolean bind() {
		acceptor.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new CoordSafeProtocolCodecFactory(false)));
		//acceptor.getSessionConfig().setReceiveBufferSize(16384);
		//acceptor.getSessionConfig().setReadBufferSize(16384);
		
		LoggingFilter log = new LoggingFilter();
		log.setMessageReceivedLogLevel(LogLevel.INFO);
		acceptor.getFilterChain().addLast("logger", log);
		acceptor.setHandler(new LocatorMessageHandler());
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);

		try {
			acceptor.bind(new InetSocketAddress(PORT));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	public static void main(String[] args) throws Exception {
		CommServer server = new CommServer();
		if (!server.bind()) {
			System.out.println("Server failed to start");
		} else {
			System.out.println("Server started successfully");
		}
	}
}
