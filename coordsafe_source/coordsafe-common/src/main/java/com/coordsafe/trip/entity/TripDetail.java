/**
 * TripDetail.java
 * May 29, 2013
 * Yang Wei
 */
package com.coordsafe.trip.entity;

import java.util.List;

import com.coordsafe.locator.entity.LocatorLocationHistory;

/**
 * @author Yang Wei
 *
 */
public class TripDetail {
	public Trip trip;
	public String tripType;// finished,unfinished
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

	/**
	 * @return the tripType
	 */
	public String getTripType() {
		return tripType;
	}

	/**
	 * @param tripType the tripType to set
	 */
	public void setTripType(String tripType) {
		this.tripType = tripType;
	}

	/**
	 * @return the history
	 */
	public List<LocatorLocationHistory> getHistory() {
		return history;
	}

	/**
	 * @param history the history to set
	 */
	public void setHistory(List<LocatorLocationHistory> history) {
		this.history = history;
	}

	public List<LocatorLocationHistory> history;
	
	/**
	 * @param tripId
	 * @param history
	 */
	public TripDetail(Trip trip, List<LocatorLocationHistory> history) {
		super();
		//this.tripId = tripId;
		this.trip = trip;
		this.history = history;
	}

	public TripDetail() {
		// TODO Auto-generated constructor stub
	}
}
