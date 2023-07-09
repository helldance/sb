package com.coordsafe.safedistance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coordsafe.safedistance.dao.SafedistanceDao;
import com.coordsafe.safedistance.entity.Safedistance;

/**
 * @author Yang Wei
 * @Date Feb 4, 2014
 */
@Service
public class SafedistanceServiceImpl implements SafedistanceService {
	@Autowired SafedistanceDao safedistanceDao;
	/* (non-Javadoc)
	 * @see com.coordsafe.safedistance.service.SafedistanceService#create(com.coordsafe.safedistance.entity.Safedistance)
	 */
	@Override
	public void create(Safedistance distance) {
		safedistanceDao.create(distance);

	}

	/* (non-Javadoc)
	 * @see com.coordsafe.safedistance.service.SafedistanceService#update(com.coordsafe.safedistance.entity.Safedistance)
	 */
	@Override
	public void update(Safedistance distance) {
		safedistanceDao.update(distance);

	}

	/* (non-Javadoc)
	 * @see com.coordsafe.safedistance.service.SafedistanceService#findById(long)
	 */
	@Override
	public Safedistance findById(long id) {
		return safedistanceDao.findById(id);
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.safedistance.service.SafedistanceService#findByGuardianId(long)
	 */
	@Override
	public Safedistance findByGuardianId(long guardianId) {
		return safedistanceDao.findByGuardianId(guardianId);
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.safedistance.service.SafedistanceService#delete(long)
	 */
	@Override
	public void delete(long id) {
		safedistanceDao.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.safedistance.service.SafedistanceService#deleteByGuardian(long)
	 */
	@Override
	public void deleteByGuardian(long guardianId) {
		safedistanceDao.deleteByGuardian(guardianId);
	}

}
