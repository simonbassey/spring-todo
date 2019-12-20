package com.simonbassey.springtodo.infrastructure.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.simonbassey.springtodo.core.abstractions.services.JwtTokenService;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenService _jwtTokenService;
	private final AppUserDetailService _userDetailsService;
	
	public AuthenticationFilter(JwtTokenService tokenService, AppUserDetailService userDetailService) {
		_jwtTokenService = tokenService;
		_userDetailsService = userDetailService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String username = null;
			String jwtToken = null;
			var authHeader = request.getHeader("Authorization");
			if(authHeader != null && !authHeader.isBlank() && authHeader.toLowerCase().contains("bearer")) {
				jwtToken = authHeader.substring(7).trim();
				username = _jwtTokenService.extractUserNameFromToken(jwtToken);
			}
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				var signedInUser = _userDetailsService.loadUserByUsername(username);
				var usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(signedInUser, signedInUser.getPassword(), signedInUser.getAuthorities());
				usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);
			}
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			// log here and instead of bubbling down the chain, deny authentication gracefully by terminating request with unauthenticated status code
			filterChain.doFilter(request, response);
			// throw e;
		}
		
	}

}
