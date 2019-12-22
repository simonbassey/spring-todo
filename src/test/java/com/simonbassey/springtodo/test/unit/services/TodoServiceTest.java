package com.simonbassey.springtodo.test.unit.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.simonbassey.springtodo.core.abstractions.repositories.TodoRepository;
import com.simonbassey.springtodo.core.domain.entities.Todo;
import com.simonbassey.springtodo.core.services.TodoService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class TodoServiceTest {

	@Mock
	private TodoRepository _todoRepository = Mockito.mock(TodoRepository.class);
	@InjectMocks
	public TodoService _todoService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test()
	public void shouldFailToCreateEmptyTodoObject() {
		//arrange
		Todo emptyTodo = null;
		//act
		Exception todoCreateResult = assertThrows(NullPointerException.class, ()->_todoService.CreateTodo(emptyTodo));
		//assert
		assertThat(todoCreateResult)
			.isInstanceOf(NullPointerException.class);
	}
	
	@Test
	public void shouldCreateNewTodoGivenNonEmptyTodoObject() {
		// arrange
		Todo todo = new Todo("Test activity1", "Test activity description");
		
		when(_todoRepository.addTodo(Mockito.any(Todo.class))).thenReturn(todo);
		//act
		var createResult = _todoService.CreateTodo(new Todo());
		//assert
		assertThat(createResult).isNotNull();
		assertEquals("Test activity1", createResult.getTitle());
		
	}
}
