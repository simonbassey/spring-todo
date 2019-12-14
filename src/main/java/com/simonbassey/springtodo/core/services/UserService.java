package com.simonbassey.springtodo.core.services;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.simonbassey.springtodo.core.abstractions.repositories.UserRepository;
import com.simonbassey.springtodo.core.abstractions.services.IUserService;
import com.simonbassey.springtodo.core.domain.entities.ApplicationUser;

@Service
public class UserService<PasswordEncoder> implements IUserService {
	
	private final UserRepository _userRepository;
	//private final PasswordEncoder _passwordEncoder;
	
	
	public UserService(final UserRepository userRepository) {
		_userRepository = userRepository;
	}
	@Override
	public ApplicationUser createUser(ApplicationUser user) {
		Objects.requireNonNull(user);
		try {
			ApplicationUser existingUser = this.getUserByEmail(user.getEmail());
			//user.setPassword(_passwordEncoder.encode(user.getPassword())); // delayed until we add security
			if(existingUser != null)
				return null;
			return _userRepository.addUser(user);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ApplicationUser getUser(String userId) {
		try {
			return _userRepository.getUser(userId);
		}
		catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ApplicationUser getUserByEmail(String emailAddress) {
		try {
			return _userRepository.getUserByEmail(emailAddress);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<ApplicationUser> getUsers() {
		try {
			return _userRepository.getUsers();
		} catch (Exception e) {
			throw e;
		}
	}

}
