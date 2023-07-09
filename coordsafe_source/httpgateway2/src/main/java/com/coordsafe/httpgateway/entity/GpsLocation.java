/**
 * 
 */
package com.coordsafe.httpgateway.entity;

public class GpsLocation {
    public double latitude = 0.0;
    public double longitude = 0.0;

	public GpsLocation() {
		
	}

	public GpsLocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
}

