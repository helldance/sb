package com.coordsafe.locator.entity;

import java.io.Serializable;
import java.util.Date;

public class GpsDevice extends Device {
	private GpsLocation gpsLocation;
	
	public GpsDevice() {
		super();
	}
	
	public GpsDevice(Long id, String model, String label, String madeBy, Date madeDate) {
		super(id, model, label, madeBy,madeDate);
	}

	/**
	 * @return the gpsLocation
	 */
	public GpsLocation getGpsLocation() {
		return gpsLocation;
	}

	/**
	 * @param gpsLocation the gpsLocation to set
	 */
	public void setGpsLocation(GpsLocation gpsLocation) {
		this.gpsLocation = gpsLocation;
	}
}
