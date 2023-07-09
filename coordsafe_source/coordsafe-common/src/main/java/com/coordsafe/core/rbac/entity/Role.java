package com.coordsafe.core.rbac.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;
import org.hibernate.validator.constraints.NotBlank;

import com.coordsafe.core.security.CoordsafeGrantedAuthority;
import com.coordsafe.core.rbac.entity.Permission;
import com.coordsafe.core.rbac.entity.Role;
import com.coordsafe.core.rbac.entity.User;

@SuppressWarnings("serial")
@XmlRootElement
@Entity
public class Role implements Serializable, CoordsafeGrantedAuthority, Comparable<Role> {

	/*
	 * Fields
	 */

	private Long id;
	private String name;
	private String type;
	private String description;
	private Set<User> users = new HashSet<User>();
	private Set<Permission> permissions = new HashSet<Permission>();

	/*
	 * Constructors
	 */

	public Role() {
		super();
	}

	public Role(Long id, String name, String type, String description,
			Set<User> users, Set<Permission> permissions) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.users = users;
		this.permissions = permissions;
	}

	/*
	 * Getters and Setters Persisted Fields
	 */

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

	@Size(max = 40)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// Many to many relationship with User
	@XmlInverseReference(mappedBy = "roles")
	@JsonBackReference("roles")
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	// Many to many relationship with Permission
	@JsonManagedReference("permissions")
	@ManyToMany(fetch = FetchType.EAGER)
	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	/*
	 * Transient Fields
	 */
	
	/*
	 * Check whether the current operation is adding a new role.
	 */
	@Transient
	public boolean isCreate() {
		return (this.id == null);
	}

	@Transient
	public List<Permission> getPermissons() {
		if (permissions.size() > 0) {
			List<Permission> permList = new ArrayList<Permission>();
			for (Permission permission : permissions) {
				permList.add(permission);
			}
			return permList;
		} else
			return null;
	}

	// For spring security GrantedAuthority.
	@Override
	@Transient
	public String getAuthority() {
		return name;
	}

	/*
	 * Overridden object methods
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int hash = 1;
		
		hash = hash * PRIME + ((id == null) ? 0 : id.hashCode());
		hash = hash * PRIME + ((name == null) ? 0 : name.hashCode());
		hash = hash * PRIME + ((type == null) ? 0 : type.hashCode());
		hash = hash * PRIME + ((description == null) ? 0 : description.hashCode());
		
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Role other = (Role) obj;
		if (other.getId() == null)
			return false;
		else if (other.getId() != this.id)
			return false;
		else
			return true;
	}

	@Override
	public int compareTo(Role o) {
		if (!this.name.equals(o.getName())) {
			return this.name.compareTo(o.getName());
		}
		return 0;
	}
}
