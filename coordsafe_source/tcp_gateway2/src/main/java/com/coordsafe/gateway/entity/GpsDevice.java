package com.coordsafe.gateway.entity;

import java.math.BigInteger;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Document(collection = "devices")
public class GpsDevice {
	
	private String mongoID;
	@Id
	private BigInteger  id;
	private String deviceid;
	private String iport;
	private int channelid;
	
	
	
	public GpsDevice(String deviceid, String iport, int channelid) {
		super();
		this.deviceid = deviceid;
		this.iport = iport;
		this.channelid = channelid;
	}
	
	public GpsDevice() {
		// TODO Auto-generated constructor stub
	}

	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getIport() {
		return iport;
	}
	public void setIport(String iport) {
		this.iport = iport;
	}
	public int getChannelid() {
		return channelid;
	}
	public void setChannelid(int channelid) {
		this.channelid = channelid;
	}


}
