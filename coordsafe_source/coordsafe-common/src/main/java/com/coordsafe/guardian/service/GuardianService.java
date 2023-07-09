package com.coordsafe.guardian.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.guardian.dao.GuardianUserDAO;
import com.coordsafe.guardian.entity.Guardian;



import com.coordsafe.ward.entity.Ward;

import org.slf4j.*;
import org.springframework.cache.annotation.CacheEvict; 
import org.springframework.cache.annotation.Cacheable; 
@Service("guardianService")
@Transactional
public class GuardianService
{
    protected static Logger logger = LoggerFactory.getLogger(GuardianService.class);

    @Autowired
    GuardianUserDAO guardianDAO;
    
    //@Cacheable("guardianCache" )
    public Guardian get(Integer id)
    {
        logger.debug("Retrieving guardian from DB by id");
       
        return guardianDAO.findById(id);
        
    }
    
    public Guardian get(String login)
    {
        logger.debug("Retrieving guardian by login: " + login);
        return guardianDAO.findByGuardianname(login);
        
    }
    //create a new guardian
    public void add(Guardian guardian)
    {
        logger.debug("Adding new guardian");
        guardianDAO.save(guardian);
    }
    //@CacheEvict(value="guardianCache" , key="#guardian.getId()")
    public void update(Guardian guardian)
    {
        logger.debug("Updating guardian by login");
        guardianDAO.update(guardian);
        
    }
    
    //@CacheEvict(value="guardianCache" , key="#ward.getGuardians().toArray()[0].getId()")
    public void cleanGuardianCache(Ward ward){
    	logger.info("Clean guardian cache for " + ward.getGuardians().toArray()[0]);
    }

	public List<Guardian> getAllGuardians() {
		// TODO Auto-generated method stub
		return guardianDAO.getAllGuardians();
	}

	public Guardian getByEmail(String email) {
		// TODO Auto-generated method stub
		return guardianDAO.getByEmail(email);
	}
	
	public Guardian getByNameOrEmail(String nameOrEmail){
		Guardian g = guardianDAO.findByGuardianname(nameOrEmail);
		
		if (g == null){
			g = guardianDAO.getByEmail(nameOrEmail);
		}
		
		return g;
		
	}
	
	public Guardian getByApiKey (String apikey){
		return guardianDAO.getByApiKey(apikey);
	}
}
