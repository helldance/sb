package com.coordsafe.core.rbac.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.validator.constraints.NotBlank;

import com.coordsafe.core.rbac.entity.Group;
import com.coordsafe.core.rbac.entity.Organization;
import com.coordsafe.core.rbac.entity.Role;
import com.coordsafe.core.rbac.entity.User;

@SuppressWarnings("serial")
@XmlRootElement
@Entity
public class Group implements Serializable, Comparable<Group> {

	private Long id;
	private String name;
	private String description;
	private Set<User> users = new HashSet<User>();
	private Set<Role> roles = new HashSet<Role>();
	private Set<Organization> organizations = new HashSet<Organization>();
	
	public Group() {
		super();
	}

	public Group(Long id, String name, String description, Set<User> users) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.users = users;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Size(min = 2, max = 40)
	@NotBlank
	@Column(unique=true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonManagedReference("users")
	@ManyToMany(fetch = FetchType.EAGER)
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	public Set<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	public Set<Organization> getOrganizations() {
		return organizations;
	}
	
	public void setOrganizations(Set<Organization> organizations) {
		this.organizations = organizations;
	}
	
	/*
	 * Transient fields
	 */
	
	/*
	 * Check whether the current operation is adding a new Group.
	 */
	@Transient
	public boolean isCreate() {
		return (this.id == null);
	}
	
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int hash = 1;
		
		hash = hash * PRIME + ((id == null) ? 0 : id.hashCode());
		hash = hash * PRIME + ((name == null) ? 0 : name.hashCode());
		hash = hash * PRIME + ((description == null) ? 0 : description.hashCode());
		
		return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (!(obj instanceof Group))
			return false;
		Group other = (Group) obj;
		if (other.getId() == null)
			return false;
		else if (other.getId() != this.id)
			return false;
		else
			return true;
	}
	
	@Override
	public int compareTo(Group o) {
		if (!this.name.equals(o.getName())) {
			return this.name.compareTo(o.getName());
		}
		
		return 0;
	}
}
