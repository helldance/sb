/**
 * EventServiceImpl.java
 * May 30, 2013
 * Yang Wei
 */
package com.coordsafe.event.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.event.dao.EventDAO;
import com.coordsafe.event.entity.GenericEvent;
import com.coordsafe.event.entity.VehicleEvent;

/**
 * @author Yang Wei
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EventServiceImpl implements EventService{

	@Autowired
	private EventDAO eventDao;
	
	/**
	 * @param eventDao the eventDao to set
	 */
	public void setEventDao(EventDAO eventDao) {
		this.eventDao = eventDao;
	}

	@Override
	public void create(GenericEvent event) {
		eventDao.create(event);
	}

	@Override
	public void update(GenericEvent event) {
		eventDao.update(event);
	}

	@Override
	public void delete(GenericEvent event) {
		eventDao.delete(event);
	}

	@Override
	public GenericEvent findLatestEventByLocatorId(long id) {
		return eventDao.findLatestEventByLocatorId(id);
	}

	@Override
	public List<GenericEvent> findInvalidEventWithNullPosition() {
		return eventDao.findInvalidEventWithNullPosition();
	}

	@Override
	public List<GenericEvent> findEventbyCount(long companyId, int count) {
		// TODO Auto-generated method stub
		return eventDao.findEventbyCount(companyId, count);
	}

	@Override
	public List<GenericEvent> findEventbyTime(long companyId, Date start,
			Date end) {
		// TODO Auto-generated method stub
		return eventDao.findEventbyTime(companyId, start, end);
	}

	@Override
	public List<GenericEvent> findEventByLocatorTime(long locatorId,
			Date start, Date end) {
		// TODO Auto-generated method stub
		return eventDao.findEventByLocatorTime(locatorId, start, end);
	}

	@Override
	public List<GenericEvent> findEventbyTrip(long tripId) {
		// TODO Auto-generated method stub
		return eventDao.findEventbyTrip(tripId);
	}

	@Override
	public List<VehicleEvent> findVehicleEventbyTime(long companyId,
			Date start, Date end) {
		// TODO Auto-generated method stub
		return eventDao.findVehicleEventByTime(companyId, start, end);
	}

}
