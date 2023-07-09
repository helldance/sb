package com.coordsafe.httpgateway.entity;
/*
 * For authentication
 */
public class Locator {
	public int service_id; //1 for elderly
	public int msg_direction; // 0 - l-> s
	public int msg_type; // 0 : authentication
	public String imei;
	public String login_time;
	//public GpsLocation location;
	public GpsData gpsData;
	/**
	 * @param service_id
	 * @param msg_direction
	 * @param msg_type
	 * @param imei
	 * @param login_time
	 * @param location
	 */
	public Locator(int service_id, int msg_direction, int msg_type,
			String imei, String login_time, GpsData gpsData) {
		super();
		this.service_id = service_id;
		this.msg_direction = msg_direction;
		this.msg_type = msg_type;
		this.imei = imei;
		this.login_time = login_time;
		this.gpsData = gpsData;
	}
	
	public Locator (){
		
	}
	
	public Locator (String imei, GpsData gpsData){
		this.imei = imei;
		this.gpsData = gpsData;
	}
}
