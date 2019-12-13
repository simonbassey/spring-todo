package com.simonbassey.springtodo.core.abstractions.services;

import java.util.List;

import com.simonbassey.springtodo.core.domain.entities.ApplicationUser;


public interface IUserService {
	ApplicationUser createUser(ApplicationUser user);
	ApplicationUser getUser(String userId);
	ApplicationUser getUserByEmail(String emailAddress);
	List<ApplicationUser> getUsers();
}
