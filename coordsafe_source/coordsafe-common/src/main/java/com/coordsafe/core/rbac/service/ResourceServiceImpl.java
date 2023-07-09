package com.coordsafe.core.rbac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.core.rbac.dao.ResourceDAO;
import com.coordsafe.core.rbac.entity.Resource;
import com.coordsafe.core.rbac.exception.ResourceException;
import com.coordsafe.core.rbac.service.ResourceService;

@Transactional(propagation=Propagation.REQUIRED)
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

	private ResourceDAO resourceDAO;

	@Autowired
	public void setResourceDAO(ResourceDAO resourceDAO) {
		this.resourceDAO = resourceDAO;
	}

	@Override
	public void create(Resource resource) throws ResourceException {
		resourceDAO.save(resource);
	}

	@Override
	public void update(Resource resource) throws ResourceException {
		resourceDAO.update(resource);
	}

	@Override
	public void delete(Resource resource) throws ResourceException {
		resourceDAO.delete(resource);
	}

	@Override
	public void delete(String resourceName) throws ResourceException {
		Resource resource = resourceDAO.findByName(resourceName);
		if (resource != null) {
			resourceDAO.delete(resource);
		} else {
			throw new ResourceException("Resource name " + resourceName
					+ " does not exists!");
		}
	}

	@Override
	public Resource findByName(String resourceName) {
		return resourceDAO.findByName(resourceName);
	}

	@Override
	public List<Resource> findByType(String type) {
		return resourceDAO.findByType(type);
	}

	@Override
	public List<Resource> findAll() {
		return resourceDAO.findAll();
	}

}
