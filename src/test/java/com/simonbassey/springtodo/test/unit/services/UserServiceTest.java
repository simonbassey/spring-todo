package com.simonbassey.springtodo.test.unit.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.simonbassey.springtodo.core.abstractions.repositories.UserRepository;
import com.simonbassey.springtodo.core.domain.entities.ApplicationUser;
import com.simonbassey.springtodo.core.services.UserService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class UserServiceTest {

	private UserRepository _userRepository = mock(UserRepository.class);
	private UserService _userService;
	
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		_userService = new UserService(_userRepository);
	}
	
	@Test
	public void createUserShouldReturnUserObjectGivenValidApplicationUser() {
		when(_userRepository.addUser(Mockito.any(ApplicationUser.class))).thenAnswer(invocation -> invocation.getArgument(0));
		var mockUser = new ApplicationUser("Maria", "Nagaga", "maria@example.com","marie");
		
		// act
		var createdUser = _userService.createUser(mockUser);
		
		//assert
		assertThat(createdUser).isNotNull();
		assertThat(createdUser).isInstanceOf(ApplicationUser.class);
		assertThat(createdUser.getPassword()).isNotEmpty();
		assertThat(createdUser.getPassword().length() > mockUser.getPassword().length());
	}
	
	
	
}
