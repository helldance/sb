/**
 * ApiKey.java
 * Apr 2, 2013
 * Yang Wei
 */
package com.coordsafe.api.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Yang Wei
 *
 */
@Entity
public class ApiKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String key;
	private String appName;
	private int type; // 0: web, 1: app
	private String domain;
	private String email;
	private int count;
	private Date lastReqDt;
	private String lastReqFrom;
	private Date createDt;
	private Date validUntilDt;
	private boolean valid;
	private boolean loggedIn;
	/**
	 * @param key
	 * @param appName
	 * @param domain
	 * @param email
	 */
	public ApiKey(String key, String appName, int type, String domain, String email) {
		super();
		this.key = key;
		this.appName = appName;
		this.type = type;
		this.domain = domain;
		this.email = email;
		this.count = 0;
		this.valid = true;
		Date now = new Date();
		this.createDt = now;
		this.validUntilDt = new Date(now.getTime() + 365*24*3600*1000);
		this.loggedIn = false; 
	}
	/**
	 * 
	 */
	public ApiKey() {
		super();
		this.valid = true;
		Date now = new Date();
		this.createDt = now;
		this.validUntilDt = new Date(now.getTime() + 365*24*3600*1000);
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}
	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}
	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * @return the lastReqDt
	 */
	public Date getLastReqDt() {
		return lastReqDt;
	}
	/**
	 * @param lastReqDt the lastReqDt to set
	 */
	public void setLastReqDt(Date lastReqDt) {
		this.lastReqDt = lastReqDt;
	}
	/**
	 * @return the lastReqFrom
	 */
	public String getLastReqFrom() {
		return lastReqFrom;
	}
	/**
	 * @param lastReqFrom the lastReqFrom to set
	 */
	public void setLastReqFrom(String lastReqFrom) {
		this.lastReqFrom = lastReqFrom;
	}
	/**
	 * @return the createDt
	 */
	public Date getCreateDt() {
		return createDt;
	}
	/**
	 * @param createDt the createDt to set
	 */
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	/**
	 * @return the validUntilDt
	 */
	public Date getValidUntilDt() {
		return validUntilDt;
	}
	/**
	 * @param validUntilDt the validUntilDt to set
	 */
	public void setValidUntilDt(Date validUntilDt) {
		this.validUntilDt = validUntilDt;
	}
	/**
	 * @return the valid
	 */
	public boolean isValid() {
		return valid;
	}
	/**
	 * @param valid the valid to set
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	/**
	 * @return the loggedIn
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}
	/**
	 * @param loggedIn the loggedIn to set
	 */
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ApiKey [id=" + id + ", key=" + key + ", appName=" + appName
				+ ", type=" + type + ", domain=" + domain + ", email=" + email
				+ ", count=" + count + ", lastReqDt=" + lastReqDt
				+ ", lastReqFrom=" + lastReqFrom + ", createDt=" + createDt
				+ ", validUntilDt=" + validUntilDt + ", valid=" + valid + "]";
	}
	
}
