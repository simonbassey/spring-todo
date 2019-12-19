package com.simonbassey.springtodo.infrastructure.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.simonbassey.springtodo.core.abstractions.services.JwtTokenService;
import com.simonbassey.springtodo.core.domain.models.AuthenticationRequest;
import com.simonbassey.springtodo.core.domain.models.AuthenticationResult;;

@Service
public class AuthenticationService {

	private final AppUserDetailService _userDetailService;
	private final JwtTokenService _jwtTokenService;
	private final AuthenticationManager _authManager;
	public AuthenticationService(AppUserDetailService userService, JwtTokenService tokenService, AuthenticationManager authenticationManager) {
		_userDetailService = userService;
		_jwtTokenService = tokenService;
		_authManager = authenticationManager;
	}
	
	public  AuthenticationResult signInUser(AuthenticationRequest authRequests) throws Exception {
		try {
			if(authRequests == null)
				return new AuthenticationResult(false, null, new String[]{"Invalid authentication request"});
			_authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequests.getUsername(), authRequests.getPassword()));
			UserDetails userDetails = _userDetailService.loadUserByUsername(authRequests.getUsername());
			if(userDetails == null)
				throw new Exception("Failed to load user details");
			var userClaims = buildClaimsFromUserDetails(userDetails);
			var token = _jwtTokenService.generateToken(userDetails.getUsername(), userClaims);
			if(token == null || token.isBlank())
				return new AuthenticationResult(false,null, new String[] {"Failed to generate jwt Token"});
			return new AuthenticationResult(true, token);
		} 
		catch (DisabledException e) {
			return new AuthenticationResult(false, null, new String[] {"User account may have been disabled"});
		}
		catch (LockedException e) {
			return new AuthenticationResult(false, null, new String[] {"User account may have been locked"});
		}
		catch (BadCredentialsException e) {
			return new AuthenticationResult(false, null, new String[] {"Invalid credentials. Username or password is incorrect"});
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	private Map<String, Object> buildClaimsFromUserDetails(UserDetails userDetails) {
		var claimsList = new HashMap<String, Object>();
		var roles = new ArrayList<>();
		userDetails.getAuthorities().forEach(c->roles.add(c.getAuthority()));
		claimsList.put("roles", roles);
		return claimsList;
	}
	
	
}
