package com.simonbassey.springtodo.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simonbassey.springtodo.core.abstractions.services.IUserService;
import com.simonbassey.springtodo.core.domain.entities.ApplicationUser;


@RestController
@RequestMapping("/api/accounts")
public class AccountController {
	
	private IUserService _userService;
	@Autowired
	public AccountController(IUserService userService) {
		this._userService = userService;
	}
	
	@PostMapping(value = "/create")
	public ResponseEntity<?> createAccount(@Valid @RequestBody ApplicationUser user, Errors errors) {
		try {
			if(errors.hasErrors())
				return ResponseEntity.badRequest().body(errors.getAllErrors());
			var similarUser = _userService.getUserByEmail(user.getEmail());
			if(similarUser != null)
				return ResponseEntity.badRequest().body("It appears you already have an account. Reset your password instead");
			var createdUser = _userService.createUser(user);
			if(createdUser.getUserId().isEmpty())
				return ResponseEntity.noContent().build();
			return ResponseEntity.ok(createdUser);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@GetMapping("")
	public ResponseEntity<?> fetchUserAccounts() {
		try {
			var users = _userService.getUsers();
			return ResponseEntity.ok(users);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
