package com.simonbassey.springtodo.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.simonbassey.springtodo.core.abstractions.services.IUserService;
import com.simonbassey.springtodo.infrastructure.security.AppUserDetailService;
import com.simonbassey.springtodo.infrastructure.security.AuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final AppUserDetailService _userDetailService;
	private final AuthenticationFilter _authFilter;
	public AppSecurityConfig(AppUserDetailService userService, AuthenticationFilter authenticationFilter) {
		this._userDetailService = userService;
		_authFilter = authenticationFilter;
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(_userDetailService);
	}
	
	@Bean
	protected BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable();
		httpSecurity.
			authorizeRequests()
			.antMatchers("/api/accounts/create", "/api/auth/Token").anonymous()
			.antMatchers("/api/**").fullyAuthenticated()
			.and()
			//.httpBasic();
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.addFilterBefore(_authFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
