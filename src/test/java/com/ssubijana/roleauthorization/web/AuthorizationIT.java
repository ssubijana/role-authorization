package com.ssubijana.roleauthorization.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssubijana.roleauthorization.utils.Constants;
import com.ssubijana.roleauthorization.web.presentation.AuthorizationRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationIT {

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldAuthorizeUser() throws Exception {
		AuthorizationRequest request = AuthorizationRequest.builder().userName("userTest").password("password1")
				.build();
		final MvcResult mvcResult = mockMvc.perform(
				post("/login").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		assertThat(mvcResult.getResponse().getHeader(Constants.HEADER_AUTHORIZATION_KEY)).isNotBlank();
	}

	@Test
	public void shouldNotAuthorizeUser() throws Exception {
		AuthorizationRequest request = AuthorizationRequest.builder().userName("userTest").password("password2")
				.build();
		mockMvc.perform(
				post("/login").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andDo(print()).andExpect(status().isForbidden());
	}

	@Test
	public void shouldAuthorizeAdminUserToGetUserInfo() throws Exception {
		AuthorizationRequest request = AuthorizationRequest.builder().userName("userTest").password("password1")
				.build();
		final MvcResult mvcResult = mockMvc.perform(
				post("/login").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		String token = mvcResult.getResponse().getHeader(Constants.HEADER_AUTHORIZATION_KEY);

		mockMvc.perform(
				get("/users/1").header(Constants.HEADER_AUTHORIZATION_KEY, token))
				.andDo(print()).andExpect(status().isOk()).andReturn();
	}

	@Test
	public void shouldNotAuthorizeUserToGetUserInfo() throws Exception {
		AuthorizationRequest request = AuthorizationRequest.builder().userName("userTest_2").password("password2")
				.build();
		final MvcResult mvcResult = mockMvc.perform(
				post("/login").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		String token = mvcResult.getResponse().getHeader(Constants.HEADER_AUTHORIZATION_KEY);

		mockMvc.perform(
				get("/users/1").header(Constants.HEADER_AUTHORIZATION_KEY, token))
				.andDo(print()).andExpect(status().isForbidden()).andReturn();
	}
}
