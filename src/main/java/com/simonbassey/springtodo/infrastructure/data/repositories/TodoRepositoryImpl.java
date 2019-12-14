package com.simonbassey.springtodo.infrastructure.data.repositories;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Example;

import com.simonbassey.springtodo.core.abstractions.repositories.TodoRepository;
import com.simonbassey.springtodo.core.domain.entities.Todo;

//@Repository
public class TodoRepositoryImpl implements TodoRepository {

	private final SpringJpaTodoRepository _todoRepository;
	public TodoRepositoryImpl(SpringJpaTodoRepository repository) {
		_todoRepository = repository;
	}
	
	@Override
	public Todo addTodo(Todo todo) {
		try {
			Objects.requireNonNull(todo);
			return _todoRepository.saveAndFlush(todo);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Todo getTodo(int todoId) {
		try {
			return _todoRepository.getOne(todoId);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public boolean updateTodo(int todoId, Todo todo) {
		try {
			if(!_todoRepository.existsById(todoId))
				return false;
			return _todoRepository.saveAndFlush(todo) != null;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public boolean removeTodo(int todoId) {
		try {
			if(!_todoRepository.existsById(todoId))
				return false;
			_todoRepository.deleteById(todoId);
			return true;
			
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<Todo> getTodos() {
		try {
			return _todoRepository.findAll();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<Todo> getTodosByUserId(String userId) {
		try {
			Todo targetTodo = new Todo();
			targetTodo.setUserId(userId);
			Example<Todo> targetCriteria = Example.of(targetTodo);
			List<Todo> result = _todoRepository.findAll(targetCriteria);
			return result;
		} catch (Exception e) {
			throw e;
		}
	}

}
