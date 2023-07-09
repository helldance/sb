/**
 * 
 */

package com.coordsafe.httpgateway.entity;

public class GpsData {
	public GpsData (){
		
	}
	/**
	 * @param latitude2
	 * @param longitude2
	 */
	public GpsData(double latitude2, double longitude2) {
		// TODO Auto-generated constructor stub
		this.latitude = latitude2;
		this.longitude = longitude2;
	}
	public static final int FORMAT_DEGREES = 0;
	public static final int FORMAT_MINUTES = 1;
	public static final int FORMAT_SECONDS = 2;
	
    //private String mProvider;
    public long time = 0;
    public double latitude = 0.0;
    public double longitude = 0.0;
    public boolean hasAltitude = false;
    public double altitude = 0.0f;
    public boolean hasSpeed = false;
    public float speed = 0.0f;
    public boolean hasBearing = false;
    public float bearing = 0.0f;
    public boolean hasAccuracy = false;
    public float accuracy = 0.0f;
    public float mDistance = 0.0f;
    public float mInitialBearing = 0.0f;
    public float[] mResults = new float[2];
}
