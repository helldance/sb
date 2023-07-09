package com.coordsafe.core.rbac.service;

import java.util.List;
import java.util.Set;

import com.coordsafe.core.rbac.entity.Organization;
import com.coordsafe.core.rbac.entity.Role;
import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.entity.UserList;
import com.coordsafe.core.rbac.exception.UserException;

public interface UserService {

	void addUser(User user) throws UserException;

	void removeUser(User user) throws UserException;

	void removeUser(String username) throws UserException;

	void updateUser(User user) throws UserException;

	void assignRoles(String username, Set<Role> roles) throws UserException;

	void removeRoles(String username, List<String> roles) throws UserException;

	void joinOrganization(String username, List<String> organizations)
			throws UserException;

	void quitOrganization(String username, List<String> organizations)
			throws UserException;
	
	User login(String username, String password) throws UserException;

	void changePassword(String username, String oldPassword, String newPassword)
			throws UserException;
	
	void resetPassword(String username, String password) throws UserException;

	User findByUsername(String username);
	
	User findById(Long id);

	UserList findAll();
	
	void disableUser(String username);
	
	void enableUser(String username);
	
	Set<Role> getUnionRoles(String username);
	
	Set<Organization> getUnionOrganizations(String username);
	
	User findByUsernameOrEmail(String nameOrEmail);
}
