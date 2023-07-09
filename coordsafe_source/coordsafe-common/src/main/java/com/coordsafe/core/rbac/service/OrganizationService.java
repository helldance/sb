package com.coordsafe.core.rbac.service;

import java.util.List;

import com.coordsafe.core.rbac.entity.Organization;
import com.coordsafe.core.rbac.exception.OrganizationException;

public interface OrganizationService {

	void create(Organization organization) throws OrganizationException;

	void update(Organization organization) throws OrganizationException;

	void delete(String organizationName) throws OrganizationException;

	Organization findByName(String name);
	
	Organization findById(Long id);

	List<Organization> findAll();
}
