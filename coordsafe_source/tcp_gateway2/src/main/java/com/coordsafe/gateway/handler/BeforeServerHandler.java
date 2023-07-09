package com.coordsafe.gateway.handler;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.MessageList;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.ByteToMessageDecoder;

@Component
@Qualifier("beforeServerHandler")
@Sharable
public class BeforeServerHandler extends ByteToMessageDecoder {
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, MessageList<Object> out) {

		boolean validMessage = true;
		in.markReaderIndex();
		
		// Check the first byte;
		byte firstByte = in.readByte();
		if (firstByte != 40 ) {
			//in.resetReaderIndex();
			validMessage = false;
			System.out.println("Invalid Message as " + firstByte);

			//throw new CorruptedFrameException("Invalid Message: " + firstByte);
		}
		// Wait until the whole data is available.
		if(in.bytesBefore((byte)41) == -1){
			return;
		}
		
		
		int dataLength = in.bytesBefore((byte)41) + 2;
		in.resetReaderIndex();
/*		int dataLength = in.readInt();
		System.out.println("The dataLength is " + dataLength);
		System.out.println("The readableBytes is " + in.readableBytes());

		if (in.readableBytes() < dataLength) {
			in.resetReaderIndex();
			return;
		}*/

		// Convert the received data into a new GPS Message.
		byte[] decoded = new byte[dataLength];
		in.readBytes(decoded);
		if(dataLength != 95 || !validMessage ){
			in.discardReadBytes();
			return;
		}
/*		for(Byte b:decoded)
			System.out.print(b);
		System.out.println();
		System.out.println("--------------------------");*/
		out.add(Unpooled.copiedBuffer(decoded));
		//ctx.channel().write(decoded);

	}
}