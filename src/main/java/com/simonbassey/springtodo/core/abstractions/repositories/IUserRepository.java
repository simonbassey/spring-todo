package com.simonbassey.springtodo.core.abstractions.repositories;

import java.util.List;

import com.simonbassey.springtodo.core.domain.entities.ApplicationUser;

public interface IUserRepository {
	ApplicationUser addUser(ApplicationUser user);
	ApplicationUser getUser(String userId);
	ApplicationUser getUserByEmail(String emailAddress);
	boolean updateUser(String userId, ApplicationUser user);
	boolean removeUser(String userId);
	List<ApplicationUser> getUsers();
}
