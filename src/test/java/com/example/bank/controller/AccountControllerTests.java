package com.example.bank.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.example.bank.model.dto.AccountCreateRequestDto;
import com.example.bank.service.AccountService;

@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AccountService accountService;

	@Autowired
	private JacksonTester<AccountCreateRequestDto> jsonAccount;

	@Test
	public void givenPostRequest_whenCreating_thenShouldReturnCreated() throws Exception {
		AccountCreateRequestDto data = AccountCreateRequestDto.builder().documentNumber("123").build();

		MockHttpServletResponse response = mvc.perform(
				post("/accounts").contentType(MediaType.APPLICATION_JSON).content(jsonAccount.write(data).getJson()))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
	}

	@Test
	public void givenPostRequest_whenCreating_thenShouldReturnInternalError() throws Exception {
		doThrow().when(accountService);

		AccountCreateRequestDto data = AccountCreateRequestDto.builder().documentNumber("123").build();
		MockHttpServletResponse response = mvc.perform(
				post("/accounts").contentType(MediaType.APPLICATION_JSON).content(jsonAccount.write(data).getJson()))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	@Test
	public void givenGetRequest_whenCreating_thenShouldReturnOk() throws Exception {
		MockHttpServletResponse response = mvc.perform(get("/accounts/123").contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	public void givenGetRequest_whenCreating_thenShouldReturnInternalError() throws Exception {
		when(accountService.getAccountById(anyInt())).thenThrow();

		MockHttpServletResponse response = mvc.perform(get("/accounts/123").contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
}