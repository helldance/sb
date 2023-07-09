package com.coordsafe.core.rbac.dao;

import java.util.List;

import com.coordsafe.core.rbac.entity.Resource;

public interface ResourceDAO {

	void save(Resource resource);

	void update(Resource resource);

	void delete(Resource resource);

	void delete(String resourceName);

	List<Resource> findByType(String type);

	Resource findByName(String name);

	List<Resource> findByExample(Resource resource);

	List<Resource> findAll();
}
