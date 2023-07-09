package com.coordsafe.guardian.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.coordsafe.api.entity.ApiKey;
import com.coordsafe.common.entity.LatLng;
import com.coordsafe.company.entity.Company;
import com.coordsafe.safedistance.entity.Safedistance;
import com.coordsafe.ward.entity.Ward;

@Entity
public class Guardian implements java.io.Serializable{

	private static final long serialVersionUID = -5527566248002296042L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "LOGIN", unique=true)
	private String login;

	@Column(name = "PASSWD")
	private String passwd;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PHONE")
	private String phone;

	@Column(name = "ROLE")
	private String role;
	
	@Column
	private int retryCount = 0;
	
	@Column
	private Date lastLoginDt;
	
	@Column(name = "LOCKED")
    private boolean accountNonLocked;
	@Column(name = "EXPIRED")
    private boolean accountNonExpired;
	@Column(name = "ENABLED")
    private boolean enabled;
	
	private String uuidpwd;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Ward> wards;
	
	@Transient
	private String confirmPassword;
	//social column
    /*@Enumerated(EnumType.STRING)
    @Column(name = "socialrole", length = 20)
    private Role socialrole;

    @Enumerated(EnumType.STRING)
    @Column(name = "sign_in_provider", length = 20)
    private SocialMediaService signInProvider;*/
	
	@ManyToOne
	private Company company;
    
    @OneToOne
    private ApiKey key;
    
    private int zoneLimit = 0;
    
    @Embedded
    private LatLng location;
    
    @OneToOne
    private Safedistance safedistance; 
    
	/**
	 * 
	 */
	public Guardian() {
		super();
		// TODO Auto-generated constructor stub
		
		zoneLimit = retryCount = 0;
	}
	
	

	/**
	 * @param login
	 * @param email
	 * @param phone
	 */
	public Guardian(String login, String email, String phone) {
		super();
		
		this.login = login;
		this.email = email;
		this.phone = phone;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Ward> getWards() {
		return wards == null? new HashSet<Ward>() : wards;
	}

	public void setWards(Set<Ward> wards) {
		this.wards = wards;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getUuidpwd() {
		return uuidpwd;
	}

	public void setUuidpwd(String uuidpwd) {
		this.uuidpwd = uuidpwd;
	}

	/**
	 * @return the key
	 */
	public ApiKey getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(ApiKey key) {
		this.key = key;
	}

	/**
	 * @return the retryCount
	 */
	public int getRetryCount() {
		return retryCount;
	}

	/**
	 * @param retryCount the retryCount to set
	 */
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	/**
	 * @return the lastLoginDt
	 */
	public Date getLastLoginDt() {
		return lastLoginDt;
	}

	/**
	 * @param lastLoginDt the lastLoginDt to set
	 */
	public void setLastLoginDt(Date lastLoginDt) {
		this.lastLoginDt = lastLoginDt;
	}

	/**
	 * @return the zoneLimit
	 */
	public int getZoneLimit() {
		return zoneLimit;
	}

	/**
	 * @param zoneLimit the zoneLimit to set
	 */
	public void setZoneLimit(int zoneLimit) {
		this.zoneLimit = zoneLimit;
	}
	
	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}



	/**
	 * @return the location
	 */
	public LatLng getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(LatLng location) {
		this.location = location;
	}


	/**
	 * @return the safedistance
	 */
	public Safedistance getSafedistance() {
		return safedistance;
	}

	/**
	 * @param safedistance the safedistance to set
	 */
	public void setSafedistance(Safedistance safedistance) {
		this.safedistance = safedistance;
	}

}
