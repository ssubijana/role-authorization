package com.ssubijana.roleauthorization.web;

import com.ssubijana.roleauthorization.domain.User;
import com.ssubijana.roleauthorization.service.UserServiceImpl;
import com.ssubijana.roleauthorization.web.presentation.UserResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

	private static final long USER_ID = 1L;

	@Mock
	private UserServiceImpl userService;

	@InjectMocks
	private UserController userController;

	@Test
	public void getUserShouldCallService() {
		final User user = User.builder().id(USER_ID).name("USER_NAME").password("USER_PASSWORD").build();
		when(userService.getUser(USER_ID)).thenReturn(user);
		final ResponseEntity<UserResponse> userResponse = userController.getUser(USER_ID);

		assertThat(userResponse.getBody()).isNotNull();

		UserResponse retrievedUser = userResponse.getBody();
		assertThat(retrievedUser.getId()).isEqualTo(user.getId());
		assertThat(retrievedUser.getName()).isEqualTo(user.getName());
	}

}
