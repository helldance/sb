package com.coordsafe.core.rbac.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotBlank;

import com.coordsafe.core.rbac.entity.Permission;
import com.coordsafe.core.rbac.entity.Resource;
import com.coordsafe.core.rbac.entity.Role;

@SuppressWarnings("serial")
@XmlRootElement
@Entity
public class Permission implements Serializable, Comparable<Permission> {

	/*
	 * Fields
	 */

	private Long id;
	private String name;
	private String description;
	private Resource resource;
	private Set<Role> roles = new HashSet<Role>();

	/*
	 * Constructors
	 */

	public Permission() {
		super();
	}

	public Permission(Long id, String name, Resource resource, String description,
			Set<Role> roles) {
		super();
		this.id = id;
		this.name = name;
		this.resource = resource;
		this.description = description;
		this.roles = roles;
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

	@Size(min = 2, max = 40, message = "Permission name must be between 2 and 40 characters long.")
	@NotBlank(message = "Permission name must be provided.")
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

	@JsonManagedReference("resource")
	@ManyToOne
	@Cascade(CascadeType.ALL)
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	// Many to many relationship with Role
	@XmlInverseReference(mappedBy = "permissions")
	@JsonBackReference("permissions")
	@ManyToMany(mappedBy = "permissions")
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/*
	 * Overridden methods
	 */

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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Permission other = (Permission) obj;
		if (other.getId() == null)
			return false;
		else if (other.getId() != this.id)
			return false;
		else
			return true;
	}

	@Override
	public int compareTo(Permission o) {
		if (!this.name.equals(o.getName())) {
			return this.name.compareTo(o.getName());
		}
		
		return 0;
	}
}
