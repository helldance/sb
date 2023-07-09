package com.coordsafe.core.rbac.controller;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.codetable.service.CodeTableService;
import com.coordsafe.core.rbac.decorators.RoleAssignPermissionsTableDecorator;
import com.coordsafe.core.rbac.entity.Permission;
import com.coordsafe.core.rbac.entity.Role;
import com.coordsafe.core.rbac.exception.PermissionException;
import com.coordsafe.core.rbac.exception.RoleException;
import com.coordsafe.core.rbac.service.PermissionService;
import com.coordsafe.core.rbac.service.ResourceService;
import com.coordsafe.core.rbac.service.RoleService;

@Controller
@RequestMapping("/" + Constants.ROLEHOME
		+ Constants.ROLEASSIGNPERMISSIONS)
public class RoleAssignPermissionsController {

	private RoleService roleService;
	private CodeTableService codeTableService;
	private ResourceService resourceService;
	private PermissionService permissionService;

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Autowired
	public void setCodeTableService(CodeTableService codeTableService) {
		this.codeTableService = codeTableService;
	}

	@Autowired
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	@Autowired
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Model showPermissions(
			@RequestParam(value = Constants.ROLEPARAM, required = true) String name,
			Model model) {
		model.addAttribute("role", roleService.findByRoleName(name));
		model.addAttribute("resources", resourceService.findAll());
		model.addAttribute("currentPerms", roleService.findByRoleName(name)
				.getPermissions());
		model.addAttribute("roleAssignPermissionsTableDecorator",
				new RoleAssignPermissionsTableDecorator(codeTableService));

		return model;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String assignPermissions(Role oldRole, HttpServletRequest request) {
		Role role = roleService.findByRoleName(oldRole.getName());
		String[] currentPerms = (String[]) request
				.getParameterValues("currentPerms");
		Set<Permission> permissions = new HashSet<Permission>();

		if (currentPerms != null) {
			for (int i = 0; i < currentPerms.length; i++) {
				Permission permission = new Permission();
				String name = currentPerms[i].substring(0, currentPerms[i].indexOf('-'));
				String resource = currentPerms[i].substring(currentPerms[i].indexOf('-') + 1);

				permission.setName(name);
				permission.setResource(resourceService.findByName(resource));

				if (permissionService.findByExample(permission).isEmpty()) {
					try {
						permissionService.create(permission);
					} catch (PermissionException e) {
						e.printStackTrace();
					}
					permissions.add(permission);
				} else {
					permissions.add(permissionService.findByExample(permission)
							.get(0));
				}
			}
		}

		role.setPermissions(permissions);

		try {
			roleService.updateRole(role);
		} catch (RoleException e) {
			e.printStackTrace();
			return "redirect:/" + Constants.ROLEHOME
					+ Constants.ROLESEARCH;
		}

		return "redirect:/" + Constants.ROLEHOME + Constants.ROLESEARCH;
	}
}
