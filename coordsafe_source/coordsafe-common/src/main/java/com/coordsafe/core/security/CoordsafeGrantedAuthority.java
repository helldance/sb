package com.coordsafe.core.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.coordsafe.core.rbac.entity.Permission;

public interface CoordsafeGrantedAuthority extends GrantedAuthority {

	public List<Permission> getPermissons();
}
