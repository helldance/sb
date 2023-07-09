package com.coordsafe.locator.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LocatorLocationHistory {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private long locator_id;
	private long wardId;
	private long vehicleId;
	private double lat;
	private double lng;
	private double altitude;
	private double speed;
	private double bearing;
	private double distance;
	private double accuracy;	
	private Date location_time;
	/**
	 * @return the altitude
	 */
	public double getAltitude() {
		return altitude;
	}
	/**
	 * @param altitude the altitude to set
	 */
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}
	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	/**
	 * @return the bearing
	 */
	public double getBearing() {
		return bearing;
	}
	/**
	 * @param bearing the bearing to set
	 */
	public void setBearing(double bearing) {
		this.bearing = bearing;
	}
	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}
	/**
	 * @return the accuracy
	 */
	public double getAccuracy() {
		return accuracy;
	}
	/**
	 * @param accuracy the accuracy to set
	 */
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	/**
	 * 
	 */
	public LocatorLocationHistory() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param locator_id
	 * @param lat
	 * @param lng
	 * @param location_time
	 */
	public LocatorLocationHistory(long locator_id, double lat, double lng,
			Date location_time) {
		super();
		this.locator_id = locator_id;
		this.lat = lat;
		this.lng = lng;
		this.location_time = location_time;
	}
	/**
	 * @param id
	 * @param locator_id
	 * @param lat
	 * @param lng
	 * @param altitude
	 * @param speed
	 * @param bearing
	 * @param distance
	 * @param accuracy
	 * @param location_time
	 */
	public LocatorLocationHistory(long id, long locator_id, double lat,
			double lng, double altitude, double speed, double bearing,
			double distance, double accuracy, Date location_time) {
		super();
		this.id = id;
		this.locator_id = locator_id;
		this.lat = lat;
		this.lng = lng;
		this.altitude = altitude;
		this.speed = speed;
		this.bearing = bearing;
		this.distance = distance;
		this.accuracy = accuracy;
		this.location_time = location_time;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the locator_id
	 */
	public long getLocator_id() {
		return locator_id;
	}
	/**
	 * @param locator_id the locator_id to set
	 */
	public void setLocator_id(long locator_id) {
		this.locator_id = locator_id;
	}
	/**
	 * @return the wardId
	 */
	public long getWardId() {
		return wardId;
	}
	/**
	 * @param wardId the wardId to set
	 */
	public void setWardId(long wardId) {
		this.wardId = wardId;
	}
	/**
	 * @return the vehicleId
	 */
	public long getVehicleId() {
		return vehicleId;
	}
	/**
	 * @param vehicleId the vehicleId to set
	 */
	public void setVehicleId(long vehicleId) {
		this.vehicleId = vehicleId;
	}
	/**
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}
	/**
	 * @param lat the lat to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}
	/**
	 * @return the lng
	 */
	public double getLng() {
		return lng;
	}
	/**
	 * @param lng the lng to set
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}
	/**
	 * @return the location_time
	 */
	public Date getLocation_time() {
		return location_time;
	}
	/**
	 * @param location_time the location_time to set
	 */
	public void setLocation_time(Date location_time) {
		this.location_time = location_time;
	}
	
	
	
}
