package com.coordsafe.core.rbac.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coordsafe.core.rbac.entity.Group;
import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.entity.UserList;
import com.coordsafe.core.rbac.dao.UserDAO;
import com.coordsafe.core.rbac.dao.UserDAOImpl;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {

	private static final Log log = LogFactory.getLog(UserDAOImpl.class);

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(User user) {
		log.debug("saving User instance");
		sessionFactory.getCurrentSession().saveOrUpdate(user);
	}

	@Override
	public void update(User user) {
		log.debug("updating User instance");
		sessionFactory.getCurrentSession().merge(user);
	}

	@Override
	public void delete(User user) {
		log.debug("deleting User instance");
		clearGroups(user); // see method comments
		sessionFactory.getCurrentSession().delete(user);
	}
	
	/*
	 * User is the inverse end of the many-to-many relationship with Group.
	 * Hence need to clear out all foreign keys before deleting the user
	 * itself.
	 */
	private void clearGroups(User user) {
		// Get the users currently related to this role.
		
		for (Group group : user.getGroups()) {
			group.getUsers().remove(user);
//			sessionFactory.getCurrentSession().update(group);
		}
	}

	@Override
	public User findByUsername(String username) {
		log.debug("finding User instance with username: " + username);
		return (User) sessionFactory.getCurrentSession()
				.createQuery("from User as u where u.username=?")
				.setString(0, username).uniqueResult();
	}
	
	@Override
	public User findById(Long id) {
		log.debug("finding User instance with id: " + id);
		return (User) sessionFactory.getCurrentSession()
				.createQuery("from User as u where u.id=?")
				.setLong(0, id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserList findByExample(User user) {
		log.debug("finding User instance by example " + user);
		return new UserList(sessionFactory.getCurrentSession().createCriteria(User.class)
				.add(Example.create(user)).list());
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserList findByProperty(String propertyName, Object value) {
		log.debug("finding User instance with property: " + propertyName
				+ ", value : " + value);
		return new UserList(sessionFactory.getCurrentSession()
				.createQuery("from User as u where u.?=?")
				.setString(0, propertyName).setEntity(1, value).list());
	}

	@Override
	public Boolean updateLastLogin(String username) {
		User user = findByUsername(username);

		if (user != null) {
			user.setLastLoginDate(new Date());
			user.setLastModDate(new Date());
			this.update(user);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean updatePassword(String username, String password, String salt) {
		User user = findByUsername(username);

		if (user != null) {
			user.setPassword(password);
			user.setSalt(salt);
			user.setLastModDate(new Date());
			this.update(user);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean updateRetry(String username, Long retry) {
		User user = findByUsername(username);

		if (user != null) {
			user.setRetryCount(retry);
			user.setLastModDate(new Date());
			this.update(user);
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsers(String username) {
		return sessionFactory.getCurrentSession()
				.createQuery("from User as u where u.username=?")
				.setString(0, username).list();
	}

	@Override
	public User getUser(String username) {
		return (User) sessionFactory.getCurrentSession()
				.createQuery("from User as u where u.username=?")
				.setString(0, username).uniqueResult();
	}

	@Override
	public Boolean lockUserAccount(String username) {
		User user = findByUsername(username);

		if (user != null) {
			user.setEnabled(false);
			user.setLastModDate(new Date());
			this.update(user);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean unlockUserAccount(String username) {
		User user = findByUsername(username);

		if (user != null) {
			user.setEnabled(true);
			user.setLastModDate(new Date());
			this.update(user);
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserList findAll() {
		return new UserList(sessionFactory.getCurrentSession().createQuery("from User")
				.list());
	}

	@Override
	public User findByUsernameOrEmail(String nameOrEmail) {
		log.debug("finding user with username or email: " + nameOrEmail);
		
		return (User) sessionFactory.getCurrentSession().createQuery("from User as u where u.username=:username or u.email=:email")
				.setString("username", nameOrEmail).setString("email", nameOrEmail).uniqueResult();
	}
}
