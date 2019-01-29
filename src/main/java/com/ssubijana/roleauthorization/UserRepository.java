package com.ssubijana.roleauthorization;

import com.ssubijana.roleauthorization.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public interface UserRepository extends JpaRepository<User, Long> {

	User findByName(String name);
}
