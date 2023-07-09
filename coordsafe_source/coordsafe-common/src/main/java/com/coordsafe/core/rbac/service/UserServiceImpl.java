package com.coordsafe.core.rbac.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.core.rbac.dao.OrganizationDAO;
import com.coordsafe.core.rbac.dao.RoleDAO;
import com.coordsafe.core.rbac.dao.UserDAO;
import com.coordsafe.core.rbac.entity.Group;
import com.coordsafe.core.rbac.entity.Organization;
import com.coordsafe.core.rbac.entity.PasswordHistory;
import com.coordsafe.core.rbac.entity.Role;
import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.entity.UserList;
import com.coordsafe.core.rbac.exception.UserException;

@Transactional(propagation=Propagation.REQUIRED)
@Service("userService")
public class UserServiceImpl implements UserService {
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	private UserDAO userDAO;
	private RoleDAO roleDAO;
	private OrganizationDAO organizationDAO;
	private PasswordEncoder passwordEncoder;
	private SaltSource saltSource;

	@Autowired
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Autowired
	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	@Autowired
	public void setOrganizationDAO(OrganizationDAO organizationDAO) {
		this.organizationDAO = organizationDAO;
	}

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Autowired
	public void setSaltSource(SaltSource saltSource) {
		this.saltSource = saltSource;
	}

	@Override
	public void addUser(User user) throws UserException {
		if (userDAO.findByUsername(user.getUsername()) != null) {
			throw new UserException("User " + user.getUsername()
					+ " already exists!");
		} else {
			userDAO.save(user);		// need to save user first to get the id for saltSource to work.
			user.setPassword(passwordEncoder.encodePassword(user.getPassword(),
					saltSource.getSalt(user)));
			user.setEnabled(true);
			user.setCreatedDate(new Date());
			userDAO.save(user);
			addPasswordHistory(user.getUsername(), user.getPassword());
		}
	}

	@Override
	public void removeUser(User user) throws UserException {
		userDAO.delete(user);
	}

	@Override
	public void removeUser(String username) throws UserException {
		User user = userDAO.findByUsername(username);

		// Set<Role> roles = user.getRoles();
		// Iterator<Role> itr = roles.iterator();
		// while(itr.hasNext()) {
		// Role role = itr.next();
		// role.getUsers().remove(user);
		// }
		removeUser(user);
	}

	@Override
	public void updateUser(User user) throws UserException {
		userDAO.update(user);
	}

	@Override
	public void assignRoles(String username, Set<Role> roles)
			throws UserException {
		User user = userDAO.findByUsername(username);
		user.setRoles(roles);
		userDAO.update(user);
	}

	@Override
	public void removeRoles(String username, List<String> roles)
			throws UserException {
		User user = userDAO.findByUsername(username);
		for (String name : roles) {
			Role role = roleDAO.findByRoleName(name);
			user.getRoles().remove(role);
		}
		userDAO.update(user);
	}

	@Override
	public void joinOrganization(String username, List<String> organizations)
			throws UserException {
		User user = userDAO.findByUsername(username);
		for (String name : organizations) {
			Organization organization = organizationDAO.findByName(name);
			user.getOrganizations().add(organization);
		}
		userDAO.update(user);
	}

	@Override
	public void quitOrganization(String username, List<String> organizations)
			throws UserException {
		User user = userDAO.findByUsername(username);
		for (String name : organizations) {
			Organization organization = organizationDAO.findByName(name);
			user.getOrganizations().remove(organization);
		}
		userDAO.update(user);
	}

	@Override
	public User login(String username, String password) throws UserException {
		User user = userDAO.findByUsername(username);
		if (user != null) {
			if (passwordEncoder.isPasswordValid(user.getPassword(), password,
					saltSource.getSalt(user))) {
				return user;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public void changePassword(String username, String oldPassword,
			String newPassword) throws UserException {
		log.info(username + " " + oldPassword + " " + newPassword);
		
		User user = userDAO.findByUsername(username);
		if (passwordEncoder.isPasswordValid(user.getPassword(), oldPassword,
				saltSource.getSalt(user))) {
			log.info("password is valid");
			
			user.setPassword(passwordEncoder.encodePassword(newPassword,
					saltSource.getSalt(user)));
		} else {
			log.info("password is invalid");			
			
			// no check for old password - by YW 06/30/2013
			user.setPassword(passwordEncoder.encodePassword(newPassword,
					saltSource.getSalt(user)));
		}
		
		userDAO.update(user);
		addPasswordHistory(user.getUsername(), user.getPassword());
	}

	@Override
	public void resetPassword(String username, String password)
			throws UserException {
		User user = userDAO.findByUsername(username);
		user.setPassword(passwordEncoder.encodePassword(password, saltSource.getSalt(user)));
		userDAO.update(user);
	}
	
	@Override
	public User findByUsername(String username) {
		return userDAO.findByUsername(username);
	}
	
	@Override
	public User findById(Long id) {
		return userDAO.findById(id);
	}

	@Override
	public UserList findAll() {
		return userDAO.findAll();
	}

	private void addPasswordHistory(String username,
			String password) {
		User user = userDAO.findByUsername(username);
		PasswordHistory passwordHistory = new PasswordHistory(null, password, new Date());
		user.getPasswordHistory().add(passwordHistory);
		userDAO.update(user);
	}

	@Override
	public void disableUser(String username) {
		userDAO.lockUserAccount(username);
	}

	@Override
	public void enableUser(String username) {
		userDAO.unlockUserAccount(username);
	}

	@Override
	public Set<Role> getUnionRoles(String username) {
		User user = userDAO.findByUsername(username);
		Set<Role> roles = new HashSet<Role>(user.getRoles());
		Set<Group> groups = new HashSet<Group>(user.getGroups());
		
		for (Group group : groups) {
			roles.addAll(group.getRoles());
		}
		
		return roles;
	}

	@Override
	public Set<Organization> getUnionOrganizations(String username) {
		User user = userDAO.findByUsername(username);
		Set<Organization> organizations = new HashSet<Organization>(user.getOrganizations());
		Set<Group> groups = new HashSet<Group>(user.getGroups());
		
		for (Group group : groups) {
			organizations.addAll(group.getOrganizations());
		}
		
		return organizations;
	}

	@Override
	public User findByUsernameOrEmail(String nameOrEmail) {
		// TODO Auto-generated method stub
		return userDAO.findByUsernameOrEmail(nameOrEmail);
	}
}
