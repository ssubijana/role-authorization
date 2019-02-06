package com.ssubijana.roleauthorization.mapper;

import com.ssubijana.roleauthorization.domain.User;
import com.ssubijana.roleauthorization.web.presentation.AuthorizationRequest;
import com.ssubijana.roleauthorization.web.presentation.UserResponse;

public class UserMapper {

	private UserMapper() {
	}

	public static UserResponse toResponse(User user) {
		return UserResponse.builder().name(user.getName()).id(user.getId()).build();
	}

	public static User toDomain(AuthorizationRequest authorizationRequest) {
		return User.builder().name(authorizationRequest.getUserName()).password(authorizationRequest.getPassword())
				.build();
	}
}
