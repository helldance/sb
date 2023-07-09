package com.coordsafe.remote.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.coordsafe.guardian.entity.Guardian;

/**
 * @author Yang Wei
 * @Date Feb 3, 2014
 */
@Entity
@JsonIgnoreProperties({"guardian"})
public class IosDeviceToken {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long id;
	public String token;
	
	@ManyToOne
	public Guardian guardian;
	
	/**
	 * @param token
	 */
	public IosDeviceToken(String token, Guardian guardian) {
		super();
		this.token = token;
		this.guardian = guardian;
	}
	/**
	 * 
	 */
	public IosDeviceToken() {
		super();
		// TODO Auto-generated constructor stub
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
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the guardian
	 */
	public Guardian getGuardian() {
		return guardian;
	}
	/**
	 * @param guardian the guardian to set
	 */
	public void setGuardian(Guardian guardian) {
		this.guardian = guardian;
	}	
	
}
