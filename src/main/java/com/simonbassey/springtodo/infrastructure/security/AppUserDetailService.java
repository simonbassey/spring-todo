package com.simonbassey.springtodo.infrastructure.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.simonbassey.springtodo.core.abstractions.services.IUserService;
import com.simonbassey.springtodo.core.domain.entities.ApplicationUser;

public class AppUserDetailService implements UserDetailsService {

	private final IUserService _userSevice;
	
	public AppUserDetailService(IUserService userService) {
		_userSevice = userService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			ApplicationUser user = _userSevice.getUserByEmail(username);
			if(user == null || user.getEmail().isEmpty())
				throw new UsernameNotFoundException(String.format("User with email address %s was not found",username));
			return org.springframework.security.core.userdetails.User
					.withUsername(username)
					.password(user.getPassword())
					.roles("USER")
					.accountExpired(false)
					.accountLocked(false)
					.build();
		} catch (Exception e) {
			throw e;
		}
	}

}
