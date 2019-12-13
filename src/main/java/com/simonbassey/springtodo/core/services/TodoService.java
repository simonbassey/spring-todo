package com.simonbassey.springtodo.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import com.simonbassey.springtodo.core.abstractions.repositories.ITodoRepository;
import com.simonbassey.springtodo.core.abstractions.services.ITodoService;
import com.simonbassey.springtodo.core.domain.entities.Todo;

public class TodoService implements ITodoService {

	private final ITodoRepository _todoRepository;
	
	@Autowired
	public TodoService(ITodoRepository repository) {
		_todoRepository = repository;
	}
	
	@Override
	public Todo CreateTodo(Todo todoEntity) {
		Objects.requireNonNull(todoEntity);
		try {
			Todo res = this._todoRepository.addTodo(todoEntity);
			return res;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<Todo> GetTodos() {
		try {
			List<Todo> todos = new ArrayList<>();
			this._todoRepository.getTodos();
			return todos;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<Todo> GetTodos(String userId) {
		try {
			return _todoRepository.getTodosByUserId(userId);
		} catch (Exception e) {
			throw e;
		}
	}

}
