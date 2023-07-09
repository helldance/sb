/**
 * @author Yang Wei
 * @Date Nov 11, 2013
 */
package com.coordsafe.im.entity;

import java.util.Date;

import javax.persistence.Entity;

/**
 * @author Yang Wei
 *
 */
@Entity
public class ChatMessage extends InstantMessage {
	private static final long serialVersionUID = 1L;
	
	private long sourceId;
	private long destId;
	private String text;
	
	public ChatMessage(){}
	
	/**
	 * @param sourceId
	 * @param destId
	 */
	public ChatMessage(String text, Date time, long sourceId, long destId) {
		super(time, MessageType.CHAT);

		this.sourceId = sourceId;
		this.destId = destId;
		this.text = text;
	}
	
	/**
	 * @return the sourceId
	 */
	public long getSourceId() {
		return sourceId;
	}
	/**
	 * @param sourceId the sourceId to set
	 */
	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}
	/**
	 * @return the destId
	 */
	public long getDestId() {
		return destId;
	}
	/**
	 * @param destId the destId to set
	 */
	public void setDestId(long destId) {
		this.destId = destId;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

}
