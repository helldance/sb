/**
 * @author Yang Wei
 * @Date Oct 29, 2013
 */
package com.coordsafe.circle.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.coordsafe.circle.entity.Invite;

/**
 * @author Yang Wei
 *
 */
public class InviteDAOImpl implements InviteDAO{
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void create(Invite invite) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(invite);
	}

	@Override
	public void update(Invite invite) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(invite);
	}

	@Override
	public void delete(Invite invite) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(invite);
	}

	@Override
	public Invite findById(long inviteId) {
		// TODO Auto-generated method stub
		return (Invite) sessionFactory.getCurrentSession().createQuery("from Invite where id = :inviteId").setLong("inviteId", inviteId).uniqueResult();
	}

}
