package com.coordsafe.core.rbac.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.config.Password;
import com.coordsafe.core.rbac.dao.UserDAO;
import com.coordsafe.core.rbac.entity.PasswordHistory;
import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.exception.UserException;
import com.coordsafe.core.rbac.service.PasswordMgmtService;

@Transactional(propagation=Propagation.REQUIRED)
@Service("passwordMgmtService")
public class PasswordMgmtServiceImpl implements PasswordMgmtService {

	private UserDAO userDAO;
	private PasswordEncoder passwordEncoder;
	private SaltSource saltSource;
	private Password msapPassword;

	private int lastNPassword;
	private int passwordExpiryDuration;
	private int noLoginRetry;
	private int minLength;

	/*
	 * Getters and setters
	 */

	@Autowired
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Autowired
	public void setSaltsource(SaltSource saltsource) {
		this.saltSource = saltsource;
	}

	@Autowired
	public void setPasswordRule(Password msapPassword) {
		this.msapPassword = msapPassword;
	}

	@SuppressWarnings("unused")
	@Required @Value("${passwordMgmtProperties.lastNPassword}")
	private void setLastNPassword(int lastNPassword) {
		this.lastNPassword = lastNPassword;
	}

	@SuppressWarnings("unused")
	@Required @Value("${passwordMgmtProperties.passwordExpiryDuration}")
	private void setPasswordExpiryDuration(int passwordExpiryDuration) {
		this.passwordExpiryDuration = passwordExpiryDuration;
	}

	@SuppressWarnings("unused")
	@Required @Value("${passwordMgmtProperties.noLoginRetry}")
	private void setNoLoginRetry(int noLoginRetry) {
		this.noLoginRetry = noLoginRetry;
	}

	@SuppressWarnings("unused")
	@Required @Value("${passwordMgmtProperties.minLength}")
	private void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	/*
	 * Methods
	 */

	@Override
	public Boolean login(String username, String password) throws UserException {
		Boolean bool;
		User user = userDAO.getUser(username);
		if (user != null && user.isAccountNonLocked()) {
			if (bool = passwordEncoder.isPasswordValid(user.getPassword(),
					password, saltSource.getSalt(user))) {
				userDAO.updateLastLogin(username);
			} else {
				if (user.getRetryCount() == noLoginRetry) {
					lockUserAccount(username);
				}
				userDAO.updateRetry(username, user.getRetryCount());
			}
			return bool;
		} else {
			throw new UserException();
		}
	}

	@Override
	public Boolean changePassword(String username, String oldPassword,
			String newPassword) throws UserException {

		if (oldPassword.trim().equals(newPassword.trim())) {
			throw new UserException();
		} else if (!msapPassword.isPasswordValid(newPassword.trim())) {
			throw new UserException();
		} else {
			User user = userDAO.getUser(username);
			if (!passwordEncoder.isPasswordValid(user.getPassword(),
					oldPassword, saltSource.getSalt(user))) {
				throw new UserException();
			} else {
				if (!user.getPasswordHistory().isEmpty()) {
					for (PasswordHistory passwordHistory : user.getPasswordHistory()) {
						if (passwordHistory
								.getPassword()
								.equals(passwordEncoder.encodePassword(
										newPassword, saltSource.getSalt(user)))) {
							throw new UserException();
						}
					}
				}
			}
		}
		return userDAO.updatePassword(
				username,
				passwordEncoder.encodePassword(newPassword,
						saltSource.getSalt(userDAO.getUser(username))), null);
	}

	@Override
	public Boolean resetPassword(String username, String newPassword)
			throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isPasswordExpire(String username) throws UserException {
		// TODO Auto-generated method stub
		User user = userDAO.getUser(username);
		if (user != null) {

		}
		return null;
	}

	@Override
	public Date getLastLogin(String username) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isFirstTimeLogin(String username) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int passwordStrength(String password) throws UserException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void lockUserAccount(String username) throws UserException {
		if (userDAO.lockUserAccount(username)) {

		} else {

		}
	}

	@Override
	public void unlockUserAccount(String username) throws UserException {
		if (userDAO.unlockUserAccount(username)) {

		} else {

		}
	}

}
