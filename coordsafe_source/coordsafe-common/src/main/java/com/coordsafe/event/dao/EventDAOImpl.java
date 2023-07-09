/**
 * EventDAOImpl.java
 * May 3, 2013
 * Yang Wei
 */
package com.coordsafe.event.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.event.entity.GenericEvent;
import com.coordsafe.event.entity.VehicleEvent;

@Repository
public class EventDAOImpl implements EventDAO{
	private static final Log logger = LogFactory.getLog(EventDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void create(GenericEvent event) {
		logger.info("create event: " + event.locatorId);
		
		sessionFactory.getCurrentSession().save(event);		
	}

	@Override
	public void update(GenericEvent event) {
		logger.info("update event: " + event.locatorId);
		
		sessionFactory.getCurrentSession().saveOrUpdate(event);
	}

	@Override
	public void delete(GenericEvent event) {
		logger.info("delete event: " + event.locatorId);
		
		sessionFactory.getCurrentSession().delete(event);
	}

	@Override
	public GenericEvent findLatestEventByLocatorId(long id) {		
		logger.info("find latest event for locator: " + id);
		
		//TODO: need differentiate event tables
		List<GenericEvent> events = sessionFactory.getCurrentSession().createQuery("from GenericEvent evt where evt.locatorId = ? order by id desc")
				.setLong(0, id).list();
		
		if (events != null && events.size() > 0){
			return events.get(0);
		}
		
		return null;
	}

	@Override
	public List<GenericEvent> findInvalidEventWithNullPosition() {
		logger.info("find invalid event with null position");
		
		List<GenericEvent> events = sessionFactory.getCurrentSession().createQuery("from GenericEvent evt where evt.location.latitude = 0" +
				" and evt.location.longitude = 0").list();
		
		return events;
	}

	@Override
	public List<GenericEvent> findEventbyCount(long companyId, int count) {
		// TODO Auto-generated method stub
		return (List<GenericEvent>) sessionFactory.getCurrentSession().createQuery("from VehicleEvent evt where evt.company.id = :cid" +
				" order by evt.eventTime desc").setLong("cid", companyId).setMaxResults(count).list();
	}

	@Override
	public List<GenericEvent> findEventbyTime(long companyId, Date start, Date end) {
		// TODO Auto-generated method stub
		logger.info("find event by company: " + companyId + " " + start + " " + end);
		
		return (List<GenericEvent>) sessionFactory.getCurrentSession().createQuery("from VehicleEvent evt where evt.company.id = :cid" +
				" and evt.eventTime >= :sd and evt.eventTime <= :ed order by evt.eventTime desc").setLong("cid", companyId).setDate("sd", start).setDate("ed", end).list();
	}

	@Override
	public List<GenericEvent> findEventByLocatorTime(long locatorId,
			Date start, Date end) {
		// TODO Auto-generated method stub
		return (List<GenericEvent>) sessionFactory.getCurrentSession().createQuery("from GenericEvent evt where evt.locatorId = :lid" +
				" and evt.eventTime >= :sd and evt.eventTime <= :ed order by evt.eventTime desc").setLong("lid", locatorId).setDate("sd", start).setDate("ed", end).list();

	}

	@Override
	public List<GenericEvent> findEventbyTrip(long tripId) {
		// TODO Auto-generated method stub
		return (List<GenericEvent>) sessionFactory.getCurrentSession().createQuery("from VehicleEvent evt where evt.trip.id = :tid").setLong("tid", tripId).list();
	}

	@Override
	public List<VehicleEvent> findVehicleEventByTime(long companyId,
			Date start, Date end) {
		logger.info("find vehicle-event by company: " + companyId + " " + start + " " + end);
		
		// shamir's hack
		if (companyId == 2){
			return  ( List<VehicleEvent> )sessionFactory.getCurrentSession()
					.createQuery("from VehicleEvent evt where evt.company.id = :cid or evt.company.id = :anotherId " +
							" and evt.eventTime >= :sd and evt.eventTime <= :ed order by evt.eventTime desc").setLong("cid", companyId).setLong("anotherId", 3).setDate("sd", start).setDate("ed", end).list();
		}
		else {
			return (List<VehicleEvent>) sessionFactory.getCurrentSession().createQuery("from VehicleEvent evt where evt.company.id = :cid" +
				" and evt.eventTime >= :sd and evt.eventTime <= :ed order by evt.eventTime desc").setLong("cid", companyId).setDate("sd", start).setDate("ed", end).list();
		}
	}
	
}
