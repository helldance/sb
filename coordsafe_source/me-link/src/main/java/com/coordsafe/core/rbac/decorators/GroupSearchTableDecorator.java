package com.coordsafe.core.rbac.decorators;

import org.displaytag.decorator.TableDecorator;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.rbac.entity.Group;

public class GroupSearchTableDecorator extends TableDecorator {

	public String getAction() {
		Group group = (Group) getCurrentRowObject();

		String spaces = "&nbsp&nbsp";

		String assignUsers = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/group/assignUsers?name=" + group.getName()
				+ "\" title=\"Assign Users\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH
				+ "/common/user_add.png\"></a>";

		String assignRoles = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/group/assignRoles?name=" + group.getName()
				+ "\" title=\"Assign Roles\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH
				+ "/common/role_add.png\"></a>";

		String assignOrganization = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/group/joinOrganization?name=" + group.getName()
				+ "\" title=\"Join Organization\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH
				+ "/common/organization_add.png\"></a>";

		String editGroup = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/group/edit?name=" + group.getName()
				+ "\" title=\"Edit Group\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH
				+ "/common/group_edit.png\"></a>";

		String deleteGroup = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/group/delete?name=" + group.getName()
				+ "\" title=\"Delete Group\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH
				+ "/common/group_delete.png\"></a>";

		return assignUsers + spaces + assignRoles + spaces + assignOrganization
				+ spaces + editGroup + spaces + deleteGroup;
	}
}
