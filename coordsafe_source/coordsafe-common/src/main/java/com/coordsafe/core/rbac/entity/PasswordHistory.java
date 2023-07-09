package com.coordsafe.core.rbac.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import com.coordsafe.core.rbac.entity.PasswordHistory;

@SuppressWarnings("serial")
@XmlRootElement
@Entity
public class PasswordHistory implements Serializable, Comparable<PasswordHistory> {

	/**
	 * Fields
	 */
	
	private Long id;
	private String password;
	private Date createdDate;
	
	/**
	 * Constructors
	 */
	
	public PasswordHistory() {
		super();
	}

	public PasswordHistory(Long id, String password,
			Date createdDate) {
		super();
		this.id = id;
		this.password = password;
		this.createdDate = createdDate;
	}

	/**
	 * Getters and Setters
	 * Persisted Fields
	 */

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Overridden methods
	 */

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int hash = 1;
		
		hash = hash * PRIME + ((id == null) ? 0 : id.hashCode());
		hash = hash * PRIME + ((password == null) ? 0 : password.hashCode());
		hash = hash * PRIME + ((createdDate == null) ? 0 : createdDate.hashCode());
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
		final PasswordHistory other = (PasswordHistory) obj;
		if (other.getId() == null)
			return false;
		else if (other.getId() != this.id)
			return false;
		else
			return true;
	}

	@Override
	public int compareTo(PasswordHistory o) {
		if (!this.createdDate.equals(o.getCreatedDate())) {
			return this.createdDate.compareTo(o.getCreatedDate());
		}
		
		return 0;
	}
}
