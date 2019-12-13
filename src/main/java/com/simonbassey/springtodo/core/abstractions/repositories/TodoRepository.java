package com.simonbassey.springtodo.core.abstractions.repositories;

import java.util.List;

import com.simonbassey.springtodo.core.domain.entities.Todo;

public interface TodoRepository {
	Todo addTodo(Todo todo);
	Todo getTodo(int todoId);
	boolean updateTodo(int todoId, Todo todo);
	boolean removeTodo(int todoId);
	List<Todo> getTodos();
	List<Todo> getTodosByUserId(String userId);	
}
