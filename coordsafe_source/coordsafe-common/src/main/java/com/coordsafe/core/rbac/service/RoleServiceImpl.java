package com.coordsafe.core.rbac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.core.rbac.dao.RoleDAO;
import com.coordsafe.core.rbac.entity.Role;
import com.coordsafe.core.rbac.entity.RoleList;
import com.coordsafe.core.rbac.exception.RoleException;
import com.coordsafe.core.rbac.service.RoleService;

@Transactional(propagation=Propagation.REQUIRED)
@Service("roleService")
public class RoleServiceImpl implements RoleService {

	private RoleDAO roleDAO;

	@Autowired
	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	@Override
	public void addRole(Role role) throws RoleException {
		if (roleDAO.findByRoleName(role.getName()) != null) {
			throw new RoleException("Role " + role.getName()
					+ " already exists!");
		} else {
			roleDAO.save(role);
		}
	}

	@Override
	public void updateRole(Role role) throws RoleException {
		roleDAO.merge(role);
	}

	@Override
	public void removeRole(Role role) throws RoleException {
		roleDAO.delete(role);
	}

	@Override
	public void removeRole(String roleName) throws RoleException {
		Role role = roleDAO.findByRoleName(roleName);
		if (role == null) {
			throw new RoleException("Role " + roleName + " does not exists!");
		} else {
			roleDAO.delete(role);
		}
	}

	@Override
	public Role findByRoleName(String roleName) {
		return roleDAO.findByRoleName(roleName);
	}

	@Override
	public RoleList findByExample(Role role) {
		return roleDAO.findByExample(role);
	}

	@Override
	public RoleList findAllRoles() {
		return roleDAO.findAllRoles();
	}

}
