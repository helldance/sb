package com.coordsafe.core.rbac.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.core.rbac.dao.GroupDAO;
import com.coordsafe.core.rbac.entity.Group;
import com.coordsafe.core.rbac.entity.Organization;
import com.coordsafe.core.rbac.entity.Role;
import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.exception.GroupException;
import com.coordsafe.core.rbac.service.GroupService;

@Transactional(propagation = Propagation.REQUIRED)
@Service("groupService")
public class GroupServiceImpl implements GroupService {

	private GroupDAO groupDAO;
	
	@Autowired
	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}
	
	@Override
	public void addGroup(Group group) throws GroupException {
		groupDAO.save(group);
	}

	@Override
	public void updateGroup(Group group) throws GroupException {
		groupDAO.update(group);
	}

	@Override
	public void deleteGroup(String name) throws GroupException {
		groupDAO.delete(name);
	}

	@Override
	public List<Group> findAll() {
		return groupDAO.findAll();
	}

	@Override
	public Group findByName(String name) {
		return groupDAO.findByName(name);
	}

	@Override
	public void addUser(String name, User user) throws GroupException {
		Group group = findByName(name);
		
		group.getUsers().add(user);
		updateGroup(group);
	}

	@Override
	public void addUsers(String name, Collection<User> users)
			throws GroupException {
		Group group = findByName(name);
		group.getUsers().addAll(users);
		updateGroup(group);
	}

	@Override
	public void addRole(String name, Role role) throws GroupException {
		Group group = findByName(name);
		group.getRoles().add(role);
		updateGroup(group);
	}

	@Override
	public void addRoles(String name, Collection<Role> roles)
			throws GroupException {
		Group group = findByName(name);
		group.getRoles().addAll(roles);
		updateGroup(group);
	}

	@Override
	public void addOrganization(String name, Organization organization)
			throws GroupException {
		Group group = findByName(name);
		group.getOrganizations().add(organization);
		updateGroup(group);
	}

	@Override
	public void addOrganizations(String name,
			Collection<Organization> organizations) throws GroupException {
		Group group = findByName(name);
		group.getOrganizations().addAll(organizations);
		updateGroup(group);
	}
}
