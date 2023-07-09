package com.coordsafe.core.rbac.decorators;

import org.displaytag.decorator.TableDecorator;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.rbac.entity.Organization;

public class OrganizationSearchTableDecorator extends TableDecorator {

	public String getAction() {
		Organization organization = (Organization) getCurrentRowObject();

		String spaces = "&nbsp&nbsp";

		String editOrganization = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/organization/edit?name=" + organization.getName()
				+ "\" title=\"Edit Organization\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH + "/common/organization_edit.png\"></a>";

		String deleteOrganization = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/organization/delete?name=" + organization.getName()
				+ "\" title=\"Delete Organization\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH + "/common/organization_delete.png\"></a>";

		return editOrganization + spaces + deleteOrganization;
	}
}
