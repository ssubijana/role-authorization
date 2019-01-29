package com.ssubijana.roleauthorization.web;

import com.ssubijana.roleauthorization.web.presentation.AuthorizationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthorizationController {

	@PostMapping("/token")
	public ResponseEntity requestAuthorization(@RequestBody AuthorizationRequest authorizationRequest) {
		return ResponseEntity.ok().build();
	}

}
