/**
 * @author Yang Wei
 * @Date Oct 29, 2013
 */
package com.coordsafe.circle.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.coordsafe.circle.dao.InviteDAO;
import com.coordsafe.circle.entity.Invite;

/**
 * @author Yang Wei
 *
 */
public class InviteServiceImpl implements InviteService{
	@Autowired
	private InviteDAO inviteDao;

	@Override
	public void create(Invite invite) {
		inviteDao.create(invite);
	}

	@Override
	public void update(Invite invite) {
		inviteDao.update(invite);
	}

	@Override
	public void delete(Invite invite) {
		inviteDao.delete(invite);		
	}

	@Override
	public Invite findById(long inviteId) {
		// TODO Auto-generated method stub
		return inviteDao.findById(inviteId);
	}
}
