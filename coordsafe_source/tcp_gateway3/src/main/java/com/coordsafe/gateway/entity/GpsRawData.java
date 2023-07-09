package com.coordsafe.gateway.entity;

import java.math.BigInteger;
import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "rawdatas")
public class GpsRawData {
	
	
	private String mongoID;
	
	@Id
	BigInteger  id;
	
	String deviceId;
	String rawData;
	String gpsDate;
	Timestamp generatetime;

	public GpsRawData() {
		// TODO Auto-generated constructor stub
	}
	public GpsRawData(BigInteger  int1, String string, String string2, String string3) {
		// TODO Auto-generated constructor stub
	}
	public BigInteger  getId() {
		return id;
	}
	public void setId(BigInteger  id) {
		this.id = id;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getRawData() {
		return rawData;
	}
	public void setRawData(String rawData) {
		this.rawData = rawData;
	}
	public String getGpsDate() {
		return gpsDate;
	}
	public void setGpsDate(String gpsDate) {
		this.gpsDate = gpsDate;
	}
	public Timestamp getGeneratetime() {
		return generatetime;
	}
	public void setGeneratetime(Timestamp generatetime) {
		this.generatetime = generatetime;
	}


}
