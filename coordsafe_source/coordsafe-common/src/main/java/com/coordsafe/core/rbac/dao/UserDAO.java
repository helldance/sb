package com.coordsafe.core.rbac.dao;

import java.util.List;

import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.entity.UserList;

public interface UserDAO {

	void save(User user);

	void update(User user);

	void delete(User user);

	User findByUsername(String username);

	User findById(Long id);

	UserList findByExample(User user);

	UserList findByProperty(String propertyName, Object value);

	Boolean updateLastLogin(String username);

	Boolean updatePassword(String username, String password, String salt);

	Boolean updateRetry(String username, Long retry);

	List<User> getUsers(String username);
	
	User getUser(String username);

	Boolean lockUserAccount(String username);

	Boolean unlockUserAccount(String username);

	UserList findAll();

	User findByUsernameOrEmail(String nameOrEmail);
}
