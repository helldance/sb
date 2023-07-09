package com.coordsafe.core.rbac.decorators;

import java.util.Set;

import org.displaytag.decorator.TableDecorator;

import com.coordsafe.core.codetable.service.CodeTableService;
import com.coordsafe.core.rbac.entity.Permission;
import com.coordsafe.core.rbac.entity.Resource;

public class RoleAssignPermissionsTableDecorator extends TableDecorator {

	private CodeTableService codeTableService;

	public RoleAssignPermissionsTableDecorator(CodeTableService codeTableService) {
		this.codeTableService = codeTableService;
	}

	public String getType() {
		Resource resource = (Resource) getCurrentRowObject();

		if (resource.getType() == null || resource.getType().isEmpty())
			return "";
		else
			return codeTableService.findByTypeCode("RESOURCE_TYPE",
					resource.getType()).getDescription();
	}

	public String getViewPermission() {
		Resource resource = (Resource) getCurrentRowObject();
		@SuppressWarnings("unchecked")
		Set<Permission> currentPermissions = (Set<Permission>) getPageContext()
				.getRequest().getAttribute("currentPerms");

		for (Permission permission : currentPermissions) {
			if (permission.getName().equalsIgnoreCase("VIEW")
					&& permission.getResource().equals(resource)) {
				return "<input type=\"checkbox\" name=\"currentPerms\" value=\"VIEW-"
						+ resource.getName() + "\" checked=\"checked\"/>";
			}
		}
		return "<input type=\"checkbox\" name=\"currentPerms\" value=\"VIEW-"
				+ resource.getName() + "\"/>";
	}

	public String getOpenPermission() {
		Resource resource = (Resource) getCurrentRowObject();
		@SuppressWarnings("unchecked")
		Set<Permission> currentPermissions = (Set<Permission>) getPageContext()
				.getRequest().getAttribute("currentPerms");

		for (Permission permission : currentPermissions) {
			if (permission.getName().equalsIgnoreCase("OPEN")
					&& permission.getResource().equals(resource)) {
				return "<input type=\"checkbox\" name=\"currentPerms\" value=\"OPEN-"
						+ resource.getName() + "\" checked=\"checked\"/>";
			}
		}
		return "<input type=\"checkbox\" name=\"currentPerms\" value=\"OPEN-"
				+ resource.getName() + "\"/>";
	}

	public String getExecutePermission() {
		Resource resource = (Resource) getCurrentRowObject();
		@SuppressWarnings("unchecked")
		Set<Permission> currentPermissions = (Set<Permission>) getPageContext()
				.getRequest().getAttribute("currentPerms");

		for (Permission permission : currentPermissions) {
			if (permission.getName().equalsIgnoreCase("EXECUTE")
					&& permission.getResource().equals(resource)) {
				return "<input type=\"checkbox\" name=\"currentPerms\" value=\"EXECUTE-"
						+ resource.getName() + "\" checked=\"checked\"/>";
			}
		}
		return "<input type=\"checkbox\" name=\"currentPerms\" value=\"EXECUTE-"
				+ resource.getName() + "\"/>";
	}
}
