package com.coordsafe.httpgateway.messaging;

import java.io.Serializable;
import java.util.Date;
import java.util.Calendar;

public class MobileEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String entityId;
	private String userId;
	private String groupId;
	private ChannelType channel = ChannelType.TCPIP;
	private String channelRefNo;
	private Date lastUpdTime;
	
	public MobileEntity() {
		lastUpdTime = new Date();
	}
	
	public MobileEntity(String entityId, String userId, String groupId) {
		this.entityId = entityId;
		this.userId = userId;
		this.groupId = groupId;
		this.lastUpdTime = new Date();
	}
	
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public ChannelType getChannel() {
		return channel;
	}
	public void setChannel(ChannelType channel) {
		this.channel = channel;
	}
	public String getChannelRefNo() {
		return channelRefNo;
	}
	public void setChannelRefNo(String channelRefNo) {
		this.channelRefNo = channelRefNo;
	}
	public Date getLastUpdTime() {
		return lastUpdTime;
	}
	public void setLastUpdTime(Date lastUpdTime) {
		this.lastUpdTime = lastUpdTime;
	}
	
	public void touch() {
		this.lastUpdTime = new Date();
	}
	
	public String toString() {
		return "Entity Id: " + this.entityId + 
			"\nUser Id: " + this.userId +
			"\nGroup Id: " + this.groupId;
	}
}

