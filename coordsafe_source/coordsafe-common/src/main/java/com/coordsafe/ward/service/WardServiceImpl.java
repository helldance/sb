package com.coordsafe.ward.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.ward.dao.WardDAO;
import com.coordsafe.ward.entity.Ward;

import org.slf4j.*;
@Transactional(propagation=Propagation.REQUIRED)
@Service("wardService")
public class WardServiceImpl implements WardService {
	Logger log = LoggerFactory.getLogger(WardServiceImpl.class);
	private WardDAO wardDao;

	@Override
	//@CachePut(value="wardCache",key="#ward.getLocator().getId()")
	public Ward create(Ward ward) {
		wardDao.create(ward);
		return ward;
	}

	@Override
	//@CacheEvict(value="guardianCache", key="#ward.getGuardians().toArray()[0].getId()")
	@CacheEvict(value={"guardianCache", "wardCache"}, allEntries=true) 
	public Ward update(Ward ward) {
		wardDao.update(ward);
		return ward;
	}

	@Override
	//@CacheEvict(value="guardianCache", key="#ward.getGuardians().toArray()[0].getId()") 
	public void delete(Ward ward){
		// TODO Auto-generated method stub
		wardDao.delete(ward);
	}

	@Override
	//@CacheEvict(value="guardianCache", allEntries=true) 
	public void delete(String wardName) {
		// TODO Auto-generated method stub
		wardDao.delete(this.findByName(wardName));
	}

	@Override
	public Ward findByName(String wardName) {
		// TODO Auto-generated method stub
		return wardDao.findByName(wardName);
	}

	@Override
	public List<Ward> findByType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ward> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param wardDao the wardDao to set
	 */
	@Autowired
	public void setWardDao(WardDAO wardDao) {
		this.wardDao = wardDao;
	}

	@Override
	public List<Ward> findByGuardian(String guardianname) {
		return wardDao.findByGuardianId(guardianname);
	}

	@Override
	public Ward findByID(Long wardID) {
		// TODO Auto-generated method stub
		return wardDao.findByID(wardID);
	}

	@Override
	//@Cacheable("wardCache")
	public Ward findByLocatorID(Long locatorid) {
		log.info("Getting ward from DB by locatorID");
		return wardDao.findByLocatorID(locatorid);
	}

	@Override
	public void deleteById(long wardId) {
		// TODO Auto-generated method stub
		wardDao.delete(this.findByID(wardId));
	}

	@Override
	//@CacheEvict(value="wardCache" , key="#ward.getLocator().getId()")
	public void updateWardFenceStatus(Ward ward) {
		// TODO Auto-generated method stub
		wardDao.update(ward);
	}

	@Override
	//@CacheEvict(value="wardCache" , key="#ward.getLocator().getId()")
	public void updateCache(Ward ward) {
		// TODO Auto-generated method stub
		log.info("Update Cache..." + ward.getLocator().getId());
	}

}
