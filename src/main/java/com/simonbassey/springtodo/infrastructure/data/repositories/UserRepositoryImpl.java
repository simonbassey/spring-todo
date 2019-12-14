package com.simonbassey.springtodo.infrastructure.data.repositories;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.simonbassey.springtodo.core.abstractions.repositories.UserRepository;
import com.simonbassey.springtodo.core.domain.entities.ApplicationUser;

@Repository
public class UserRepositoryImpl implements UserRepository {
	
	private final SpringJpaUserRepository _userRepository;
	public UserRepositoryImpl(final SpringJpaUserRepository repository) {
		_userRepository = repository;
	}
	@Override
	public ApplicationUser addUser(ApplicationUser user) {
		try {
			Objects.requireNonNull(user);
			return _userRepository.saveAndFlush(user);
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public ApplicationUser getUser(String userId) {
		try {
			return _userRepository.getOne(userId);
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public ApplicationUser getUserByEmail(String emailAddress) {
		try {
			ApplicationUser existingUserWithEmail = new ApplicationUser();
			existingUserWithEmail.setEmail(emailAddress);
			Example<ApplicationUser> e = Example.of(existingUserWithEmail);
			Optional<ApplicationUser> user = _userRepository.findOne(e);
			if(!user.isPresent())
				return null;
			return user.get();
			
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public boolean updateUser(String userId, ApplicationUser user) {
		try {
			if(!_userRepository.existsById(userId))
				return false;
			return _userRepository.saveAndFlush(user) != null;
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public boolean removeUser(String userId) {
		try {
			if(!_userRepository.existsById(userId))
				return false;
			_userRepository.deleteById(userId);
			return true;
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public List<ApplicationUser> getUsers() {
		try {
			return _userRepository.findAll();
			
		} catch (Exception e) {
			throw e;
		}
	}
}
