package com.simonbassey.springtodo.test.unit.api;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.simonbassey.springtodo.api.controllers.AccountController;
import com.simonbassey.springtodo.core.abstractions.services.IUserService;
import com.simonbassey.springtodo.core.domain.entities.ApplicationUser;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

	@Autowired
	private MockMvc _mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@Mock
	private IUserService _userService;
	
	@InjectMocks
	private AccountController _accountController;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		/*
		_mockMvc = MockMvcBuilders
				//.standaloneSetup(AccountController.class)
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
		*/
	}
	
	@Test
	public void createAccountEndPointShouldOkStatusResponseGivenValidRequestPayload() throws Exception {
		var createdAccountMock = new ApplicationUser("fakeFirstName", "fakeLastName", "fakeuser@example.com", "password1");
		when(_userService.createUser(Mockito.any(ApplicationUser.class))).thenReturn(createdAccountMock);
		
		var accountToCreate = new ApplicationUser("fackFirstName", "fakeLastName", "fakeuser@example.com", "password1");
		accountToCreate.setUserId("user001");
		//act
		var requestResponse = _mockMvc.perform(post("/api/accounts/create", accountToCreate));
		
		//assert
		requestResponse
			.andExpect(status().isOk())
			.andReturn();
	}
}
