package com.ssubijana.roleauthorization.repository;

import com.ssubijana.roleauthorization.domain.Role;
import com.ssubijana.roleauthorization.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@Rollback
public class UserRepositoryIT {

	@Autowired
	private RoleRepository roleRepository;

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

	@Test
	public void shouldGetAllUsers() {
		final List<User> users = userRepository.findAll();

		assertThat(users).hasSize(3);
	}

	@Test
	public void shouldSaveUserWithAssociatedRoles() {
		Set<Role> roles = new HashSet<>();
		roles.add(roleRepository.getRoleById(2));

		User newUser = User.builder().name("NEW_USER").password("PASSWORD").roles(roles).build();
		User savedUser = userRepository.save(newUser);

		final User retrievedUser = userRepository.findById(savedUser.getId());

		assertThat(retrievedUser).isEqualTo(savedUser);
	}

}
