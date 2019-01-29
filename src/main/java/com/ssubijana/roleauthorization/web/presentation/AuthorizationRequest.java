package com.ssubijana.roleauthorization.web.presentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
public class AuthorizationRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("user")
	private String userName;

	private String password;

	public AuthorizationRequest() {
	}
}
