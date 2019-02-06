package com.ssubijana.roleauthorization.service;

import com.ssubijana.roleauthorization.domain.Role;
import com.ssubijana.roleauthorization.domain.User;
import com.ssubijana.roleauthorization.repository.RoleRepository;
import com.ssubijana.roleauthorization.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserServiceTest {

	private static final String USER_NAME = "USER_NAME";

	private static final long USER_ID = 1L;

	private static final String USER_PASS = "USER_PASS";

	@Mock
	private UserRepository userRepository;

	@Mock
	private RoleRepository roleRepository;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	public void loadUserByUsernameShouldMapUserDetails() {
		Set<Role> roles = new HashSet<>();
		roles.add(Role.builder().name("ROLE_ADMIN").id(1L).description("ROLE_DESC").build());
		roles.add(Role.builder().name("ROLE_USER").id(1L).description("ROLE_DESC_USER").build());

		User expectedUser = User.builder().id(USER_ID).name(USER_NAME).password(USER_PASS).roles(roles).build();

		when(userRepository.findByName(USER_NAME)).thenReturn(expectedUser);

		final UserDetails userDetails = userService.loadUserByUsername(USER_NAME);

		assertThat(userDetails).isNotNull();
		assertThat(userDetails.getUsername()).isEqualTo(USER_NAME);
		assertThat(userDetails.getAuthorities()).isNotEmpty();
	}

	@Test(expected = UsernameNotFoundException.class)
	public void loadUserByUsernameShouldThrowExceptionIfUserIsNotFound() {
		userService.loadUserByUsername(USER_NAME);
	}

	@Test
	public void getUserShouldCallRepository() {
		userService.getUser(USER_ID);

		verify(userRepository).findById(USER_ID);
	}

	@Test
	public void saveUserShouldSaveUserWithUserRole() {
		final User userToSave = User.builder().name(USER_NAME).password(USER_PASS).build();
		final Role userRole = Role.builder().name("ROLE_USER").id(1L).description("ROLE_USER").build();

		Set<Role> roles = new HashSet<>();
		roles.add(userRole);
		User encryptedUser = User.builder().name(USER_NAME).password(USER_PASS).roles(roles).build();

		when(roleRepository.findByName("USER")).thenReturn(userRole);
		when(userRepository.save(encryptedUser)).thenReturn(encryptedUser);

		final User savedUser = userService.save(userToSave);

		assertThat(savedUser).isEqualTo(encryptedUser);
	}

}
