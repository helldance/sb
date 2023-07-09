package com.coordsafe.core.rbac.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.coordsafe.api.entity.ApiKey;
import com.coordsafe.core.security.CoordsafeGrantedAuthority;
import com.coordsafe.company.entity.Company;
import com.coordsafe.core.rbac.entity.Group;
import com.coordsafe.core.rbac.entity.IUserDetails;
import com.coordsafe.core.rbac.entity.Organization;
import com.coordsafe.core.rbac.entity.PasswordHistory;
import com.coordsafe.core.rbac.entity.Role;
import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.entity.UserInformation;

@XmlRootElement
@Entity
public class User implements Serializable, IUserDetails, Comparable<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * Fields
	 */

	private Long id;
	private String username;
	private String password;
	private String confirmPassword; // Transient use for confirming password
	private String oldPassword; // Transient use for changing password
	private String salt;
	private Date passwordLastModDate;
	private String contact;
	private Long retryCount;
	private String email;
	private String nickName;
	private String langId;
	private Date lastLoginDate;
	private String termCond;
	private boolean enabled;
	private String remark;
	private String companyName;
	private Date createdDate;
	private Date lastModDate;
	
	private Company company;
	private Set<Role> roles = new HashSet<Role>();
	private Set<Organization> organizations = new HashSet<Organization>();
	private Set<PasswordHistory> passwordHistory = new HashSet<PasswordHistory>();
	private Set<Group> groups = new HashSet<Group>();
	private UserInformation userInformation;
	
	private ApiKey apiKey;

	/*
	 * Constructors
	 */

	public User() {
		super();
	}

	public User(Long id, String username, String password,
			String confirmPassword, String salt, Date passwordLastModDate,
			String contact, Long retryCount, String email, String nickName,
			String langId, Date lastLoginDate, String termCond,
			boolean enabled, String remark, String companyName,
			Date createdDate, Date lastModDate, Set<Role> roles,
			Set<Organization> organizations,
			Set<PasswordHistory> passwordHistory, Set<Group> groups,
			UserInformation userInformation,Company company) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.salt = salt;
		this.passwordLastModDate = passwordLastModDate;
		this.contact = contact;
		this.retryCount = retryCount;
		this.email = email;
		this.nickName = nickName;
		this.langId = langId;
		this.lastLoginDate = lastLoginDate;
		this.termCond = termCond;
		this.enabled = enabled;
		this.remark = remark;
		this.companyName = companyName;
		this.createdDate = createdDate;
		this.lastModDate = lastModDate;
		this.roles = roles;
		this.organizations = organizations;
		this.passwordHistory = passwordHistory;
		this.groups = groups;
		this.userInformation = userInformation;
		this.company = company;
	}

	/*
	 * Getters and Setters Persisted Fields
	 */

	@Override
	@Id

	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getPasswordLastModDate() {
		return passwordLastModDate;
	}

	public void setPasswordLastModDate(Date passwordLastModDate) {
		this.passwordLastModDate = passwordLastModDate;
	}

	@Size(max=40)
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@ManyToOne
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Size(max=40)
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Size(max=40)
	public String getLangId() {
		return langId;
	}

	public void setLangId(String langId) {
		this.langId = langId;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	@Size(max=10)
	public String getTermCond() {
		return termCond;
	}

	public void setTermCond(String termCond) {
		this.termCond = termCond;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Size(max=40)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModDate() {
		return lastModDate;
	}

	public void setLastModDate(Date lastModDate) {
		this.lastModDate = lastModDate;
	}

	// Many to many relationship with Organization
	@JsonManagedReference("organizaions")
	@ManyToMany(fetch = FetchType.EAGER)
	public Set<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(Set<Organization> organizations) {
		this.organizations = organizations;
	}

	// Many to many relationship with Role
	@JsonManagedReference("roles")
	@ManyToMany(fetch = FetchType.EAGER)
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Size(min=3, max=40)
	@Pattern(regexp="^[a-zA-Z0-9_]+$")
	@NotBlank
	@Column(unique = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Size(min=6)
	@NotBlank
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Size(max=40)
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Column(precision = 22, scale = 0)
	public Long getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Long retryCount) {
		this.retryCount = retryCount;
	}

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	@OrderBy("createdDate desc")
	@JoinColumn
	public Set<PasswordHistory> getPasswordHistory() {
		return passwordHistory;
	}

	public void setPasswordHistory(Set<PasswordHistory> passwordHistory) {
		this.passwordHistory = passwordHistory;
	}

	@XmlInverseReference(mappedBy = "users")
	@JsonBackReference("users")
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
	public Set<Group> getGroups() {
		return groups;
	}
	
	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	@OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
	public UserInformation getUserInformation() {
		return userInformation;
	}

	public void setUserInformation(UserInformation userInformation) {
		this.userInformation = userInformation;
	}

	/*
	 * Transient fields
	 */
	@Transient
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	/**
	 * @return the apiKey
	 */
	@OneToOne
	public ApiKey getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey the apiKey to set
	 */
	
	public void setApiKey(ApiKey apiKey) {
		this.apiKey = apiKey;
	}

	/*
	 * Check whether the current operation is adding a new user.
	 */
	@Transient
	public boolean isCreate() {
		return (this.id == null);
	}
	
	/*
	 * Methods overriding the spring security UserDetails
	 */
	@Override
	@Transient
	public Collection<CoordsafeGrantedAuthority> getAuthorities() {
		Collection<CoordsafeGrantedAuthority> roleArray = new ArrayList<CoordsafeGrantedAuthority>();

		for (Role role : this.getRoles()) {
			roleArray.add(role);
		}
		
		// Group roles included. 
		for (Group group : this.getGroups()) {
			roleArray.addAll(group.getRoles());
		}
		
		return roleArray;
	}

	@Override
	@Transient
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@Transient
	public boolean isAccountNonLocked() {
		return enabled;
	}

	@Override
	@Transient
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@Transient
	public Date getLastLogin() {
		return null;
	}
	
	@Transient
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}


	// End spring security overriding.

	/*
	 * Overridden Object Methods
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int hash = 1;
		
		hash = hash * PRIME + ((id == null) ? 0 : id.hashCode());
		hash = hash * PRIME + ((username == null) ? 0 : username.hashCode());
		hash = hash * PRIME + ((password == null) ? 0 : password.hashCode());
		hash = hash * PRIME + ((contact == null) ? 0 : contact.hashCode());
		hash = hash * PRIME + ((email == null) ? 0 : email.hashCode());
		hash = hash * PRIME + ((nickName == null) ? 0 : nickName.hashCode());
		hash = hash * PRIME + ((companyName == null) ? 0 : companyName.hashCode());
		
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
		final User other = (User) obj;
		if (other.getId() == null)
			return false;
		else if (other.getId() != this.id)
			return false;
		else
			return true;
	}

	@Override
	public int compareTo(User o) {
		if (!this.username.equals(o.getUsername())) {
			return this.username.compareTo(o.getUsername());
		}
		return 0;
	}


}
