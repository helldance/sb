package com.coordsafe.locator.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class GpsLocation implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int FORMAT_DEGREES = 0;
	public static final int FORMAT_MINUTES = 1;
	public static final int FORMAT_SECONDS = 2;
	
    //private String mProvider;
    private Long time = 0L;
    private Double latitude = 0.0;
    private Double longitude = 0.0;
    private Boolean hasAltitude = false;
    private Double altitude = 0.0;
    private Boolean hasSpeed = false;
    private Float speed = 0.0f;
    private Boolean hasBearing = false;
    private Float bearing = 0.0f;
    private Boolean hasAccuracy = false;
    private Float accuracy = 0.0f;
    private Float distance = 0.0f;
    private Float initialBearing = 0.0f;

	public GpsLocation() {
		
	}

	public GpsLocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
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

	/**
	 * @return the hasAltitude
	 */
	public boolean isHasAltitude() {
		return hasAltitude;
	}

	/**
	 * @param hasAltitude the hasAltitude to set
	 */
	public void setHasAltitude(boolean hasAltitude) {
		this.hasAltitude = hasAltitude;
	}

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
	 * @return the hasSpeed
	 */
	public boolean isHasSpeed() {
		return hasSpeed;
	}

	/**
	 * @param hasSpeed the hasSpeed to set
	 */
	public void setHasSpeed(boolean hasSpeed) {
		this.hasSpeed = hasSpeed;
	}

	/**
	 * @return the speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	/**
	 * @return the hasBearing
	 */
	public boolean isHasBearing() {
		return hasBearing;
	}

	/**
	 * @param hasBearing the hasBearing to set
	 */
	public void setHasBearing(boolean hasBearing) {
		this.hasBearing = hasBearing;
	}

	/**
	 * @return the bearing
	 */
	public float getBearing() {
		return bearing;
	}

	/**
	 * @param bearing the bearing to set
	 */
	public void setBearing(float bearing) {
		this.bearing = bearing;
	}

	/**
	 * @return the hasAccuracy
	 */
	public boolean isHasAccuracy() {
		return hasAccuracy;
	}

	/**
	 * @param hasAccuracy the hasAccuracy to set
	 */
	public void setHasAccuracy(boolean hasAccuracy) {
		this.hasAccuracy = hasAccuracy;
	}

	/**
	 * @return the accuracy
	 */
	public float getAccuracy() {
		return accuracy;
	}

	/**
	 * @param accuracy the accuracy to set
	 */
	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}

	/**
	 * @return the distance
	 */
	public float getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(float distance) {
		this.distance = distance;
	}

	/**
	 * @return the initialBearing
	 */
	public float getInitialBearing() {
		return initialBearing;
	}

	/**
	 * @param initialBearing the initialBearing to set
	 */
	public void setInitialBearing(float initialBearing) {
		this.initialBearing = initialBearing;
	}

	/**
	 * @return the results
	 */
/*	public float[] getResults() {
		return results;
	}

	*//**
	 * @param results the results to set
	 *//*
	public void setResults(float[] results) {
		this.results = results;
	}*/

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (time ^ (time >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof GpsLocation)) {
			return false;
		}
		GpsLocation other = (GpsLocation) obj;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude)) {
			return false;
		}
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude)) {
			return false;
		}
		if (time != other.time) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GpsLocation [time=" + time + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", altitude=" + altitude
				+ ", speed=" + speed + "]";
	}
	
}
