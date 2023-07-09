package com.coordsafe.core.rbac.dao;

import java.util.List;

import com.coordsafe.core.rbac.entity.Permission;
import com.coordsafe.core.rbac.entity.Resource;

public interface PermissionDAO {

	void save(Permission permission);

	void update(Permission permission);

	void delete(Permission permission);

	Permission load(Long id);

	List<Permission> findByExample(Permission permission);

	List<Permission> findByResourceId(Resource resource);

	List<Permission> findByResourcePermission(Resource resource,
			String permission);

	List<Permission> findAll();
}
