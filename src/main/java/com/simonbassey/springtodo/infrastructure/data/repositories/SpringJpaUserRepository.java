package com.simonbassey.springtodo.infrastructure.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simonbassey.springtodo.core.domain.entities.ApplicationUser;

public interface SpringJpaUserRepository extends JpaRepository<ApplicationUser, String>  {

}
