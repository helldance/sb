package com.coordsafe.tcpgateway.codec;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class ServerMsgDecoder implements ProtocolDecoder {

	@Override
	public void decode(IoSession session, IoBuffer buf, ProtocolDecoderOutput output)
			throws Exception {
		String s = buf.getString(Charset.forName("UTF-8").newDecoder());
		output.write(s);

	}

	@Override
	public void dispose(IoSession arg0) throws Exception {
		

	}

	@Override
	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1)
			throws Exception {
		

	}

}
