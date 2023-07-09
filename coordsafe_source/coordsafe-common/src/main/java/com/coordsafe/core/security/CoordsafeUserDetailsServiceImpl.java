package com.coordsafe.core.security;

import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.service.UserService;

@Service("userDetailsService")
public class CoordsafeUserDetailsServiceImpl implements UserDetailsService {

	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataException {
		
		//User user = userService.findByUsername(username);
		User user = userService.findByUsernameOrEmail(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found!");
		}
		
		return user;
	}
}
