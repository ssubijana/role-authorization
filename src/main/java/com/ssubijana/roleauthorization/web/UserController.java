package com.ssubijana.roleauthorization.web;

import com.ssubijana.roleauthorization.domain.User;
import com.ssubijana.roleauthorization.mapper.UserMapper;
import com.ssubijana.roleauthorization.service.UserService;
import com.ssubijana.roleauthorization.web.presentation.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUser(@PathVariable long id) {
		final User user = userService.getUser(id);

		UserResponse userResponse = UserMapper.toResponse(user);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

}
