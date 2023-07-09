package com.coordsafe.retailer.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coordsafe.retailer.entity.Simcard;

@Repository("simcardDao")
public class SimcardDAOImpl implements SimcardDAO{
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void create(Simcard simcard) {
		sessionFactory.getCurrentSession().save(simcard);
		
	}

	@Override
	public void update(Simcard simcard) {
		sessionFactory.getCurrentSession().update(simcard);
	}

	@Override
	public void delete(Simcard simcard) {
		sessionFactory.getCurrentSession().delete(simcard);
		
	}

	@Override
	public List<Simcard> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Simcard> findByGuardianId(String simretailer) {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from Simcard as s where s.simretailer =?").setString(0, simretailer).list();
	}

	@Override
	public List<Simcard> findByExample(Simcard simcard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Simcard findByName(String simcardName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Simcard findByID(String imei) {
		// TODO Auto-generated method stub
		return (Simcard) sessionFactory.getCurrentSession().createQuery("from Simcard as s where s.simimei =?").setString(0, imei).uniqueResult();
	}

	@Override
	public Simcard findByLocatorID(Long locatorid) {
		// TODO Auto-generated method stub
		return null;
	}

}
