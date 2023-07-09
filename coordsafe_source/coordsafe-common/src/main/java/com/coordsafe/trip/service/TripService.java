/**
 * TripService.java
 * Mar 17, 2013
 * Yang Wei
 */
package com.coordsafe.trip.service;

import java.util.Date;
import java.util.List;

import com.coordsafe.trip.entity.Trip;
import com.coordsafe.trip.entity.TripDetail;

/**
 * @author Yang Wei
 *
 */
public interface TripService {
	public Trip findById(long tripId);
	public List<Trip> findByTime(long locatorId, Date start, Date end);
	public Trip findCurrentOrLastTrip(long locatorId);
	public void create (Trip trip);
	public void update (Trip trip);
	public void delete (Trip trip);
	
	public List<Trip> findTripBtwTime(long locatorId, String startTime, String endTime);
	public List<Trip> findDailyTripsByLocator (long locatorId, String date);
	public List<TripDetail> findTripDetailBtwTimeFleetLink(long locatorId, String startTime, String endTime);
	public List<TripDetail> findTripDetailBtwTime(long locatorId, String startTime, String endTime);
	
	public String findGroupTripByTime(long groupId, String startTime, String endTime);
	public double getMileageByTime(long parseLong, String startTime, String endTime);
	public double getMovingTimeByTime(long parseLong, String startTime, String endTime);
	public List<Trip> findUnprocessed();
	public List<Trip> findValid();
	
	public TripDetail findCurOrLastTripDetail(long locatorId);
	public TripDetail findTripDetailById(long tripId);
}
