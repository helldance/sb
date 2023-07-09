/**
 * @author Yang Wei
 * @Date Nov 13, 2013
 */
package com.coordsafe.im.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.im.dao.ImDAO;
import com.coordsafe.im.entity.InstantMessage;

/**
 * @author Yang Wei
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ChatServiceImpl implements ImService{
	private static final Log log = LogFactory.getLog(ChatServiceImpl.class);

	@Autowired
	private ImDAO chatDao;
	
	/**
	 * @param chatDao the chatDao to set
	 */
	public void setChatDao(ImDAO chatDao) {
		this.chatDao = chatDao;
	}

	@Override
	public void create(InstantMessage message) {
		log.info("create chat message..");
		
		chatDao.create(message);		
	}

}
