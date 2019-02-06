package com.ssubijana.roleauthorization.service;

import com.ssubijana.roleauthorization.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	User getUser(long id);

	User save(User user);
}
