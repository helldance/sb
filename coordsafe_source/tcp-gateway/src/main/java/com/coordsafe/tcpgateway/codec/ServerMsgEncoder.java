package com.coordsafe.tcpgateway.codec;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.coordsafe.tcpgateway.locator.LocatorMessage;

public class ServerMsgEncoder implements ProtocolEncoder {

	@Override
	public void dispose(IoSession session) throws Exception {
		
	}

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput output)
			throws Exception {
		IoBuffer buf = IoBuffer.allocate(50).setAutoExpand(true);

		//System.out.println("Encoding server2client message..");
		
		/*ServerResponseMessage srMsg = (ServerResponseMessage)message;
		
		buf.put(srMsg.header.service_id);
		buf.put(srMsg.header.msg_dir);
		buf.put(srMsg.header.msg_type);
		buf.putString(srMsg.header.serv_ip, Charset.forName("UTF-8").newEncoder());*/
		buf.putString(message.toString(), Charset.forName("UTF-8").newEncoder());
		
		buf.flip();
		output.write(buf);
		
	}

}
