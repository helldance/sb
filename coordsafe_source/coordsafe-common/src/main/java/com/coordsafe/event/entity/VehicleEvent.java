/**
 * VehicleEvent.java
 * May 3, 2013
 * Yang Wei
 */
package com.coordsafe.event.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.coordsafe.common.entity.LatLng;
import com.coordsafe.company.entity.Company;
import com.coordsafe.trip.entity.Trip;

/**
 * @author Yang Wei
 *
 */
@Entity
@Table(name="tbl_vehicle_event")
@JsonIgnoreProperties({"company"})
public class VehicleEvent extends GenericEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne
	public Company company;
	
	@ManyToOne
	public Trip trip;

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public VehicleEvent (long locatorId, String type, String message, 
			LatLng location, Date eventTime, String bearName, Company company){
		this.locatorId = locatorId;
		this.location = location;
		this.eventTime = eventTime;
		this.message = message;
		this.company = company;
		this.bearerName = bearName;
		this.type = type;
	}

	/**
	 * @param company
	 * @param trip
	 */
	public VehicleEvent(long locatorId, String type, String message, 
			LatLng location, Date eventTime, String bearName, Company company, Trip trip) {
		this.locatorId = locatorId;
		this.location = location;
		this.eventTime = eventTime;
		this.message = message;
		this.company = company;
		this.bearerName = bearName;
		this.type = type;
		this.trip = trip;
	}

	/**
	 * @return the trip
	 */
	public Trip getTrip() {
		return trip;
	}

	/**
	 * @param trip the trip to set
	 */
	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public VehicleEvent() {
		// TODO Auto-generated constructor stub
	}
}
