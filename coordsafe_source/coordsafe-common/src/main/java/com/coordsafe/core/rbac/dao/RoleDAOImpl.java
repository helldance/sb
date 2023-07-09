package com.coordsafe.core.rbac.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coordsafe.core.rbac.entity.Role;
import com.coordsafe.core.rbac.entity.RoleList;
import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.dao.RoleDAO;
import com.coordsafe.core.rbac.dao.RoleDAOImpl;

@Repository("roleDAO")
public class RoleDAOImpl implements RoleDAO {

	private static final Log log = LogFactory.getLog(RoleDAOImpl.class);

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(Role role) {
		log.debug("saving Role instance");
		sessionFactory.getCurrentSession().saveOrUpdate(role);
	}

	@Override
	public void delete(Role role) {
		log.debug("deleting Role instance");
		clearUsers(role); // See method comments.
		sessionFactory.getCurrentSession().delete(role);
	}
	
	/*
	 * Role is the inverse end of the many-to-many relationship with User.
	 * Hence need to clear out all foreign keys before deleting the role
	 * itself.
	 */
	private void clearUsers(Role role) {
		// Get the users currently related to this role.
		
		for (User user : role.getUsers()) {
			user.getRoles().remove(role);
//			sessionFactory.getCurrentSession().update(user);
		}
	}

	@Override
	public Role findById(Long id) {
		log.debug("finding Role instance with id: " + id);
		return (Role) sessionFactory.getCurrentSession().get(Role.class, id);
	}

	@Override
	public Role findByRoleName(String roleName) {
		log.debug("finding Role instance with Role Name: " + roleName);
		return (Role) sessionFactory.getCurrentSession()
				.createQuery("from Role as r where r.name=?")
				.setString(0, roleName).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public RoleList findByExample(Role role) {
		log.debug("finding Role instance by example");
		return new RoleList(sessionFactory.getCurrentSession().createCriteria(Role.class)
				.add(Example.create(role)).list());
	}

	@SuppressWarnings("unchecked")
	private List<Role> findByProperty(String propertyName, Object value) {
		log.debug("finding TblRole instance with property: " + propertyName
				+ ", value: " + value);
		return sessionFactory.getCurrentSession()
				.createQuery("from Role as r where r.?=?")
				.setString(0, propertyName).setEntity(1, value).list();
	}

	@Override
	public RoleList findByName(Object name) {
		log.debug("finding Role instance by name");
		return new RoleList(findByProperty("name", name));
	}

	@Override
	public Role load(Long id) {
		log.debug("finding Role instance by id");
		return (Role) sessionFactory.getCurrentSession().get(Role.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public RoleList findAllRoles() {
		return new RoleList(sessionFactory.getCurrentSession().createQuery("from Role")
				.list());
	}

	@Override
	public Role merge(Role role) {
		log.debug("merging TblRole instance");
		return (Role) sessionFactory.getCurrentSession().merge(role);
	}

}
