package com.coordsafe.locator.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class DeviceStatus implements Serializable {
	private static final long serialVersionUID = 1L;
	private String imei="";
	private Boolean isGsmOn=false;
	private Boolean isGpsOn=false;
	private Integer networkAvailability=0; // 0 : non-availble; 1: gsm; 2: wifi; 3:								// both
	private String ip="";
	private Integer batteryLeft=0;

	/**
	 * @param isGsmOn
	 * @param isGpsOn
	 * @param batteryLeft
	 */
	public DeviceStatus(String imei, Boolean isGsmOn, Boolean isGpsOn,
			int networkAvailability, String ip, int batteryLeft) {
		super();
		this.imei = imei;
		this.isGsmOn = isGsmOn;
		this.isGpsOn = isGpsOn;
		this.networkAvailability = networkAvailability;
		this.ip = ip;
		this.batteryLeft = batteryLeft;
	}

	public DeviceStatus() {
		this.imei="";
		this.isGsmOn=false;
		this.isGpsOn=false;
		this.networkAvailability=0; // 0 : non-availble; 1: gsm; 2: wifi; 3:								// both
		this.ip="";
		this.batteryLeft=0;

	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Boolean getIsGsmOn() {
		return isGsmOn;
	}

	public void setIsGsmOn(Boolean isGsmOn) {
		this.isGsmOn = isGsmOn;
	}

	public Boolean getIsGpsOn() {
		return isGpsOn;
	}

	public void setIsGpsOn(Boolean isGpsOn) {
		this.isGpsOn = isGpsOn;
	}

	public Integer getNetworkAvailability() {
		return networkAvailability;
	}

	public void setNetworkAvailability(Integer networkAvailability) {
		this.networkAvailability = networkAvailability;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getBatteryLeft() {
		return batteryLeft;
	}

	public void setBatteryLeft(Integer batteryLeft) {
		this.batteryLeft = batteryLeft;
	}

}
