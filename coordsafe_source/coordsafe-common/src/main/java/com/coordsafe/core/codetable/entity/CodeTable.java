package com.coordsafe.core.codetable.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotBlank;

import com.coordsafe.core.codetable.entity.CodeTable;

@SuppressWarnings("serial")
@XmlRootElement
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"type", "code"})})
public class CodeTable implements Serializable {

	/*
	 * Fields
	 */

	private Long id;
	private String type;
	private String code;
	private String description;
	private Date startDate;
	private Date endDate;
	private CodeTable parentCode;
	private Set<CodeTable> childCodes = new HashSet<CodeTable>();
	private String remark;
	private Date createdDate;
	private Date lastModDate;
	private String createdBy;
	private String lastModBy;
	private Date archiveDate;
	private Boolean deleteIndicator;
	private Integer version;

	/*
	 * Constructors
	 */

	public CodeTable() {
		super();
	}

	public CodeTable(Long id, String type, String code, String description,
			Date startDate, Date endDate, CodeTable parentCode,
			Set<CodeTable> childCodes, String remark, Date createdDate,
			Date lastModDate, String createdBy, String lastModBy,
			Date archiveDate, Boolean deleteIndicator, Integer version) {
		super();
		this.id = id;
		this.type = type;
		this.code = code;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.parentCode = parentCode;
		this.childCodes = childCodes;
		this.remark = remark;
		this.createdDate = createdDate;
		this.lastModDate = lastModDate;
		this.createdBy = createdBy;
		this.lastModBy = lastModBy;
		this.archiveDate = archiveDate;
		this.deleteIndicator = deleteIndicator;
		this.version = version;
	}

	/*
	 * Getter and Setters Persisted Fields
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Size(max = 40)
	@NotBlank
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Size(max = 40)
	@NotBlank
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@JsonManagedReference
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	public CodeTable getParentCode() {
		return parentCode;
	}

	public void setParentCode(CodeTable parentCode) {
		this.parentCode = parentCode;
	}

	@XmlInverseReference(mappedBy = "parentCode")
	@JsonBackReference
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parentCode")
	@Cascade(CascadeType.ALL)
	public Set<CodeTable> getChildCodes() {
		return childCodes;
	}

	public void setChildCodes(Set<CodeTable> childCodes) {
		this.childCodes = childCodes;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModDate() {
		return lastModDate;
	}

	public void setLastModDate(Date lastModDate) {
		this.lastModDate = lastModDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModBy() {
		return lastModBy;
	}

	public void setLastModBy(String lastModBy) {
		this.lastModBy = lastModBy;
	}

	public Date getArchiveDate() {
		return archiveDate;
	}

	public void setArchiveDate(Date archiveDate) {
		this.archiveDate = archiveDate;
	}

	public Boolean getDeleteIndicator() {
		return deleteIndicator;
	}

	public void setDeleteIndicator(Boolean deleteIndicator) {
		this.deleteIndicator = deleteIndicator;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	/*
	 * Transient methods
	 */
	@Transient
	public boolean isCreate() {
		return (this.id == null);
	}

	/*
	 * Overridden methods
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((code == null) ? 0 : code.hashCode());
		result = PRIME * result + ((type == null) ? 0 : type.hashCode());
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
		final CodeTable other = (CodeTable) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}
