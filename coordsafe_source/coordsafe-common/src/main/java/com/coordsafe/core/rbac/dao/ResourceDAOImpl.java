package com.coordsafe.core.rbac.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coordsafe.core.rbac.entity.Permission;
import com.coordsafe.core.rbac.entity.Resource;
import com.coordsafe.core.rbac.entity.Role;
import com.coordsafe.core.rbac.dao.ResourceDAO;
import com.coordsafe.core.rbac.dao.ResourceDAOImpl;

@Repository("resourceDAO")
public class ResourceDAOImpl implements ResourceDAO {

	private static final Log log = LogFactory.getLog(ResourceDAOImpl.class);

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(Resource resource) {
		log.debug("saving Resource instance");
		sessionFactory.getCurrentSession().save(resource);
	}

	@Override
	public void update(Resource resource) {
		log.debug("Updating Resource instance");
		sessionFactory.getCurrentSession().merge(resource);
	}

	@Override
	public void delete(Resource resource) {
		log.debug("deleting Resource instance");
		clearForeignKeys(resource);
		sessionFactory.getCurrentSession().delete(resource);
	}
	
	private void clearForeignKeys(Resource resource) {
		for (Permission permission : resource.getPermissions()) {
			for (Role role : permission.getRoles()) {
				role.getPermissions().remove(permission);
			}
		}
	}

	@Override
	public void delete(String resourceName) {
		log.debug("deleting Resource : " + resourceName);
		sessionFactory.getCurrentSession()
				.createQuery("delete Resource as r where r.name=?")
				.setString(0, resourceName).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Resource> findByType(String type) {
		log.debug("finding Resource instance by type");
		return sessionFactory.getCurrentSession()
				.createQuery("from Resource as r where r.type=?")
				.setString(0, type).list();
	}

	@Override
	public Resource findByName(String name) {
		log.debug("finding Resource instance by name");
		return (Resource) sessionFactory.getCurrentSession()
				.createQuery("from Resource as r where r.name=?")
				.setString(0, name).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Resource> findByExample(Resource resource) {
		log.debug("finding Resource instance by example");
		return sessionFactory.getCurrentSession()
				.createCriteria(Resource.class).add(Example.create(resource))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Resource> findAll() {
		log.debug("finding all Resource instance");
		return sessionFactory.getCurrentSession()
				.createQuery("from Resource as r order by r.name").list();
	}

}
