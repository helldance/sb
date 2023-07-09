package com.coordsafe.wardevent.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coordsafe.ward.entity.Ward;
import com.coordsafe.wardevent.entity.WardEvent;

@Repository("wardEventDao")
public class WardEventDAOImpl implements WardEventDAO{
	
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void create(WardEvent wardEvent) {
		this.sessionFactory.getCurrentSession().save(wardEvent);
	}

	@Override
	public void update(WardEvent wardEvent) {
		this.sessionFactory.getCurrentSession().update(wardEvent);
		
	}

	@Override
	public void delete(WardEvent wardEvent) {
		this.sessionFactory.getCurrentSession().delete(wardEvent);		
	}

	@Override
	public List<WardEvent> findAll() {
		return null;
	}

	@Override
	public List<WardEvent> findByWardId(Long wardId) {
		@SuppressWarnings("unchecked")
		List<WardEvent> wardEvents = (List<WardEvent>) sessionFactory.getCurrentSession()
				.createQuery("from WardEvent as w where w.ward.id=? and w.isNotified=false").setLong(0, wardId.longValue()).list();
				
		return wardEvents;
	}

	@Override
	public WardEvent findByID(Long wardEventID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WardEvent> findByTime(long wardId, Date _start, Date _end) {
		return (List<WardEvent>) sessionFactory.getCurrentSession().createQuery("from WardEvent we where we.notificationTime >= :start and " +
				"we.notificationTime <= :end and we.ward.id = :wardId order by we.notificationTime desc").setDate("start", _start).setDate("end", _end).setLong("wardId", wardId).list();
	}

}
