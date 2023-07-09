package com.coordsafe.guardian.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class GuardianUser implements Serializable, UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private Integer id;
    private String password;
    private String username;
    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private boolean enabled;
    private GrantedAuthority[] authorities = null;
    
    public GuardianUser(Integer id, String username,String password){
    	super();
    	this.id = id;
    	this.username = username;
    	this.password = password;
    }
    
    
	public GuardianUser(Integer id, String password, String username,
			boolean accountNonLocked, boolean accountNonExpired,
			boolean enabled, GrantedAuthority[] authorities) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		
		this.accountNonLocked = accountNonLocked;
		this.accountNonExpired = accountNonExpired;
		this.enabled = enabled;
		this.authorities = authorities;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        Collections.addAll(auth, authorities);
        return auth;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

}
