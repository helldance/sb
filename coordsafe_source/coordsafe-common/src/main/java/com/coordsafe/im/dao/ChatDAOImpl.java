/**
 * @author Yang Wei
 * @Date Nov 13, 2013
 */
package com.coordsafe.im.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coordsafe.im.entity.InstantMessage;

/**
 * @author Yang Wei
 *
 */
@Repository
public class ChatDAOImpl implements ImDAO {
	private static final Log log = LogFactory.getLog(ChatDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void create(InstantMessage message) {
		log.info("create chat message..");
		
		sessionFactory.getCurrentSession().save(message);
	}

}
