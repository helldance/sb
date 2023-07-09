package com.coordsafe.guardian.security;

import org.springframework.security.core.GrantedAuthority;

public class GuardianUserGrantedAuthority implements GrantedAuthority {
	private static final long serialVersionUID = -3786297951121082644L;

	private String authority = null;

	public GuardianUserGrantedAuthority(String auth) {
		authority = auth;
	}

	@Override
	public String getAuthority() {
		return authority;
	}
}
