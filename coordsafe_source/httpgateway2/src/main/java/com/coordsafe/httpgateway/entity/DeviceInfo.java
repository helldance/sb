package com.coordsafe.httpgateway.entity;

public class DeviceInfo {
	public String imei;
	public Boolean isGsmOn;
	public Boolean isGpsOn;
	public int networkAvailability; // 0 : non-availble; 1: gsm; 2: wifi; 3: both
	public String ip;
	public int batteryLeft;
	
	/**
	 * @param isGsmOn
	 * @param isGpsOn
	 * @param batteryLeft
	 */
	public DeviceInfo(String imei, Boolean isGsmOn, Boolean isGpsOn, int networkAvailability, String ip, int batteryLeft) {
		super();
		this.imei = imei;
		this.isGsmOn = isGsmOn;
		this.isGpsOn = isGpsOn;
		this.networkAvailability = networkAvailability;
		this.ip = ip;
		this.batteryLeft = batteryLeft;
	}
	
	public DeviceInfo (){
		
	}
}
