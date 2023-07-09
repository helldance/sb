package com.coordsafe.core.rbac.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;
import org.hibernate.validator.constraints.NotBlank;

import com.coordsafe.core.rbac.entity.Permission;
import com.coordsafe.core.rbac.entity.Resource;

@SuppressWarnings("serial")
@XmlRootElement
@Entity
public class Resource implements Serializable, Comparable<Resource> {

	/*
	 * Fields
	 */

	private Long id;
	private String name;
	private String type;
	private String value;
	private String description;
	private Set<Permission> permissions = new HashSet<Permission>();

	/*
	 * Constructors
	 */

	public Resource() {
		super();
	}

	public Resource(Long id, String name, String type, String value,
			String description, Set<Permission> permissions) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.value = value;
		this.description = description;
		this.permissions = permissions;
	}

	/*
	 * Getters and Setters Persisted fields
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
	@Column(unique = true)
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

	@Size(max = 40)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlInverseReference(mappedBy = "resource")
	@JsonBackReference("resource")
	@OneToMany(mappedBy = "resource", orphanRemoval = true)
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
	 * Check whether the current operation is adding a new resource.
	 */
	@Transient
	public boolean isCreate() {
		return (this.id == null);
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
		hash = hash * PRIME + ((type == null) ? 0 : type.hashCode());
		hash = hash * PRIME + ((value == null) ? 0 : value.hashCode());
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
		final Resource other = (Resource) obj;
		if (other.getId() == null)
			return false;
		else if (other.getId() != this.id)
			return false;
		else
			return true;
	}

	@Override
	public int compareTo(Resource o) {
		if (!this.name.equals(o.getName())) {
			return this.name.compareTo(o.getName());
		}
		
		return 0;
	}
}
