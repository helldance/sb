/**
 * EventDAO.java
 * May 3, 2013
 * Yang Wei
 */
package com.coordsafe.event.dao;

import java.util.Date;
import java.util.List;

import com.coordsafe.event.entity.GenericEvent;
import com.coordsafe.event.entity.VehicleEvent;

/**
 * @author Yang Wei
 *
 */
public interface EventDAO {
	public void create(GenericEvent event);
	public void update(GenericEvent event);
	public void delete(GenericEvent event);
	
	public GenericEvent findLatestEventByLocatorId(long id);
	public List<GenericEvent> findInvalidEventWithNullPosition();
	public List<GenericEvent> findEventbyCount(long companyId, int count);
	public List<GenericEvent> findEventbyTime(long companyId, Date start, Date end);
	public List<GenericEvent> findEventByLocatorTime(long locatorId, Date start, Date end);
	public List<GenericEvent> findEventbyTrip(long tripId);
	public List<VehicleEvent> findVehicleEventByTime(long companyId, Date start, Date end);
}
