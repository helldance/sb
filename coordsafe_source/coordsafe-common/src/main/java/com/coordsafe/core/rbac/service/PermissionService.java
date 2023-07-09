package com.coordsafe.core.rbac.service;

import java.util.List;

import com.coordsafe.core.rbac.entity.Permission;
import com.coordsafe.core.rbac.entity.Resource;
import com.coordsafe.core.rbac.exception.PermissionException;

public interface PermissionService {

	void create(Permission permission) throws PermissionException;

	void update(Permission permission) throws PermissionException;

	void delete(Permission permission) throws PermissionException;

	List<Permission> findByExample(Permission permission);

	List<Permission> findAll();
	
	List<Permission> findByResourceId(Resource resource);
}
