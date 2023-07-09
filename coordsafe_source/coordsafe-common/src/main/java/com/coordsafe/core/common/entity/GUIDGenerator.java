package com.coordsafe.core.common.entity;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * generate GUID for the entity if and only if there's no GUID provided for the 
 * entity.
 *  
 * @since Apr 25, 2011
 *
 * @author Daren Mok
 * @version 1.0
 */

public class GUIDGenerator implements IdentifierGenerator {
	private static final Logger logger = LoggerFactory.getLogger(GUIDGenerator.class);

	/* (non-Javadoc)
	 * @see org.hibernate.id.IdentifierGenerator#generate(org.hibernate.engine.SessionImplementor, java.lang.Object)
	 */
	@Override
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		BaseEntity baseEntity = (BaseEntity) object;
		String id = baseEntity.getId();
		if(logger.isTraceEnabled()) {
			logger.trace("Passed in GUID:" + id);
		}
		if(id == null || id.isEmpty()) {
			id = "{" + UUID.randomUUID().toString() +"}";
		}
		return id;
	}

}
