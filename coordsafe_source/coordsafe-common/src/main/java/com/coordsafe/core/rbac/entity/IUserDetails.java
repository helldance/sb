package com.coordsafe.core.rbac.entity;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

public interface IUserDetails extends UserDetails {

	public Long getId();
	public Long getRetryCount();
	public Date getLastLogin();
	public String getSalt();
}
