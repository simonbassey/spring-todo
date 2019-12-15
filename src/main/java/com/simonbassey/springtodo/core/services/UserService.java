package com.simonbassey.springtodo.core.services;

import java.util.List;
import java.util.Objects;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.simonbassey.springtodo.core.abstractions.repositories.UserRepository;
import com.simonbassey.springtodo.core.abstractions.services.IUserService;
import com.simonbassey.springtodo.core.domain.entities.ApplicationUser;

@Service
public class UserService implements IUserService {
	
	private final UserRepository _userRepository;
	private final PasswordEncoder _passwordEncoder;
	
	
	public UserService(final UserRepository userRepository) {
		_userRepository = userRepository;
		_passwordEncoder = new BCryptPasswordEncoder(); // tight coupling here. Refactor to an interface behind a proxy or Adapter
	}
	@Override
	public ApplicationUser createUser(ApplicationUser user) {
		Objects.requireNonNull(user);
		try {
			ApplicationUser existingUser = this.getUserByEmail(user.getEmail());
			if(existingUser != null)
				return null;
			user.setPassword(_passwordEncoder.encode(user.getPassword()));
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
