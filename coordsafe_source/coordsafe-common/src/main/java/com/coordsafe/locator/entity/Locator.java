package com.coordsafe.locator.entity;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

import com.coordsafe.vehicle.entity.Vehicle;

//import com.coordsafe.ward.entity.Owner;

@Entity
@DynamicUpdate(value = true)

public class Locator extends Device implements java.io.Serializable {
	private String imeiCode;
	private String devicePassword;
	@Embedded
	private GpsLocation gpsLocation;
	//private String location;
	@Embedded
	private DeviceStatus deviceStatus;
	private Long assignedTo;
	
	protected String phoneNumber;
	/*@OneToOne
	private Vehicle vehicle;*/
	/**
	 * 
	 */
	private static final long serialVersionUID = 70911505620075128L;

	//private Owner owner;

	public Locator() {
		super();
	}

	public Locator(Long id, String model, String label,String status,
			String madeBy, Date madeDate) {
		super(id, model, label, madeBy, madeDate);
	}

	
	/**
	 * @param imeiCode
	 * @param assignedTo
	 */
	public Locator(String imeiCode, String label, Date madeDt, boolean valid) {
		super();
		
		this.imeiCode = imeiCode;
		this.label = label;
		this.madeDate = madeDt;
		this.spoiled = valid;
	}

	public GpsLocation getGpsLocation() {
		return gpsLocation;
	}

	public void setGpsLocation(GpsLocation gpsLocation) {
		this.gpsLocation = gpsLocation;
	}

	public String getImeiCode() {
		return imeiCode;
	}

	public void setImeiCode(String imeiCode) {
		this.imeiCode = imeiCode;
	}

	/*
	 * Check whether the current operation is adding a new user.
	 */
	@Transient
	public boolean isCreate() {
		return (this.imeiCode== null);
	}

	/*public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}*/

	/*public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}*/

	public DeviceStatus getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(DeviceStatus deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public Long getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Long assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getDevicePassword() {
		return devicePassword;
	}

	public void setDevicePassword(String devicePassword) {
		this.devicePassword = devicePassword;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
