package com.coordsafe.retailer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.retailer.dao.SimcardDAO;
import com.coordsafe.retailer.entity.Simcard;



@Transactional(propagation=Propagation.REQUIRED)
@Service("simcardService")
public class SimcardServiceImpl implements SimcardService {
	
	private SimcardDAO simcardDao;

	@Override
	public void create(Simcard simcard) {
		simcardDao.create(simcard);
	}

	@Override
	public void update(Simcard simcard){
		// TODO Auto-generated method stub
		simcardDao.update(simcard);
	}

	@Override
	public void delete(Simcard simcard) {
		// TODO Auto-generated method stub
		simcardDao.delete(simcard);
	}

	@Override
	public void delete(String simcardName) {
		// TODO Auto-generated method stub
		simcardDao.delete(this.findByName(simcardName));
	}

	@Override
	public Simcard findByName(String simcardName) {
		// TODO Auto-generated method stub
		return simcardDao.findByName(simcardName);
	}

	@Override
	public List<Simcard> findByType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Simcard> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param simcardDao the simcardDao to set
	 */
	@Autowired
	public void setSimcardDao(SimcardDAO simcardDao) {
		this.simcardDao = simcardDao;
	}

	@Override
	public List<Simcard> findByGuardian(String guardianname) {
		return simcardDao.findByGuardianId(guardianname);
	}

	@Override
	public Simcard findByID(String simimei) {
		// TODO Auto-generated method stub
		return simcardDao.findByID(simimei);
	}

	@Override
	public Simcard findByLocatorID(Long locatorid) {
		// TODO Auto-generated method stub
		return simcardDao.findByLocatorID(locatorid);
	}

}
