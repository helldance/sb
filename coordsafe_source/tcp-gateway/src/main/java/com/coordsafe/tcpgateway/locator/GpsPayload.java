package com.coordsafe.tcpgateway.locator;

public class GpsPayload {
	public static final int FORMAT_DEGREES = 0;
	public static final int FORMAT_MINUTES = 1;
	public static final int FORMAT_SECONDS = 2;
	
    //private String mProvider;
    //private long time = 0;
    //private double latitude = 0.0;
    //private double longitude = 0.0;
	private boolean hasGpsFix = false;
	private byte noSatellite = 0;
    private boolean hasAltitude = false;
    private double altitude = 0.0f;
    private boolean hasSpeed = false;
    private float speed = 0.0f;
    private boolean hasBearing = false;
    private float bearing = 0.0f;
    private boolean hasAccuracy = false;
    private float accuracy = 0.0f;
    private float mDistance = 0.0f;
    private float mInitialBearing = 0.0f;
    //private float[] mResults = new float[2];
	
	/**
	 * @return the hasAltitude
	 */
	public boolean isHasAltitude() {
		return hasAltitude;
	}
	/**
	 * @return the hasGpsFix
	 */
	public boolean isHasGpsFix() {
		return hasGpsFix;
	}
	/**
	 * @param hasGpsFix the hasGpsFix to set
	 */
	public void setHasGpsFix(boolean hasGpsFix) {
		this.hasGpsFix = hasGpsFix;
	}
	/**
	 * @return the noSatellite
	 */
	public byte getNoSatellite() {
		return noSatellite;
	}
	/**
	 * @param noSatellite the noSatellite to set
	 */
	public void setNoSatellite(byte noSatellite) {
		this.noSatellite = noSatellite;
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
	 * @return the mDistance
	 */
	public float getmDistance() {
		return mDistance;
	}
	/**
	 * @param mDistance the mDistance to set
	 */
	public void setmDistance(float mDistance) {
		this.mDistance = mDistance;
	}
	/**
	 * @return the mInitialBearing
	 */
	public float getmInitialBearing() {
		return mInitialBearing;
	}
	/**
	 * @param mInitialBearing the mInitialBearing to set
	 */
	public void setmInitialBearing(float mInitialBearing) {
		this.mInitialBearing = mInitialBearing;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GpsPayload [hasGpsFix=" + hasGpsFix + ", noSatellite="
				+ noSatellite + ", altitude="
				+ altitude + ", speed=" + speed
				+ ", bearing=" + bearing
				+ ", accuracy=" + accuracy
				+ ", mDistance=" + mDistance + ", mInitialBearing="
				+ mInitialBearing + "]";
	}	
}
