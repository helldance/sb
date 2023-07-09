package com.coordsafe.core.rbac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.core.rbac.dao.PermissionDAO;
import com.coordsafe.core.rbac.entity.Permission;
import com.coordsafe.core.rbac.entity.Resource;
import com.coordsafe.core.rbac.exception.PermissionException;
import com.coordsafe.core.rbac.service.PermissionService;

@Transactional(propagation=Propagation.REQUIRED)
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

	private PermissionDAO permissionDAO;

	@Autowired
	public void setPermissionDAO(PermissionDAO permissionDAO) {
		this.permissionDAO = permissionDAO;
	}

	@Override
	public void create(Permission permission) throws PermissionException {
		permissionDAO.save(permission);
	}

	@Override
	public void update(Permission permission) throws PermissionException {
		permissionDAO.update(permission);
	}

	@Override
	public void delete(Permission permission) throws PermissionException {
		permissionDAO.delete(permission);
	}

	@Override
	public List<Permission> findByExample(Permission permission) {
		return permissionDAO.findByExample(permission);
	}

	@Override
	public List<Permission> findAll() {
		return permissionDAO.findAll();
	}

	@Override
	public List<Permission> findByResourceId(Resource resource) {
		return permissionDAO.findByResourceId(resource);
	}
}
