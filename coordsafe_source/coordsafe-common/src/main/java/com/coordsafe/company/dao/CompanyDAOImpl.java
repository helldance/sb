package com.coordsafe.company.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coordsafe.company.entity.Company;


@Repository("companyDAO")
public class CompanyDAOImpl implements CompanyDAO {
	private static final Log log = LogFactory.getLog(CompanyDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public void save(Company company) {
		log.debug("saving Company instance");
		sessionFactory.getCurrentSession().saveOrUpdate(company);
	}

	@Override
	public void delete(Company company) {
		log.debug("deleting Company instance");
		//clearForeignKeys(Company);
		sessionFactory.getCurrentSession().delete(company);
	}
	

	@Override
	public Company findByName(String name) {
		log.debug("finding Company instance by name");
		return (Company) sessionFactory.getCurrentSession()
				.createQuery("from Company as o where o.name=?")
				.setString(0, name).uniqueResult();
	}
	
	@Override
	public Company findById(Long id) {
		log.debug("finding Company instance by id");
		return (Company) sessionFactory.getCurrentSession()
				.createQuery("from Company as o where o.id=?")
				.setLong(0, id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> findAll() {
		return sessionFactory.getCurrentSession()
				.createQuery("from Company as o order by o.name").list();
	}

	@Override
	public void update(Company company) {
		sessionFactory.getCurrentSession().merge(company);
	}

}
