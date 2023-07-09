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
import com.coordsafe.core.rbac.dao.PermissionDAO;
import com.coordsafe.core.rbac.dao.PermissionDAOImpl;

@Repository("permissionDAO")
public class PermissionDAOImpl implements PermissionDAO {
	private static final Log log = LogFactory.getLog(PermissionDAOImpl.class);

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(Permission permission) {
		log.debug("Saving Permissions");
		sessionFactory.getCurrentSession().save(permission);
	}

	@Override
	public void update(Permission permission) {
		log.debug("Updating Permissions");
		sessionFactory.getCurrentSession().merge(permission);
	}

	@Override
	public void delete(Permission permission) {
		log.debug("Deleting Permissions");
		sessionFactory.getCurrentSession().delete(permission);
	}

	@Override
	public Permission load(Long id) {
		log.debug("loading Permissions");
		return (Permission) sessionFactory.getCurrentSession().get(
				Permission.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Permission> findByExample(Permission permission) {
		log.debug("Finding Permissions by example ...");
		return sessionFactory.getCurrentSession()
				.createCriteria(Permission.class)
				.add(Example.create(permission)).createCriteria("resource")
				.add(Example.create(permission.getResource())).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Permission> findByResourceId(Resource resource) {
		log.debug("Finding Permissions by resource id ...");
		return sessionFactory.getCurrentSession()
				.createQuery("from Permission as p where p.resource=?")
				.setEntity(0, resource).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Permission> findByResourcePermission(Resource resource,
			String permission) {
		log.debug("Finding Permissions by resource permission ...");
		return sessionFactory.getCurrentSession()
				.createQuery("from Permission as p where p.resource=?")
				.setEntity(0, resource).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Permission> findAll() {
		log.debug("Finding all Permissions");
		return sessionFactory.getCurrentSession()
				.createQuery("from Permission").list();
	}
}
