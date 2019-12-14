package com.simonbassey.springtodo.api.controllers;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simonbassey.springtodo.core.abstractions.services.ITodoService;
import com.simonbassey.springtodo.core.abstractions.services.IUserService;
import com.simonbassey.springtodo.core.domain.entities.ApplicationUser;
import com.simonbassey.springtodo.core.domain.entities.Todo;
import com.simonbassey.springtodo.core.domain.models.TodoCreateRequest;


@RestController
@RequestMapping("/api/todos")
public class TodoController {
	private final ITodoService _todoService;
	private final IUserService _userService;
	
	public TodoController(ITodoService todoService, IUserService userService) {
		_todoService = todoService;
		_userService = userService;
	}
	
	
	@GetMapping("/{userId}")
	public ResponseEntity<?> getTodos(@NotEmpty @PathVariable("userId") String userId ) {
		try {
			if(userId == null || userId.isEmpty())
				return ResponseEntity.badRequest().body(String.format("%s is not recognized as a valid user Id", userId));
			var todos = _todoService.GetTodos(userId);
			return ResponseEntity.ok(todos);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> addTodo(@RequestBody @Valid TodoCreateRequest request, Errors errors) {
		try {
			if(errors.hasErrors())
				return ResponseEntity.badRequest().body(errors.getAllErrors());
			ApplicationUser user = _userService.getUser(request.getUserId());
			if(user == null || user.getEmail().isEmpty())
				return ResponseEntity.badRequest().body("Invalid user Id. A valid user Id is required to create a new todo");
			var todoEntity = new Todo(request.getTitle(), request.getDetail())
									.withUserId(user.getUserId());
			
			var createresult = _todoService.CreateTodo(todoEntity);
			if(createresult == null)
				return ResponseEntity.noContent().build();
			return ResponseEntity.ok(createresult);
		} 
		catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body("Invalid user Id. A valid user Id is required to create a new todo");
		}
		catch (Exception e) {
			throw e;
		}
	}
}
