/**
 * @author Yang Wei
 * @Date Nov 11, 2013
 */
package com.coordsafe.im.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author Yang Wei
 *
 */
@MappedSuperclass
public abstract class InstantMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Date messageTime;
	//private byte [] content;
	@Enumerated(EnumType.STRING)
	private MessageType type;

	/**
	 * 
	 */
	public InstantMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param messageTime
	 * @param content
	 */
	public InstantMessage(Date messageTime, MessageType type) {
		super();
		
		this.messageTime = messageTime;
		this.type = type;
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
	 * @return the messageTime
	 */
	public Date getMessageTime() {
		return messageTime;
	}
	/**
	 * @param messageTime the messageTime to set
	 */
	public void setMessageTime(Date messageTime) {
		this.messageTime = messageTime;
	}

	/**
	 * @return the type
	 */
	public MessageType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(MessageType type) {
		this.type = type;
	}
		
}
