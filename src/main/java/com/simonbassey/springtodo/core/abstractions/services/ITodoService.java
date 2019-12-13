package com.simonbassey.springtodo.core.abstractions.services;

import java.util.List;

import com.simonbassey.springtodo.core.domain.entities.Todo;

public interface ITodoService {
	Todo CreateTodo(Todo todoEntity);
	List<Todo> GetTodos();
	List<Todo> GetTodos(String userId);
}
