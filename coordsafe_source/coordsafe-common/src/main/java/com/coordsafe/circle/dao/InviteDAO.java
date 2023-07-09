/**
 * @author Yang Wei
 * @Date Oct 29, 2013
 */
package com.coordsafe.circle.dao;

import com.coordsafe.circle.entity.Invite;

/**
 * @author Yang Wei
 *
 */
public interface InviteDAO {
	public void create(Invite invite);
	public void update(Invite invite);
	public void delete(Invite invite);
	public Invite findById(long inviteId);
}
