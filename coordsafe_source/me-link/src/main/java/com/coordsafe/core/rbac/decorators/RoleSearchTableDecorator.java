package com.coordsafe.core.rbac.decorators;

import org.displaytag.decorator.TableDecorator;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.codetable.service.CodeTableService;
import com.coordsafe.core.rbac.entity.Role;

public class RoleSearchTableDecorator extends TableDecorator {
	
	private CodeTableService codeTableService;
	
	public RoleSearchTableDecorator(CodeTableService codeTableService) {
		super();
		this.codeTableService = codeTableService;
	}

	public String getType() {
		Role role = (Role) getCurrentRowObject();
		
		if (role.getType() == null || role.getType().isEmpty())
			return "";
		else
			return codeTableService.findByTypeCode("ROLE_TYPE", role.getType()).getDescription();
	}

	public String getAction() {
		
		Role role = (Role) getCurrentRowObject();

		String spaces = "&nbsp&nbsp";

		String editRole = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/role/edit?name=" + role.getName()
				+ "\" title=\"Edit Role\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH
				+ "/common/role_edit.png\"></a>";

		String showRolePermissions = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/role/assignPermissions?name=" + role.getName()
				+ "\" title=\"Show Role Permissions\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH
				+ "/common/permission_show.png\"></a>";

		String showRoleUsers = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/role/users?name=" + role.getName()
				+ "\" title=\"Show Role Users\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH
				+ "/common/user_show.png\"></a>";

		String deleteRole = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/role/delete?name=" + role.getName()
				+ "\" title=\"Delete Role\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH
				+ "/common/role_delete.png\"></a>";

		return editRole + spaces + showRolePermissions + spaces + showRoleUsers
				+ spaces + deleteRole;
	}
}
