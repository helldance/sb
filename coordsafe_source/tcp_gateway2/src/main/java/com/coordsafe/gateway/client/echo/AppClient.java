package com.coordsafe.gateway.client.echo;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.channel.ChannelFuture;

public class AppClient {
	private static  String  deviceNotification = "(013612345670AP071234567891X2345080524A0117.5892N10346.6496E000.1101241323.8700000000L000450AB)";
    EventLoopGroup group = new NioEventLoopGroup();
    private static Channel channel;
    public static void main(String[] args){
    	
    	AppClient client = new AppClient();
    	if(channel == null)
    		channel = client.getChannel();
    	if(!channel.isActive())
    		channel = client.getChannel();
    	client.sentMsg(channel);
    		
    }
    public void sentMsg(Channel channel){
    	if(channel.isActive())
    		channel.write(getMessage(deviceNotification));
    }

    public Channel getChannel(){

    	Bootstrap b = new Bootstrap();
        b.group(group)
         .channel(NioSocketChannel.class)
         .option(ChannelOption.TCP_NODELAY, true)
         .handler(new ChannelInitializer<SocketChannel>() {
             @Override
             public void initChannel(SocketChannel ch) throws Exception {
                 ch.pipeline().addLast(
                         new LoggingHandler(LogLevel.INFO));
                         
             }
         });
    	ChannelFuture future = b.connect(new InetSocketAddress("localhost", 2225));
    	Channel channel = future.awaitUninterruptibly().channel();
    	return channel;
    	//ChannelFuture response = connector.write(deviceNotification);
    
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
