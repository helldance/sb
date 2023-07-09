package com.coordsafe.core.rbac.service;

import java.util.Collection;
import java.util.List;

import com.coordsafe.core.rbac.entity.Group;
import com.coordsafe.core.rbac.entity.Organization;
import com.coordsafe.core.rbac.entity.Role;
import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.exception.GroupException;

public interface GroupService {

	void addGroup(Group group) throws GroupException;
	
	void updateGroup(Group group) throws GroupException;
	
	void deleteGroup(String name) throws GroupException;
	
	List<Group> findAll();
	
	Group findByName(String name);
	
	void addUser(String name, User user) throws GroupException;
	
	void addUsers(String name, Collection<User> users) throws GroupException;
	
	void addRole(String name, Role role) throws GroupException;
	
	void addRoles(String name, Collection<Role> roles) throws GroupException;
	
	void addOrganization(String name, Organization organization) throws GroupException;
	
	void addOrganizations(String name, Collection<Organization> organizations) throws GroupException;
}
