package com.coordsafe.core.rbac.editor;

import java.beans.PropertyEditorSupport;

import com.coordsafe.core.rbac.entity.Organization;
import com.coordsafe.core.rbac.service.OrganizationService;

public class OrganizationEditor extends PropertyEditorSupport {

	private OrganizationService organizationService;

	public OrganizationEditor(OrganizationService organizationService) {
		super();
		this.organizationService = organizationService;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text.isEmpty() || text == null) {
			setValue(null);
			return;
		} 
		Organization organization = organizationService.findByName(text);
		if (organization != null) {
			setValue(organization);
		} else {
			setValue(null);
		}
	}

	@Override
	public String getAsText() {
		Organization organization = (Organization) getValue();
		if (organization == null) {
			return null;
		} else {
			return organization.getName();
		}
	}
}
