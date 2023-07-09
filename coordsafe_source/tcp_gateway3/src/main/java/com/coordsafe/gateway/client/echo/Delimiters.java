package com.coordsafe.gateway.client.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public final class Delimiters {

	public static ByteBuf[] XXEEDelimiter() {
		return new ByteBuf[] { Unpooled.wrappedBuffer("XXEE".getBytes()) };
	}

}