/**
 * Receiver.java
 * May 10, 2013
 * Yang Wei
 */
package com.coordsafe.event.entity;

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
public class Recipient {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public long id;
	public String userId;
	public int channelType;
	public String channelInfo;
	public boolean availability;
	public Date createDt;
}
