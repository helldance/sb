/**
 * EventService.java
 * May 30, 2013
 * Yang Wei
 */
package com.coordsafe.event.service;

import java.util.Date;
import java.util.List;

import com.coordsafe.event.entity.GenericEvent;
import com.coordsafe.event.entity.VehicleEvent;

/**
 * @author Yang Wei
 *
 */
public interface EventService {
	public void create(GenericEvent event);
	public void update(GenericEvent event);
	public void delete(GenericEvent event);
	
	public GenericEvent findLatestEventByLocatorId(long id);
	public List<GenericEvent> findInvalidEventWithNullPosition();
	
	public List<GenericEvent> findEventByLocatorTime(long locatorId, Date start, Date end);
	
	public List<GenericEvent> findEventbyCount(long companyId, int count);
	public List<GenericEvent> findEventbyTime(long companyId, Date start, Date end);
	public List<VehicleEvent> findVehicleEventbyTime(long companyId, Date start, Date end);
	
	public List<GenericEvent> findEventbyTrip(long tripId);
}
