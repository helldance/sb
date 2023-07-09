package com.coordsafe.tcpgateway.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import com.coordsafe.tcpgateway.codec.LocatorMsgDecoder;
import com.coordsafe.tcpgateway.codec.LocatorMsgEncoder;
import com.coordsafe.tcpgateway.codec.ServerMsgDecoder;
import com.coordsafe.tcpgateway.codec.ServerMsgEncoder;

public class CoordSafeProtocolCodecFactory implements ProtocolCodecFactory {
	private ProtocolEncoder encoder;
	private ProtocolDecoder decoder;

	public CoordSafeProtocolCodecFactory(boolean client) {
		if (client) {
			encoder = new LocatorMsgEncoder();
			decoder = new ServerMsgDecoder();
		} else {
			encoder = new ServerMsgEncoder();
			decoder = new LocatorMsgDecoder();
		}
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		return encoder;
	}

}
