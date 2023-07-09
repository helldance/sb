package com.coordsafe.core.rbac.service;

import java.util.Date;

import com.coordsafe.core.rbac.exception.UserException;

public interface PasswordMgmtService {

	Boolean login(String username, String password) throws UserException;

	Boolean changePassword(String username, String oldPassword,
			String newPassword) throws UserException;

	Boolean resetPassword(String username, String newPassword)
			throws UserException;

	Boolean isPasswordExpire(String username) throws UserException;

	Date getLastLogin(String username) throws UserException;

	Boolean isFirstTimeLogin(String username) throws UserException;

	int passwordStrength(String password) throws UserException;

	void lockUserAccount(String username) throws UserException;

	void unlockUserAccount(String username) throws UserException;
}
