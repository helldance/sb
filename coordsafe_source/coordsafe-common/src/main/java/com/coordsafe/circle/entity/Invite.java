/**
 * @author Yang Wei
 * @Date Oct 25, 2013
 */
package com.coordsafe.circle.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.coordsafe.guardian.entity.Guardian;

/**
 * @author Yang Wei
 *
 */
@Entity
public class Invite {
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;
	
	@ManyToOne
	private Circle circle;
	
	/*@OneToOne
	private Guardian inviteMemeber;*/
	private InviteChannel channel;
	
	private String inviteToken;
	
	private boolean approved;
	
	private Date inviteDt;
	
	@ManyToOne
	private Guardian inviteBy;
	
	private Date approvalDt;
	
	//private Guardian approveBy;

	/**
	 * @param id
	 * @param circle
	 * @param channel
	 * @param inviteToken
	 * @param inviteBy
	 */
	public Invite(long id, Circle circle, InviteChannel channel,
			String inviteToken, Guardian inviteBy) {
		super();
		Id = id;
		this.circle = circle;
		this.channel = channel;
		this.inviteToken = inviteToken;
		this.inviteBy = inviteBy;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return Id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		Id = id;
	}

	/**
	 * @return the channel
	 */
	public InviteChannel getChannel() {
		return channel;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(InviteChannel channel) {
		this.channel = channel;
	}

	/**
	 * @return the inviteToken
	 */
	public String getInviteToken() {
		return inviteToken;
	}

	/**
	 * @param inviteToken the inviteToken to set
	 */
	public void setInviteToken(String inviteToken) {
		this.inviteToken = inviteToken;
	}

	/**
	 * @return the circle
	 */
	public Circle getCircle() {
		return circle;
	}

	/**
	 * @param circle the circle to set
	 */
	public void setCircle(Circle circle) {
		this.circle = circle;
	}
	
	/**
	 * @return the approval
	 */
	public boolean isApproved() {
		return approved;
	}

	/**
	 * @param approval the approval to set
	 */
	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	/**
	 * @return the inviteDt
	 */
	public Date getInviteDt() {
		return inviteDt;
	}

	/**
	 * @param inviteDt the inviteDt to set
	 */
	public void setInviteDt(Date inviteDt) {
		this.inviteDt = inviteDt;
	}

	/**
	 * @return the inviteBy
	 */
	public Guardian getInviteBy() {
		return inviteBy;
	}

	/**
	 * @param inviteBy the inviteBy to set
	 */
	public void setInviteBy(Guardian inviteBy) {
		this.inviteBy = inviteBy;
	}

	/**
	 * @return the approvalDt
	 */
	public Date getApprovalDt() {
		return approvalDt;
	}

	/**
	 * @param approvalDt the approvalDt to set
	 */
	public void setApprovalDt(Date approvalDt) {
		this.approvalDt = approvalDt;
	}
}
