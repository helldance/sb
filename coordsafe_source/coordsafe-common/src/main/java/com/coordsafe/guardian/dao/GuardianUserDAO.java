package com.coordsafe.guardian.dao;

import java.util.List;

import com.coordsafe.guardian.entity.Guardian;



public interface GuardianUserDAO {

	void save(Guardian user);

	void update(Guardian user);
	

	void delete(Guardian user);

	Guardian findByGuardianname(String username);

	Guardian findById(Integer id);


	Boolean updatePassword(String username, String password, String salt);


	List<Guardian> getAllGuardians();
	
	
	Boolean lockGuardianAccount(String username);

	Boolean unlockGuardianAccount(String username);

	Guardian getByEmail(String email);

	Guardian getByApiKey(String apikey);
	
	


}
