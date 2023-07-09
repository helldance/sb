package com.coordsafe.core.rbac.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotBlank;

import com.coordsafe.core.rbac.entity.Group;
import com.coordsafe.core.rbac.entity.Organization;
import com.coordsafe.core.rbac.entity.User;

@SuppressWarnings("serial")
@XmlRootElement
@Entity
public class Organization implements Serializable, Comparable<Organization> {

	/*
	 * Fields
	 */

	private Long id;
	private Organization parentOrganization;
	private Set<Organization> childOrganizations = new HashSet<Organization>();
	private String name;
	private Boolean active;
	private String description;
	private Date createdDate;
	private String createBy;
	private Date lastModDate;
	private String lastModBy;
	private Integer version;
	private String deleteInd;
	private Set<User> users = new HashSet<User>();
	private Set<Group> groups = new HashSet<Group>();

	/*
	 * Constructors
	 */

	public Organization() {
		super();
	}

	public Organization(Long id, Organization parentOrganization,
			Set<Organization> childOrganizations, String name, Boolean active,
			String description, Date createdDate, String createBy,
			Date lastModDate, String lastModBy, Integer version,
			String deleteInd, Set<User> users, Set<Group> groups) {
		super();
		this.id = id;
		this.parentOrganization = parentOrganization;
		this.childOrganizations = childOrganizations;
		this.name = name;
		this.active = active;
		this.description = description;
		this.createdDate = createdDate;
		this.createBy = createBy;
		this.lastModDate = lastModDate;
		this.lastModBy = lastModBy;
		this.version = version;
		this.deleteInd = deleteInd;
		this.users = users;
		this.groups = groups;
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

	@JsonManagedReference("parentOrganization")
	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	public Organization getParentOrganization() {
		return parentOrganization;
	}

	public void setParentOrganization(Organization parentOrganization) {
		this.parentOrganization = parentOrganization;
	}
	public boolean hasParentOrganization(){
		if( parentOrganization.getName().equals("") || parentOrganization.getName() == null)
			return false;
		return true;
	}
	@XmlInverseReference(mappedBy = "parentOrganization")
	@JsonBackReference("parentOrganization")
	@OneToMany(mappedBy = "parentOrganization")
	@Cascade(CascadeType.SAVE_UPDATE)
	public Set<Organization> getChildOrganizations() {
		return childOrganizations;
	}

	public void setChildOrganizations(Set<Organization> childOrganizations) {
		this.childOrganizations = childOrganizations;
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

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Size(max = 40)
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getLastModDate() {
		return lastModDate;
	}

	public void setLastModDate(Date lastModDate) {
		this.lastModDate = lastModDate;
	}

	@Size(max = 40)
	public String getLastModBy() {
		return lastModBy;
	}

	public void setLastModBy(String lastModBy) {
		this.lastModBy = lastModBy;
	}

	@Max(10)
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Size(max = 2)
	public String getDeleteInd() {
		return deleteInd;
	}

	public void setDeleteInd(String deleteInd) {
		this.deleteInd = deleteInd;
	}

	// Many to many relationship with User
	@XmlInverseReference(mappedBy = "organizations")
	@JsonBackReference("organizations")
	@ManyToMany(mappedBy = "organizations")
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}


	// Many to many relationship with Group
	@XmlInverseReference(mappedBy = "organizations")
	@JsonBackReference("organizations")
	@ManyToMany(mappedBy = "organizations")
	public Set<Group> getGroups() {
		return groups;
	}
	
	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}
	
	/*
	 * Transient Fields
	 */
	
	/*
	 * Check whether the current operation is adding a new organization.
	 */
	@Transient
	public boolean isCreate() {
		return (this.id == null);
	}

	/*
	 * Overrriden methods
	 */

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int hash = 1;
		
		hash = hash * PRIME + ((id == null) ? 0 : id.hashCode());
		hash = hash * PRIME + ((name == null) ? 0 : name.hashCode());
		hash = hash * PRIME + ((description == null) ? 0 : description.hashCode());
		hash = hash * PRIME + ((createdDate == null) ? 0 : createdDate.hashCode());
		hash = hash * PRIME + ((createBy == null) ? 0 : createBy.hashCode());
		hash = hash * PRIME + ((lastModDate == null) ? 0 : lastModDate.hashCode());
		hash = hash * PRIME + ((lastModBy == null) ? 0 : lastModBy.hashCode());
		
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
		final Organization other = (Organization) obj;
		if (other.getId() == null)
			return false;
		else if (other.getId() != this.id)
			return false;
		else
			return true;
	}

	@Override
	public int compareTo(Organization o) {
		if (!this.name.equals(o.getName())) {
			return this.name.compareTo(o.getName());
		}
		return 0;
	}

}
