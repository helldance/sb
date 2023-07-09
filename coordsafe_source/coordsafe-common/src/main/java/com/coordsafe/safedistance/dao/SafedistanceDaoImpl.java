package com.coordsafe.safedistance.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.safedistance.entity.Safedistance;

/**
 * @author Yang Wei
 * @Date Feb 4, 2014
 */
@Repository
@Transactional
public class SafedistanceDaoImpl implements SafedistanceDao {
	@Autowired SessionFactory sessionFactory;
	
	private static final Log log = LogFactory.getLog("SafedistanceDaoImpl");
	
	/* (non-Javadoc)
	 * @see com.coordsafe.safedistance.dao.SafedistanceDao#create(com.coordsafe.safedistance.entity.Safedistance)
	 */
	@Override
	public void create(Safedistance distance) {
		log.info("save safe-distance session");
		
		sessionFactory.getCurrentSession().save(distance);
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.safedistance.dao.SafedistanceDao#update(com.coordsafe.safedistance.entity.Safedistance)
	 */
	@Override
	public void update(Safedistance distance) {
		sessionFactory.getCurrentSession().update(distance);
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.safedistance.dao.SafedistanceDao#findById(long)
	 */
	@Override
	public Safedistance findById(long id) {
		return (Safedistance) sessionFactory.getCurrentSession().createQuery("from Safedistance sd where sd.id = :id").setLong("id", id).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.safedistance.dao.SafedistanceDao#findByGuardianId(long)
	 */
	@Override
	public Safedistance findByGuardianId(long guardianId) {
		log.info("find safedistance by guardian " + guardianId);
		
		return (Safedistance) sessionFactory.getCurrentSession().createQuery("from Safedistance sd where sd.guardian.id = :guardianId").setLong("guardianId", guardianId).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.safedistance.dao.SafedistanceDao#delete(long)
	 */
	@Override
	public void delete(long id) {
		sessionFactory.getCurrentSession().delete(findById(id));
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.safedistance.dao.SafedistanceDao#deleteByGuardian(long)
	 */
	@Override
	public void deleteByGuardian(long guardianId) {
		Safedistance dist = findByGuardianId(guardianId);
		
		if (dist != null){
			sessionFactory.getCurrentSession().delete(dist);
		}

	}

}
