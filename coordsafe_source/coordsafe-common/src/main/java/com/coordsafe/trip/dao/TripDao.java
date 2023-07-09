/**
 * TripDao.java
 * Mar 17, 2013
 * Yang Wei
 */
package com.coordsafe.trip.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.coordsafe.trip.entity.Trip;

public interface TripDao {
	public Trip findById(long id);
	public Trip findCurrentOrLastTrip(long locatorId);
	public List<Trip> findByTime(long locatorId, Date start, Date end);
	public List<Trip> findByIDTime(long locatorId, String start, String end);
	public void create (Trip trip);
	public void update (Trip trip);
	public void delete (Trip trip);
	
	public List<Trip> findUnprocessed();
	public List<Trip> findValid();
	
	public List<HashMap<String, List<Trip>>> findGroupTripByTime(long groupId, Date start, Date end);
}
