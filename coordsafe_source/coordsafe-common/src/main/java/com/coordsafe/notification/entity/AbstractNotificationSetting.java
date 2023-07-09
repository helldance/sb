package com.coordsafe.notification.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AbstractNotificationSetting implements Serializable{



	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String eventType;
	
	@Column(nullable=true)
	private boolean emailEnable;
	@Column(nullable=true)
	private boolean smsEnable;
	private String emailaddress;
	private String mobile;
	private boolean appPush = false;
	private String description;
	private String createBy;

	/*
	 * Constructors
	 */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public boolean isSmsEnable() {
		return smsEnable;
	}

	public void setSmsEnable(boolean smsEnable) {
		this.smsEnable = smsEnable;
	}

	public boolean isEmailEnable() {
		return emailEnable;
	}

	public void setEmailEnable(boolean emailEnable) {
		this.emailEnable = emailEnable;
	}

	/*
	 * Transient Fields
	 */
	

	/**
	 * @return the appPush
	 */
	public boolean isAppPush() {
		return appPush;
	}

	/**
	 * @param appPush the appPush to set
	 */
	public void setAppPush(boolean appPush) {
		this.appPush = appPush;
	}

	/*
	 * Check whether the current operation is adding a new organization.
	 */
	
	@Transient
	private boolean create;

	public boolean isCreate() {
		return (this.id == null);
	}
}
