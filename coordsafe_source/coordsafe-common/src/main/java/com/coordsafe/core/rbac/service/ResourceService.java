package com.coordsafe.core.rbac.service;

import java.util.List;

import com.coordsafe.core.rbac.entity.Resource;
import com.coordsafe.core.rbac.exception.ResourceException;

public interface ResourceService {

	void create(Resource resource) throws ResourceException;

	void update(Resource resource) throws ResourceException;

	void delete(Resource resource) throws ResourceException;

	void delete(String resourceName) throws ResourceException;

	Resource findByName(String resourceName);

	List<Resource> findByType(String type);

	List<Resource> findAll();
}
