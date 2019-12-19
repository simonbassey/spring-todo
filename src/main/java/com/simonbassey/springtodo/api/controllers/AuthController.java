package com.simonbassey.springtodo.api.controllers;

import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simonbassey.springtodo.core.domain.models.AuthenticationRequest;
import com.simonbassey.springtodo.infrastructure.security.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthenticationService _authService;
	
	public AuthController(AuthenticationService authenticationService) {
		_authService = authenticationService;
	}
	@PostMapping("/Token")
	public ResponseEntity<?> authenticate( @NotNull @RequestBody AuthenticationRequest authRequest) {
		try {
			var signInResult = _authService.signInUser(authRequest);
			if(!signInResult.isSuccess())
				return ResponseEntity.badRequest().body(signInResult.getErrors().get(0));
			return ResponseEntity.ok(signInResult.getToken());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	

}
