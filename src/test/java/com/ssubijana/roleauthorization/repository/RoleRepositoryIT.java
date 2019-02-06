package com.ssubijana.roleauthorization.repository;

import com.ssubijana.roleauthorization.domain.Role;
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
public class RoleRepositoryIT {

	@Autowired
	private RoleRepository roleRepository;

	@Test
	public void getRoleByIdShouldReturnData() {
		final Role role = roleRepository.getRoleById(1);

		assertThat(role).isNotNull();
		assertThat(role.getId()).isEqualTo(1);
	}

	@Test
	public void getRoleByNameShouldReturnData() {
		final Role role = roleRepository.findByName("ADMIN");

		assertThat(role).isNotNull();
		assertThat(role.getId()).isEqualTo(1);
	}

}
