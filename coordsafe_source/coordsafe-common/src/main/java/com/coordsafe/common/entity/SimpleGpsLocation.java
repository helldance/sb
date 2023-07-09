/**
 * SimpleGpsLocation.java
 * May 5, 2013
 * Yang Wei
 */
package com.coordsafe.common.entity;

import javax.persistence.Embeddable;

/**
 * @author Yang Wei
 *
 */
@Embeddable
public class SimpleGpsLocation {
	private Double latitude = 0.0;
    private Double longitude = 0.0;
	/**
	 * @param latitude
	 * @param longitude
	 */
	public SimpleGpsLocation(Double latitude, Double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}
	/**
	 * 
	 */
	public SimpleGpsLocation() {
		super();
	}
    
    
}
