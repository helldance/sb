/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.coordsafe.gateway.client.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.MessageList;



import org.slf4j.*;

/**
 * Handler implementation for the echo client.  It initiates the ping-pong
 * traffic between the echo client and server by sending the first message to
 * the server.
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(EchoClientHandler.class);

    private ChannelHandlerContext ctx;

	String gpsInfo = "4048495154495051525354554866804853495051525354555657484950515253485648535052654849495546535657507849485152544654525754694848484649494849505249515051465655484848484848484876484848525348656641";
    
    String gpsString = "(013612345670BP05123456789012345080524A0117.5892N10346.6496E000.1101241323.8710000000L000450AB)";
    String realGPS = gpsString.replace(" ", "");
    
    String gpsStart;

    String gpsStop = "(013612345670BP05123456789012345080524A0117.5892N10346.6496E000.1101241323.8710000000L000450AB)";
    String deviceNotification;

    /**
     * Creates a client-side handler.
     */
    public EchoClientHandler(int firstMessageSize) throws Exception{
    	System.out.println("Simulator " + firstMessageSize);
    	if(firstMessageSize < 0)
    		deviceNotification = "(013612345670AP071234567891X2345080524A0117.5892N10346.6496E000.1101241323.8700000000L000450AB)";
        if(firstMessageSize < 10)
        	gpsStart = "(013612345670BP01123456789" + firstMessageSize + "X2345080524A0117.5892N10346.6496E000.1101241323.8700000000L000450AB)";
        else if(firstMessageSize<100 && firstMessageSize > 9)
        	gpsStart = "(013612345670BP01123456789" + firstMessageSize + "Y345080524A0117.5892N10346.6496E000.1101241323.8700000000L000450AB)";
		else if(firstMessageSize<1000 && firstMessageSize > 99)
        	gpsStart = "(013612345670BP01123456789" + firstMessageSize + "Z45080524A0117.5892N10346.6496E000.1101241323.8700000000L000450AB)";

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws InterruptedException {
    	
    	this.ctx = ctx;
    	sentGPSMessage(ctx);
    	//sentDeviceNotificationMessage(ctx);
    	//sentGpsStar(ctx);
    }
    private void sentGpsStar(ChannelHandlerContext ctx2) {
		// TODO Auto-generated method stub
		ctx.write(getMessage(gpsStart));
	}

	private void sentDeviceNotificationMessage(ChannelHandlerContext ctx2) {
		// Read GPS version,response BP07
    	//081129141830AP07
    	
    	ctx.write(getMessage(deviceNotification));
		
	}

	public void sentGPSMessage(ChannelHandlerContext ctx){
    	System.out.println("Begin sending ..." + ctx.name());
    	for(;;){
    		
    		
    		ctx.write(getMessage(gpsStart));
    		try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}    
    	
    }
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageList<Object> msgs) throws Exception {
    	logger.info("Received msg from server" + ctx.toString());
    	if(msgs.size() > 0)
    		System.out.println("There are msg" + msgs.size());
    	else
    		System.out.println("No received Msg is ");
    	
    	
/*    	
    	ByteBuf bf = getMessage(gpsStop);
		
		ctx.write(bf);	*/
		
/*		while(sendTimes < 3){
			ctx.write(getMessage());	
			sendTimes++;
		}*/
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        //logger.log(Level.WARNING, "Unexpected exception from downstream.", cause);
        ctx.close();
    }
    private ByteBuf getMessage(String msg){
    	ByteBuf firstMessage2 = Unpooled.buffer(256);
    	byte[] bb = msg.getBytes();
        for(byte bb1:bb){
        	firstMessage2.writeByte(bb1);
        }
        return firstMessage2;
    }
}
