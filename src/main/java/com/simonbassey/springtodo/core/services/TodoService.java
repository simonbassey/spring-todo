package com.simonbassey.springtodo.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simonbassey.springtodo.core.abstractions.repositories.TodoRepository;
import com.simonbassey.springtodo.core.abstractions.services.ITodoService;
import com.simonbassey.springtodo.core.domain.entities.Todo;

@Service
public class TodoService implements ITodoService {

	private final TodoRepository _todoRepository;
	
	//@Autowired
	public TodoService(TodoRepository repository) {
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
			return this._todoRepository.getTodos();
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
