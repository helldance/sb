package com.coordsafe.notification.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import com.coordsafe.ward.entity.Ward;


@Entity
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
@JsonIgnoreProperties(value= {"ward", "create", "createBy", "description"})
public class WardNotificationSetting extends AbstractNotificationSetting{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Ward ward;

	public Ward getWard() {
		return ward;
	}

	public void setWard(Ward ward) {
		this.ward = ward;
	}
	

}
