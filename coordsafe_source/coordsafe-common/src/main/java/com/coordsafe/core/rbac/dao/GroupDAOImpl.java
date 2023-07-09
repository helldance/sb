package com.coordsafe.core.rbac.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coordsafe.core.rbac.entity.Group;
import com.coordsafe.core.rbac.dao.GroupDAO;
import com.coordsafe.core.rbac.dao.GroupDAOImpl;

@Repository("groupDAO")
public class GroupDAOImpl implements GroupDAO {

	private static final Log log = LogFactory.getLog(GroupDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void save(Group group) {
		log.debug("saving Group instance");
		sessionFactory.getCurrentSession().saveOrUpdate(group);
	}

	@Override
	public void update(Group group) {
		log.debug("merging Group instance");
		sessionFactory.getCurrentSession().merge(group);
	}

	@Override
	public void delete(String name) {
		log.debug("deleteing Group instance");
		sessionFactory.getCurrentSession().delete(findByName(name));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Group> findAll() {
		return sessionFactory.getCurrentSession().createQuery("from Group").list();
	}

	@Override
	public Group findByName(String name) {
		return (Group) sessionFactory.getCurrentSession().createQuery("from Group as g where g.name=?").setString(0, name).uniqueResult();
	}
}
