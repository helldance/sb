/**
 * 
 */
package com.coordsafe.api.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.coordsafe.api.entity.ApiKey;

/**
 * @author Yang Wei
 *
 */
@Entity
public class ApiRequest {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	private ApiKey key;
	
	private String resource;
	private String referrer;
	private String agent;
	private String reqFrom;
	private Date reqDt;
	private String result;
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
	 * @return the resource
	 */
	public String getResource() {
		return resource;
	}
	/**
	 * @param resource the resource to set
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}
	/**
	 * @return the referrer
	 */
	public String getReferrer() {
		return referrer;
	}
	/**
	 * @param referrer the referrer to set
	 */
	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}
	/**
	 * @return the agent
	 */
	public String getAgent() {
		return agent;
	}
	/**
	 * @param agent the agent to set
	 */
	public void setAgent(String agent) {
		this.agent = agent;
	}
	/**
	 * @return the reqDt
	 */
	public Date getReqDt() {
		return reqDt;
	}
	/**
	 * @param reqDt the reqDt to set
	 */
	public void setReqDt(Date reqDt) {
		this.reqDt = reqDt;
	}
	/**
	 * @return the reqFrom
	 */
	public String getReqFrom() {
		return reqFrom;
	}
	/**
	 * @param reqFrom the reqFrom to set
	 */
	public void setReqFrom(String reqFrom) {
		this.reqFrom = reqFrom;
	}
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
}
