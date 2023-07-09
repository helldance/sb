package com.coordsafe.ward.dao;

import java.util.List;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coordsafe.ward.entity.Ward;

@Repository("wardDao")
public class WardDAOImpl implements WardDAO {

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void create(Ward ward) {
		sessionFactory.getCurrentSession().save(ward);
	}

	@Override
	public void update(Ward ward) {
		sessionFactory.getCurrentSession().update(ward);
	}

	@Override
	public void delete(Ward ward) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(ward);
	}

	@Override
	public List<Ward> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ward> findByGuardianId(String guardianName) {
		List<Ward> wards = (List<Ward>) sessionFactory.getCurrentSession()
				.createQuery("from Ward as w where w.guardian.login=? ")
				.setString(0, guardianName).list();
		return wards;
	}

	@Override
	public List<Ward> findByExample(Ward ward) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ward findByName(String wardName) {
		// TODO Auto-generated method stub
		return (Ward) sessionFactory.getCurrentSession()
				.createQuery("from Ward as w where w.name=? ")
				.setString(0, wardName).uniqueResult();
	}

	@Override
	public Ward findByID(Long wardID) {
		return (Ward) sessionFactory.getCurrentSession()
				.createQuery("from Ward as w where w.id=? ")
				.setLong(0, wardID).uniqueResult();
	}

	@Override
	public Ward findByLocatorID(Long locatorid) {
		// TODO Auto-generated method stub
		return (Ward) sessionFactory.getCurrentSession()
				.createQuery("from Ward as w where w.locator.id=? ")
				.setLong(0, locatorid).uniqueResult();
	}

}
