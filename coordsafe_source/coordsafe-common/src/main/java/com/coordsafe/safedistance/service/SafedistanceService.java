package com.coordsafe.safedistance.service;

import com.coordsafe.safedistance.entity.Safedistance;

/**
 * @author Yang Wei
 * @Date Feb 4, 2014
 */
public interface SafedistanceService {
	public void create(Safedistance distance);
	public void update(Safedistance distance);
	public Safedistance findById(long id);
	public Safedistance findByGuardianId(long guardianId);
	public void delete (long id);
	public void deleteByGuardian (long guardianId);
}
