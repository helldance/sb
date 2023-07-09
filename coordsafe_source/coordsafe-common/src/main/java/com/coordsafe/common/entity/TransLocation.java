package com.coordsafe.common.entity;

import java.io.Serializable;

/**
 * @author Yang Wei
 * Object to be transmitted
 */
public class TransLocation implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public String deviceId;
	public String deviceIp;
	public double lat;
	public double lng;
	@Override
	public String toString() {
		return "TransLocation [deviceId=" + deviceId + ", deviceIp=" + deviceIp
				+ ", lat=" + lat + ", lng=" + lng + ", alt=" + alt + ", speed="
				+ speed + ", distance=" + distance + ", bearing=" + bearing
				+ ", accuracy=" + accuracy + ", gpsTime=" + gpsTime
				+ ", gpsFix=" + gpsFix + ", noSatellite=" + noSatellite
				+ ", gsmOn=" + gsmOn + ", battery=" + battery + ", network="
				+ network + ", locatorType=" + locatorType + "]";
	}
	public double alt;
	public double speed;
	public double distance;
	public double bearing;
	public double accuracy;
	public long gpsTime;
	public boolean gpsFix;
	public byte noSatellite;
	public boolean gsmOn;
	public byte battery;
	public byte network;
	///For the type of locator: 1. Vehicle(default) 2.Ward 
	private String locatorType = "vehicle";
	
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getAlt() {
		return alt;
	}
	public void setAlt(double alt) {
		this.alt = alt;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getBearing() {
		return bearing;
	}
	public void setBearing(double bearing) {
		this.bearing = bearing;
	}
	public double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	public long getGpsTime() {
		return gpsTime;
	}
	public void setGpsTime(long gpsTime) {
		this.gpsTime = gpsTime;
	}
	public boolean isGpsFix() {
		return gpsFix;
	}
	public void setGpsFix(boolean gpsFix) {
		this.gpsFix = gpsFix;
	}
	public byte getNoSatellite() {
		return noSatellite;
	}
	public void setNoSatellite(byte noSatellite) {
		this.noSatellite = noSatellite;
	}
	public boolean isGsmOn() {
		return gsmOn;
	}
	public void setGsmOn(boolean gsmOn) {
		this.gsmOn = gsmOn;
	}
	public byte getBattery() {
		return battery;
	}
	public void setBattery(byte battery) {
		this.battery = battery;
	}
	public byte getNetwork() {
		return network;
	}
	public void setNetwork(byte network) {
		this.network = network;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the deviceIp
	 */
	public String getDeviceIp() {
		return deviceIp;
	}
	/**
	 * @param deviceIp the deviceIp to set
	 */
	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}
	public String getLocatorType() {
		return locatorType;
	}
	public void setLocatorType(String locatorType) {
		this.locatorType = locatorType;
	}
}
