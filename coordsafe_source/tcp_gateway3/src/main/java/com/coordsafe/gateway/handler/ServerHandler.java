package com.coordsafe.gateway.handler;

import java.net.SocketAddress;
import java.text.SimpleDateFormat;






import javax.jms.MapMessage;
import javax.jms.Session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.coordsafe.common.entity.TransLocation;
import com.coordsafe.gateway.dao.GpsRawDataDAOImpl;
import com.coordsafe.gateway.entity.GpsDevice;
import com.coordsafe.gateway.entity.GpsRawData;
import com.coordsafe.gateway.entity.SzMsgHeader;
import com.coordsafe.gateway.entity.SzOneMsg;
import com.coordsafe.gateway.entity.VehicleParameter;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;


@Component
@Qualifier("serverHandler")
@Sharable
public class ServerHandler extends SimpleChannelInboundHandler<String> {
	@Autowired
	GpsRawDataDAOImpl rawDataDaoImpl;
	@Autowired
	@Qualifier("jmsTemplate")
	JmsTemplate jmsTemplate;
	@Autowired
	@Qualifier("jmsTemplateForAlarm")
	JmsTemplate jmsTemplateForAlarm;
	@Autowired
	VehicleParameter vehicleParameter;
	@Autowired
	MongoOperations mongoTemplate;
	
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyMMddHHmmss");
	private static ChannelGroup channels = new DefaultChannelGroup("GPS", null);
	private  AttributeKey<String> power = new AttributeKey<String>("power");
	private  AttributeKey<String> register = new AttributeKey<String>("register");
	private  AttributeKey<String> device = new AttributeKey<String>("device");

	@Override
	public void messageReceived(ChannelHandlerContext ctx, String msg)	throws Exception {
		
		System.out.println(msg);
		// To process the protocal msg received...
		// 1. Parser incoming message
		// 2. Store into DB;
		// 3. convert into Json Object && Sent object to ActiveMQ;

		// 1
		GpsRawData gpsdata = new GpsRawData();
		SzMsgHeader header = new SzMsgHeader();
		SzOneMsg omsg = new SzOneMsg();
		
		String[] parts = msg.split(",");
		String locatorType = "location";
		for(int i = 0; i < parts.length; i++)
			System.out.println(parts[i]);


		//Returned confirm Msg,ignore!
/*		if(parts[parts.length-1].equalsIgnoreCase("FFFFFFFF#")){
			return;
		}*/
		
		
		header.start = parts[0].substring(0, 1);
		header.producer = parts[0].substring(1,3);
		
		header.runningNo = parts[1];
		omsg.deviceId = parts[1];
		header.cmdType = parts[2];
		header.cmd = parts[2];
		omsg.header = header;
		//register the device
		this.registerDevice(omsg, ctx);
		
		if(header.cmdType.equalsIgnoreCase("BASE")){
			
		
			if(parts[parts.length-1].equalsIgnoreCase("FFFDFFFD#") || parts[parts.length-1].equalsIgnoreCase("FFFFFFFD#")){
				//Received the alarm message and sent out a message to shudown the alarm (*XX,YYYYYYYYYY,R7,HHMMSS # )
				
				String alarm_shutdown = "*HQ," + omsg.deviceId + ",R7," + parts[3] + "#";
				
				
			
				this.alarmShutdown(omsg, alarm_shutdown);
				System.out.println("Shut down the alarm from BASE commands...");
				
			}
			return;
		}
		if(header.cmdType.equalsIgnoreCase("V1")){
			omsg.time = parts[3];
			omsg.valid = parts[4];
			if(omsg.valid.equalsIgnoreCase("V"))
				return;
			
			omsg.latLng = parts[5] + parts[6] + parts[7] + parts[8];

			omsg.speed = parts[9];
			omsg.direction = parts[10];
			
			omsg.date = parts[11];
		}
		
		
		

		
		gpsdata.setDeviceId(omsg.deviceId);
		
		
		////////////////////////
		// 3 put into active queue

		TransLocation location = new TransLocation();

		location.deviceId = "3533" + "1" + omsg.deviceId;
		double[] _latLng = convertE6LatLng(omsg.latLng);
		location.lat = _latLng[1];
		location.lng = _latLng[0];

		location.gpsTime = sdf.parse(omsg.date.concat(omsg.time)).getTime() + 8 * 3600 * 1000;

		location.speed = Double.valueOf(omsg.speed);
		
		//please note that here!! The normal location or Alarm ?
		location.setLocatorType(locatorType);
		
		
		//Panic Alarm Msg
		if(parts[parts.length-1].equalsIgnoreCase("FFFFFFFD#")){
			//Received the alarm message and return a message to shudown the alarm (*XX,YYYYYYYYYY,R7,HHMMSS # )
			
			String alarm_shutdown = "*HQ," + omsg.deviceId + ",R7," + parts[3] + "#";
			
			
			
			System.out.println("In the Alarm Queue...");
			locatorType = "Alarm";
			MapMessage mmsg = this.jmsTemplateForAlarm.getConnectionFactory().createConnection().createSession(false, Session.AUTO_ACKNOWLEDGE).createMapMessage();
			
			mmsg.setString("deviceId", location.deviceId);
			mmsg.setString("time",omsg.time);
			mmsg.setDouble("lat", location.lat);
			mmsg.setDouble("lng", location.lng);
			
			jmsTemplateForAlarm.convertAndSend(mmsg);
			
			this.alarmShutdown(omsg, alarm_shutdown);
			System.out.println("Active MQ for Alarm finished...and shut down the alarm in location command");
			
		}
		
		//Normal location Msg
		//if(parts[parts.length-1].equalsIgnoreCase("FFFFFBFF#")){
		if(parts[parts.length-1].equalsIgnoreCase("FFFFFFFF#")){	

			
			jmsTemplate.convertAndSend(location);
			System.out.println("Active MQ for Location finished...");
			
		}


		
		///////////////////////
		
		if(header.cmd.equalsIgnoreCase("AP07")){
			System.out.println("The Device ID is " + omsg.deviceId);
			int channelID = this.rawDataDaoImpl.findChannelID(omsg.deviceId);
			System.out.println("The channel ID is " + channelID);
			
			for(Channel channel:channels){
				System.out.println(channel.id() + "===" + msg.substring(0, 17) + ")");
				//channel.write(msg.substring(0, 17) + ")");
			}
			channels.find(channelID).write(msg.substring(0, 17) + ")");
			return;
		}
		System.out.println("The register is =========" + ctx.attr(register).get());
		if(ctx.attr(register).get().equals("1")){//means not register.
			
			//it means this cmd is register cmd, 
			//1. record the deviceID_ip_port_channelID to a table
			//2. change the setting time to usual;
			//ctx.attr(power).set("0");
			ctx.attr(register).set("0");//just registered
			ctx.attr(device).set(omsg.deviceId);


			
			SocketAddress remoteAddress = ctx.channel().remoteAddress();
			int channelID = ctx.channel().id();
			if(rawDataDaoImpl.findDeviceNum(omsg.deviceId)>0){
				rawDataDaoImpl.removeDevice(omsg.deviceId);
				
				Query searchUserQuery = new Query(Criteria.where("deviceid").is(omsg.deviceId));
				GpsDevice gd = mongoTemplate.findOne(searchUserQuery, GpsDevice.class);
				this.mongoTemplate.remove(gd);
			}
		//	rawDataDaoImpl.addDeviceUsingPool(omsg.deviceId, remoteAddress, channelID);
			GpsDevice gpsDevice = new GpsDevice();
			gpsDevice.setDeviceid(omsg.deviceId);
			gpsDevice.setIport(remoteAddress.toString());
			gpsDevice.setChannelid(channelID);
			rawDataDaoImpl.addDevice(gpsDevice);
			
			
			mongoTemplate.save(gpsDevice);
			
			System.out.println("The device " + omsg.deviceId + " is registered");
			//return;
		}





		
		gpsdata.setRawData(msg);
		gpsdata.setGeneratetime(new java.sql.Timestamp(System
				.currentTimeMillis()));
		gpsdata.setGpsDate(omsg.date + omsg.time);
		// 2
		rawDataDaoImpl.insert(gpsdata);
		mongoTemplate.save(gpsdata);
		System.out.println("1. add - raw data : " + gpsdata);
		//Query searchUserQuery = new Query(Criteria.where("deviceId").is(omsg.deviceId));
		//GpsRawData gr = mongoTemplate.findOne(searchUserQuery, GpsRawData.class);
/*		List<GpsRawData> grList = mongoTemplate.find(searchUserQuery, GpsRawData.class);
		for(GpsRawData gr : grList)
			System.out.println("2. find - raw data : " + gr);*/
		


		// ctx.channel().write("");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
/*		System.out.println("Local Channel" + ctx.channel().localAddress().toString()
				+ " is active\n");*/
		
		String remoteAddress =  ctx.channel().remoteAddress().toString();
		System.out.println("Remote Channel" + remoteAddress	+ " is active\n");

		ctx.attr(power).set("0"); // 0 means power on; 1 means power off;
		ctx.attr(register).set("1");// 1 means no register; 0 means registered;
		
		channels.add(ctx.channel());
/*		for(Channel c:channels){
			System.out.println("channel is ===========" + c.remoteAddress()+ c.id());
		}*/
		
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("\nChannel is disconnected");
		rawDataDaoImpl.removeDevice(ctx.attr(device).get());
		
		Query searchUserQuery = new Query(Criteria.where("deviceid").is(ctx.attr(device).get()));
		GpsDevice gd = mongoTemplate.findOne(searchUserQuery, GpsDevice.class);
		this.mongoTemplate.remove(gd);
		super.channelInactive(ctx);
	}

	private double[] convertE6LatLng(String latLng) {
		String[] temp = new String[2];
		temp[0] = latLng.substring(0, 10);
		temp[1] = latLng.substring(10);

		System.out.println(temp[0] + " " + temp[1]);

		float lngVal = Float
				.valueOf(temp[1].substring(0, temp[1].length() - 1));
		int decPart = (int) lngVal;
		int min = decPart % 100;
		int deg = (decPart - min) / 100;
		float sec = lngVal - decPart;

		// System.out.println(decPart + " " + min + " " + deg + " " + sec );

		double formatedLng = deg + Double.valueOf(min) / 60 + sec / 60;

		if (temp[1].endsWith("W")) {
			formatedLng = -formatedLng;
		}

		// double lng = formatedLng*1E6;

		float latVal = Float
				.valueOf(temp[0].substring(0, temp[0].length() - 1));
		int decPart2 = (int) latVal;
		int min2 = decPart2 % 100;
		int deg2 = (decPart2 - min2) / 100;
		float sec2 = latVal - decPart2;

		// System.out.println(decPart2 + " " + min2 + " " + deg2 + " " + sec2 );

		double formatedLat = deg2 + Double.valueOf(min2) / 60 + sec2 / 60;

		if (temp[0].endsWith("S")) {
			formatedLat = -formatedLat;
		}

		// double lat = formatedLat*1E6;

		return new double[] { formatedLng, formatedLat };
	}
	
	private void alarmShutdown(SzOneMsg omsg, String msg){
		System.out.println("begin closing the alram...");
		System.out.println("The Device ID is " + omsg.deviceId);
		int channelID = this.rawDataDaoImpl.findChannelID(omsg.deviceId);
		System.out.println("The channel ID is " + channelID);
		
/*		for(Channel channel:channels){
			System.out.println(channel.id() + "===" + msg);
			//channel.write(msg.substring(0, 17) + ")");
		}*/
		ChannelFuture cf = channels.find(channelID).write(msg);
		System.out.println("finish closing the alarm..." + cf.isSuccess());
	}
	
	private void registerDevice(SzOneMsg omsg,ChannelHandlerContext ctx){
		if(ctx.attr(register).get().equals("1")){//means not register.
			
			//it means this cmd is register cmd, 
			//1. record the deviceID_ip_port_channelID to a table
			//2. change the setting time to usual;
			//ctx.attr(power).set("0");
			ctx.attr(register).set("0");//just registered
			ctx.attr(device).set(omsg.deviceId);


			
			SocketAddress remoteAddress = ctx.channel().remoteAddress();
			int channelID = ctx.channel().id();
			if(rawDataDaoImpl.findDeviceNum(omsg.deviceId)>0){
				rawDataDaoImpl.removeDevice(omsg.deviceId);
				
				Query searchUserQuery = new Query(Criteria.where("deviceid").is(omsg.deviceId));
				GpsDevice gd = mongoTemplate.findOne(searchUserQuery, GpsDevice.class);
				this.mongoTemplate.remove(gd);
			}
		//	rawDataDaoImpl.addDeviceUsingPool(omsg.deviceId, remoteAddress, channelID);
			GpsDevice gpsDevice = new GpsDevice();
			gpsDevice.setDeviceid(omsg.deviceId);
			gpsDevice.setIport(remoteAddress.toString());
			gpsDevice.setChannelid(channelID);
			rawDataDaoImpl.addDevice(gpsDevice);
			
			
			mongoTemplate.save(gpsDevice);
			
			System.out.println("The device " + omsg.deviceId + " is registered");
			//return;
		}

	}

}
