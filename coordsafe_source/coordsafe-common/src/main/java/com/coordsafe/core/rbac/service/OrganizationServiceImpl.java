package com.coordsafe.core.rbac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.core.rbac.dao.OrganizationDAO;
import com.coordsafe.core.rbac.entity.Organization;
import com.coordsafe.core.rbac.exception.OrganizationException;
import com.coordsafe.core.rbac.service.OrganizationService;

@Transactional(propagation = Propagation.REQUIRED)
@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService {

	private OrganizationDAO organizationDAO;

	@Autowired
	public void setOrganizationDAO(OrganizationDAO organizationDAO) {
		this.organizationDAO = organizationDAO;
	}

	@Override
	public void create(Organization organization) throws OrganizationException {
		if (organizationDAO.findByName(organization.getName()) != null) {
			throw new OrganizationException("Organization "
					+ organization.getName() + " already exists.");
		} else {
			organizationDAO.save(organization);
		}
	}

	@Override
	public void update(Organization organization) throws OrganizationException {
		organizationDAO.update(organization);
	}

	@Override
	public void delete(String organizationName) throws OrganizationException {
		if (organizationDAO.findByName(organizationName) == null) {
			throw new OrganizationException("Organization " + organizationName
					+ " does not exists.");
		} else {
			organizationDAO.delete(organizationDAO.findByName(organizationName));
		}
	}

	@Override
	public Organization findByName(String name) {
		return organizationDAO.findByName(name);
	}
	
	@Override
	public Organization findById(Long id) {
		return organizationDAO.findById(id);
	}

	@Override
	public List<Organization> findAll() {
		return organizationDAO.findAll();
	}

}
