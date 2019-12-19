package com.simonbassey.springtodo.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.simonbassey.springtodo.core.abstractions.services.IUserService;
import com.simonbassey.springtodo.infrastructure.security.AppUserDetailService;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final AppUserDetailService _userDetailService;
	public AppSecurityConfig(AppUserDetailService userService) {
		this._userDetailService = userService;
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
			.httpBasic();
	}

}
