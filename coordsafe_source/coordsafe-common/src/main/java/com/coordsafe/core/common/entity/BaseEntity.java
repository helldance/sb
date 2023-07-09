package com.coordsafe.core.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

//import com.stee.ers.common.ErsSessionUtil;

/**
 * base class intended to be used in all the domain classes which have a common 
 * set of columns, and use hibernate or JPA as persistence layer.
 *  
 * @author Daren Mok
 *
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
	
	public BaseEntity() {
		Date date = new Date();
		String userId = "";  //ErsSessionUtil.getUserId();
		this.createdDate = date;
		this.creator = userId;
		this.updatedDate = date;
		this.updater = userId;
		this.id = "{" + UUID.randomUUID().toString() +"}";
	}

	/**
	 * default serial version ID
	 */
	@Transient
	private static final long serialVersionUID = 1L;

	/**
	 * shall we use GUID as the value for the ID field? it is support by some
	 * big database such as Oracle, MySQL etc. There will be problem to do unit
	 * testing with GUID strategy due to embedded Database does not have GUID
	 * capability build in. 
	*/
	@Id
//	@GeneratedValue(generator="GUIDGenerator")
//	@GenericGenerator(name="GUIDGenerator", strategy="com.stee.ers.common.entity.ErsGUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false, length=38)
	protected String id = null;

	@Version
	@Column(name = "version")
	protected int version = 0;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", updatable=false)
	protected Date createdDate;

	@Column(name = "created_by", updatable=false)
	@Size(max=64)
	protected String creator;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_date")
	protected Date updatedDate;

	@Column(name = "updated_by")
	@Size(max=64)
	protected String updater;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		if(id != null && !id.isEmpty()) {
			this.id = id;
		}
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = new Date();
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		//this.updater = ErsSessionUtil.getUserId();
	}
	
	public void updateModifier() {
		this.updatedDate = new Date();
		//this.updater = ErsSessionUtil.getUserId();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Method to get a String representing the current state of the java content
	 * tree for the message.
	 * 
	 * @return string of fields listed within the content tree.
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}

