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
public class BroadcastMessage extends InstantMessage {
	private static final long serialVersionUID = 1L;

	private long sourceId;
	private long circleId;
	private String text;
	
	public BroadcastMessage(Date messageTime, String content, long sourceId, long circleId) {
		super(messageTime, MessageType.BROADCAST);
		// TODO Auto-generated constructor stub
		
		this.sourceId = sourceId;
		this.circleId = circleId;		
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
	 * @return the circleId
	 */
	public long getCircleId() {
		return circleId;
	}

	/**
	 * @param circleId the circleId to set
	 */
	public void setCircleId(long circleId) {
		this.circleId = circleId;
	}
}
