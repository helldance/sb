package com.coordsafe.core.rbac.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.coordsafe.core.rbac.entity.User;

/**
 * This entity is to hold a list of User entity.
 * This entity is required only if JAXB is needed for marshaling to XML.
 * JAXB does not know how to handle List or Collection type interfaces, hence
 * the need for this class.
 * 
* For demonstration purposes, UserList is created so that a List of Users
 * could be marshaled to XML.
 * 
 * @author Darren Mok
 *
 */

@XmlRootElement
public class UserList {
	
	private List<User> users;

	public UserList() {
		super();
	}

	public UserList(List<User> users) {
		super();
		this.users = users;
	}

	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}
}
