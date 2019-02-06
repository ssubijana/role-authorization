package com.ssubijana.roleauthorization.repository;

import com.ssubijana.roleauthorization.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRepository extends JpaRepository<User, Long> {

	User findByName(String name);

	User findById(long id);

	List<User> findAll();
}
