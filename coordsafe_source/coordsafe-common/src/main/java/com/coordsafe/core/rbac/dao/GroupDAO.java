package com.coordsafe.core.rbac.dao;

import java.util.List;

import com.coordsafe.core.rbac.entity.Group;

public interface GroupDAO {

	void save(Group group);
	
	void update(Group group);
	
	void delete(String name);
	
	List<Group> findAll();
	
	Group findByName(String name);
}
