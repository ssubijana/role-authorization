package com.ssubijana.roleauthorization.repository;

import com.ssubijana.roleauthorization.UserRepository;
import com.ssubijana.roleauthorization.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@Rollback
public class UserRepositoryIT {

	@Autowired
	private UserRepository userRepository;


	@Test
	public void shouldGetUserByUserName() {
		User retrievedUser = userRepository.findByName("userTest");

		assertThat(retrievedUser).isNotNull();
	}

	@Test
	public void shouldGetUserById() {
		User retrievedUser = userRepository.findById(1L);

		assertThat(retrievedUser).isNotNull();
		assertThat(retrievedUser.getName()).isEqualTo("userTest");
	}

}
