package com.coordsafe.circle.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coordsafe.circle.entity.Circle;


@Repository("circleDAO")
public class CircleDAOImpl implements CircleDAO{
	
	private static final Log log = LogFactory.getLog(CircleDAOImpl.class);

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	


	@Override
	public void add(Circle circle) {
		log.info("In the add circle....");
		sessionFactory.getCurrentSession().save(circle);
		
	}

	@Override
	public void delete(Circle circle) {
		log.info("In the delete circle....");
		sessionFactory.getCurrentSession().delete(circle);
		
	}

	@Override
	public void update(Circle circle) {
		log.info("In the update circle....");
		sessionFactory.getCurrentSession().update(circle);
		
	}

	@Override
	public Circle find(Long circleID) {
		log.info("In the find circle....");
		String queryString = "from Circle c where c.id=? ";
		return (Circle)sessionFactory.getCurrentSession().createQuery(queryString).setLong(0,circleID).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Circle> findAllCircle() {
		log.info("In the findAllCircle ...");
		String queryString = "from Circle c";
		return (List<Circle>)sessionFactory.getCurrentSession().createQuery(queryString).list();		
		

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Circle> findCircleByGuardian(Long guardianID) {
		log.info("In the findCircleByGuardian ...");
		String queryString = "from Circle c where c.admin.id=?";
		return (List<Circle>)sessionFactory.getCurrentSession().createQuery(queryString).setLong(0,guardianID).list();
		
	}


}
