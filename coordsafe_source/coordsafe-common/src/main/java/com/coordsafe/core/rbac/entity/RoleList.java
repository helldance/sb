package com.coordsafe.core.rbac.entity;

import java.util.List;

/**
 * This entity is to hold a list of Role entity.
 * This entity is required only if JAXB is needed for marshaling to XML.
 * JAXB does not know how to handle List or Collection type interfaces, hence
 * the need for this class.
 * 
 * For demonstration purposes, RoleList is created so that a List of Roles
 * could be marshaled to XML.
 * 
 * @author Darren Mok
 *
 */

public class RoleList {

	private List<Role> roles;
	
	public RoleList() {
		super();
	}

	public RoleList(List<Role> roles) {
		super();
		this.roles = roles;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
