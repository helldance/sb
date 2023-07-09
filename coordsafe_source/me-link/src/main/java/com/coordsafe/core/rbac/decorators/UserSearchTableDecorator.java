package com.coordsafe.core.rbac.decorators;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.displaytag.decorator.TableDecorator;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.rbac.entity.Group;
import com.coordsafe.core.rbac.entity.Role;
import com.coordsafe.core.rbac.entity.User;

public class UserSearchTableDecorator extends TableDecorator {

	public String getEnabled() {
		User user = (User) getCurrentRowObject();

		if (user.isEnabled()) {
			return "Active";
		} else {
			return "Disabled";
		}
	}

	public String getGroups() {
		User user = (User) getCurrentRowObject();
		String groupString = new String();
		Iterator<Group> itr = user.getGroups().iterator();

		while (itr.hasNext()) {
			groupString += itr.next().getName() + ",";
		}

		if (!groupString.isEmpty()
				&& ',' == groupString.charAt(groupString.length() - 1)) {
			groupString = groupString.substring(0, groupString.length() - 1);
		}

		return groupString;
	}

	public String getRoles() {
		User user = (User) getCurrentRowObject();
		String roleString = new String();
		Set<Role> roles = new HashSet<Role>(user.getRoles());
//		Set<Group> groups = new HashSet<Group>(user.getGroups());
//		Iterator<Group> itrGroup = groups.iterator();
//		while (itrGroup.hasNext()) {
//			roles.addAll(itrGroup.next().getRoles());
//		}

		Iterator<Role> itr = roles.iterator();
		while (itr.hasNext()) {
			roleString += itr.next().getName() + ",";
		}

		if (!roleString.isEmpty()
				&& ',' == roleString.charAt(roleString.length() - 1)) {
			roleString = roleString.substring(0, roleString.length() - 1);
		}

		return roleString;
	}

	public String getAction() {
		User user = (User) getCurrentRowObject();

		String spaces = "&nbsp&nbsp";

		String assignRoles = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/user/assignRoles?username=" + user.getUsername()
				+ "\" title=\"Assign Roles\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH
				+ "/common/role_add.png\"></a>";

		String assignGroups = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/user/assignGroups?username=" + user.getUsername()
				+ "\" title=\"Assign Groups\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH
				+ "/common/group_add.png\"></a>";

		String editUser = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/user/edit?username=" + user.getUsername()
				+ "\" title=\"Edit User\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH
				+ "/common/user_edit.png\"></a>";

		String resetUserPassword = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/user/resetPassword?username=" + user.getUsername()
				+ "\" title=\"Reset User Password\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH
				+ "/common/user_reset_password.png\"></a>";

		String joinOrganization = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/user/joinOrganization?username=" + user.getUsername()
				+ "\" title=\"Join Organization\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH
				+ "/common/organization_add.png\"></a>";

		String disableUser = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/user/disable?username=" + user.getUsername()
				+ "\" title=\"Disable User\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH + "/common/lock.png\"></a>";

		String deleteUser = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/user/delete?username=" + user.getUsername()
				+ "\" title=\"Delete User\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH
				+ "/common/user_delete.png\"></a>";

		String enableUser = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/user/enable?username=" + user.getUsername()
				+ "\" title=\"Enable User\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH
				+ "/common/lock_open.png\"></a>";

		if (user.isEnabled()) {
			return assignRoles + spaces + joinOrganization + spaces
					+ assignGroups + spaces + disableUser + spaces
					+ resetUserPassword + spaces + editUser + spaces
					+ deleteUser;
		} else {
			return enableUser;
		}
	}
}
