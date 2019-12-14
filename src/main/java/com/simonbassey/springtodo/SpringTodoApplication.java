package com.simonbassey.springtodo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.simonbassey.springtodo.core.abstractions.repositories.TodoRepository;
import com.simonbassey.springtodo.core.abstractions.repositories.UserRepository;
import com.simonbassey.springtodo.infrastructure.data.repositories.SpringJpaTodoRepository;
import com.simonbassey.springtodo.infrastructure.data.repositories.SpringJpaUserRepository;
import com.simonbassey.springtodo.infrastructure.data.repositories.TodoRepositoryImpl;
import com.simonbassey.springtodo.infrastructure.data.repositories.UserRepositoryImpl;


@EnableJpaRepositories({"com.simonbassey.springtodo.core.abstractions.repositories;","com.simonbassey.springtodo.infrastructure.data.repositories"})
@SpringBootApplication(scanBasePackages = {"com.simonbassey.springtodo.api.controllers", "com.simonbassey.springtodo.core.services"})
@EntityScan(basePackages = {"com.simonbassey.springtodo.core.domain.entities"})
public class SpringTodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringTodoApplication.class, args);
	}

	@Bean
	public TodoRepository todoRepository(final SpringJpaTodoRepository repository) {
		return new TodoRepositoryImpl(repository);
	}
	
	@Bean
	public UserRepository userRepository(final SpringJpaUserRepository repository) {
		return new UserRepositoryImpl(repository);
	}
	
}
