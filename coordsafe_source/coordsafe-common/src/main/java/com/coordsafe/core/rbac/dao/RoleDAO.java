package com.coordsafe.core.rbac.dao;

import com.coordsafe.core.rbac.entity.Role;
import com.coordsafe.core.rbac.entity.RoleList;

public interface RoleDAO {

	void save(Role role);

	void delete(Role role);

	Role findById(Long id);

	Role findByRoleName(String roleName);

	RoleList findByExample(Role role);

	RoleList findByName(Object name);

	Role load(Long id);

	RoleList findAllRoles();

	Role merge(Role role);
}
