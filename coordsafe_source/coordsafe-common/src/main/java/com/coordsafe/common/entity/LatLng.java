/**
 * SimpleGpsLocation.java
 * May 5, 2013
 * Yang Wei
 */
package com.coordsafe.common.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author Yang Wei
 *
 */
@Embeddable
public class LatLng implements Serializable{
	private static final long serialVersionUID = 1L;
	public Double latitude;
    public Double longitude;
	/**
	 * @param latitude
	 * @param longitude
	 */
	public LatLng(Double latitude, Double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}
	/**
	 * 
	 */
	public LatLng() {
		super();
	}
    
    /**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	/*public boolean isValid (){
    	if (latitude == 0 && longitude == 0){
    		return false;
    	}
    	
    	return true;
    }*/
    
    //TODO
    public void trim(){
    	
    }
}
