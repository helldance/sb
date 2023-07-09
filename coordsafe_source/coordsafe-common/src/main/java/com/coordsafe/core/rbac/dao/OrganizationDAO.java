package com.coordsafe.core.rbac.dao;

import java.util.List;

import com.coordsafe.core.rbac.entity.Organization;

public interface OrganizationDAO {

	void save(Organization organization);

	void delete(Organization organization);

	Organization findByName(String name);

	Organization findById(Long id);

	List<Organization> findAll();

	void update(Organization organization);
}
