package com.coordsafe.locator.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Device {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long id;
	/*protected String type;*/
	protected String model;
	protected String label;
	/*protected String status;*/
	protected String madeBy;
	protected Date madeDate;
	/*protected String ownerId;*/
	
	protected Date lastLocationUpdate;
	protected Date lastStatusUpdate;
	protected boolean spoiled;
	
	public Device() {
		
	}
	
	public Device(Long id, String model, String label, String madeBy, Date madeDate) {
		this.id = id;
		/*this.type = type;*/
		this.label = label;
		this.model = model;
		/*this.status = status;*/
		this.madeBy = madeBy;
		this.madeDate = madeDate;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the types
	 *//*
	public String getType() {
		return type;
	}

	*//**
	 * @param type
	 *            the type to set
	 *//*
	public void setType(String type) {
		this.type = type;
	}*/

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}


	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the status
	 *//*
	public String getStatus() {
		return status;
	}

	*//**
	 * @param status
	 *            the status to set
	 *//*
	public void setStatus(String status) {
		this.status = status;
	}*/

	/**
	 * @return the madeBy
	 */
	public String getMadeBy() {
		return madeBy;
	}

	/**
	 * @param madeBy
	 *            the madeBy to set
	 */
	public void setMadeBy(String madeBy) {
		this.madeBy = madeBy;
	}

	/**
	 * @return the madeDate
	 */
	public Date getMadeDate() {
		return madeDate;
	}

	/**
	 * @return the lastLocationUpdate
	 */
	public Date getLastLocationUpdate() {
		return lastLocationUpdate;
	}

	/**
	 * @param lastLocationUpdate the lastLocationUpdate to set
	 */
	public void setLastLocationUpdate(Date lastLocationUpdate) {
		this.lastLocationUpdate = lastLocationUpdate;
	}

	/**
	 * @return the lastStatusUpdate
	 */
	public Date getLastStatusUpdate() {
		return lastStatusUpdate;
	}

	/**
	 * @param lastStatusUpdate the lastStatusUpdate to set
	 */
	public void setLastStatusUpdate(Date lastStatusUpdate) {
		this.lastStatusUpdate = lastStatusUpdate;
	}

	/**
	 * @param madeDate
	 *            the madeDate to set
	 */
	public void setMadeDate(Date madeDate) {
		this.madeDate = madeDate;
	}

	/*public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}*/

	/**
	 * @return the spoiled
	 */
	public boolean isSpoiled() {
		return spoiled;
	}

	/**
	 * @param spoiled the spoiled to set
	 */
	public void setSpoiled(boolean spoiled) {
		this.spoiled = spoiled;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	/*@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result
				+ ((lastUpdate == null) ? 0 : lastUpdate.hashCode());
		result = prime * result + ((madeBy == null) ? 0 : madeBy.hashCode());
		result = prime * result
				+ ((madeDate == null) ? 0 : madeDate.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((ownerId == null) ? 0 : ownerId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}*/

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/*@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Device other = (Device) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (lastUpdate == null) {
			if (other.lastUpdate != null)
				return false;
		} else if (!lastUpdate.equals(other.lastUpdate))
			return false;
		if (madeBy == null) {
			if (other.madeBy != null)
				return false;
		} else if (!madeBy.equals(other.madeBy))
			return false;
		if (madeDate == null) {
			if (other.madeDate != null)
				return false;
		} else if (!madeDate.equals(other.madeDate))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (ownerId == null) {
			if (other.ownerId != null)
				return false;
		} else if (!ownerId.equals(other.ownerId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}*/
}
