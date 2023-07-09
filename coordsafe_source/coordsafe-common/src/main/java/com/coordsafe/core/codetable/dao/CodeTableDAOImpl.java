package com.coordsafe.core.codetable.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coordsafe.core.codetable.entity.CodeTable;
import com.coordsafe.core.rbac.dao.OrganizationDAOImpl;
import com.coordsafe.core.codetable.dao.CodeTableDAO;

@Repository("codeTableDAO")
public class CodeTableDAOImpl implements CodeTableDAO {

	private static final Log log = LogFactory.getLog(OrganizationDAOImpl.class);

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(CodeTable codeTable) {
		log.debug("saving CodeTable instance");
		sessionFactory.getCurrentSession().save(codeTable);
	}

	@Override
	public void delete(CodeTable codeTable) {
		log.debug("deleting CodeTable instance");
		codeTable.setDeleteIndicator(true);
		update(codeTable);
	}

	@Override
	public void update(CodeTable codeTable) {
		log.debug("updating CodeTable instance");
		sessionFactory.getCurrentSession().merge(codeTable);
	}

	@Override
	public int getCodeTypeCount(String type) {
		return ((Integer) sessionFactory
				.getCurrentSession()
				.createQuery(
						"select count(*) from CodeTable where type=? and deleteIndicator=false")
				.setString(0, type).iterate().next()).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CodeTable> findAll() {
		return sessionFactory.getCurrentSession()
				.createQuery("from CodeTable where deleteIndicator=false")
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CodeTable> findByType(String type) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"from CodeTable where type=? and deleteIndicator=false")
				.setString(0, type).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CodeTable> findByType(String type, Date startDate, Date endDate) {
		return sessionFactory.getCurrentSession()
				.createCriteria(CodeTable.class)
				.add(Restrictions.eq("type", type))
				.add(Restrictions.le("startDate", startDate))
				.add(Restrictions.ge("endDate", endDate)).list();
	}

	@Override
	public CodeTable findByTypeCode(String type, String code) {
		return (CodeTable) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from CodeTable where type=? and code=? and deleteIndicator=false")
				.setString(0, type).setString(1, code).uniqueResult();
	}

	@Override
	public CodeTable findById(Long id) {
		return (CodeTable) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from CodeTable where id=? and deleteIndicator=false")
				.setLong(0, id).uniqueResult();
	}
}
