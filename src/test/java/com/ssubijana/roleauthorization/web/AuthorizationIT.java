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
		String token = getToken("userTest", "password1");

		mockMvc.perform(
				get("/users/1").header(Constants.HEADER_AUTHORIZATION_KEY, token))
				.andDo(print()).andExpect(status().isOk()).andReturn();
	}

	private String getToken(String userTest, String password1) throws Exception {
		AuthorizationRequest request = AuthorizationRequest.builder().userName(userTest).password(password1)
				.build();
		final MvcResult mvcResult = mockMvc.perform(
				post("/login").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		return mvcResult.getResponse().getHeader(Constants.HEADER_AUTHORIZATION_KEY);
	}

	@Test
	public void shouldAuthorizeUserToGetUserInfo() throws Exception {
		String token = getToken("userTest_2", "password2");

		mockMvc.perform(
				get("/users/1").header(Constants.HEADER_AUTHORIZATION_KEY, token))
				.andDo(print()).andExpect(status().isOk()).andReturn();
	}

	@Test
	public void shouldNotAuthorizeOperationalUserToGetUserInfo() throws Exception {
		String token = getToken("userTest_3", "password2");

		mockMvc.perform(
				get("/users/1").header(Constants.HEADER_AUTHORIZATION_KEY, token))
				.andDo(print()).andExpect(status().isForbidden()).andReturn();
	}

	@Test
	public void shouldAuthorizeAdminUserToSave() throws Exception {
		String token = getToken("userTest", "password1");

		AuthorizationRequest request = AuthorizationRequest.builder().userName("userTest_4").password("password4")
				.build();
		mockMvc.perform(
				post("/users").header(Constants.HEADER_AUTHORIZATION_KEY, token).contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(request)))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void shouldNotAuthorizeOperationalUserToSave() throws Exception {
		String token = getToken("userTest_3", "password2");

		AuthorizationRequest request = AuthorizationRequest.builder().userName("userTest_4").password("password4")
				.build();
		mockMvc.perform(
				post("/users").header(Constants.HEADER_AUTHORIZATION_KEY, token).contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(request)))
				.andDo(print()).andExpect(status().isForbidden()).andReturn();
	}

}
