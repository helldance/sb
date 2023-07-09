package com.coordsafe.notification.entity;

import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.OneToOne;
import org.hibernate.annotations.DynamicUpdate;

import com.coordsafe.company.entity.Company;



@Entity
@DynamicUpdate(value = true)
public class NotificationSetting extends AbstractNotificationSetting implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@OneToOne
	private Company company;
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
