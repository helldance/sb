package com.coordsafe.tcpgateway.locator;

public class GpsLocation {
    private double latitude = 0.0;
    private double longitude = 0.0;

	public GpsLocation() {
		
	}

	public GpsLocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public String toString (){
		return "latitude: " + latitude + " longitude: " + longitude;
	}
	
}
