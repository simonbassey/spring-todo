package com.simonbassey.springtodo.infrastructure.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simonbassey.springtodo.core.domain.entities.Todo;


public interface SpringJpaTodoRepository extends JpaRepository<Todo, Integer> {

}
