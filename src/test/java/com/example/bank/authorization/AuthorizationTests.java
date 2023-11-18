package com.example.bank.authorization;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.servlet.ServletException;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorizationTests {
	@Autowired
	private MockMvc mvc;

	@Test
	public void givenRequest_whenNotAuthorized_thenShouldReturnAccessDeniedException() throws Exception {
		Exception exception = assertThrows(ServletException.class, () -> {
			mvc.perform(get("/auth/test").header("Authorization", "123")).andReturn().getResponse();
		});
		
		assertThat(exception.getCause().getMessage()).isEqualTo("Access Denied");
	}

	@Test
	public void givenRequest_whenAuthorized_thenShouldReturnResourceNotFound() throws Exception {
		MockHttpServletResponse response = mvc.perform(get("/auth/test").header("Authorization", "pismo123"))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}
}