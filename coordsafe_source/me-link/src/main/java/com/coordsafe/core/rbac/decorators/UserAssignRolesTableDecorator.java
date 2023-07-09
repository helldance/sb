package com.coordsafe.core.rbac.decorators;

import org.displaytag.decorator.TableDecorator;

import com.coordsafe.core.rbac.entity.Role;
import com.coordsafe.core.rbac.entity.User;


/*
 * Currently Unused!!!!!
 */
public class UserAssignRolesTableDecorator extends TableDecorator {

	public String getName() {
		Role role = (Role) getCurrentRowObject();
		User user = (User) getPageContext().getRequest().getAttribute("user");

		if (user.getRoles().contains(role)) {
			return "<input type=\"checkbox\" name=\"currentRole\" value=\""
					+ role.getName() + "\" checked=\"checked\">"
					+ role.getName() + "</input>";
		} else {
			return "<input type=\"checkbox\" name=\"currentRole\" value=\""
					+ role.getName() + "\">" + role.getName() + "</input>";
		}
	}
}