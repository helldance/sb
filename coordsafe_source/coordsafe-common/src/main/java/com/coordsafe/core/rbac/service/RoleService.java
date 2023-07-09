package com.coordsafe.core.rbac.service;

import com.coordsafe.core.rbac.entity.Role;
import com.coordsafe.core.rbac.entity.RoleList;
import com.coordsafe.core.rbac.exception.RoleException;

public interface RoleService {

	void addRole(Role role) throws RoleException;

	void updateRole(Role role) throws RoleException;

	void removeRole(Role role) throws RoleException;

	void removeRole(String roleName) throws RoleException;

	Role findByRoleName(String roleName);

	RoleList findByExample(Role role);

	RoleList findAllRoles();
}
