package com.example.bank.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
import com.example.bank.model.dto.TransactionCreateRequestDto;
import com.example.bank.service.TransactionService;

@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private TransactionService transactionService;

	@Autowired
	private JacksonTester<TransactionCreateRequestDto> jsonTransaction;

	@Test
	public void givenPostRequest_whenCreating_thenShouldReturnCreated() throws Exception {
		TransactionCreateRequestDto data = TransactionCreateRequestDto.builder().build();

		MockHttpServletResponse response = mvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
				.content(jsonTransaction.write(data).getJson())).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
	}

	@Test
	public void givenPostRequest_whenCreating_thenShouldReturnInternalError() throws Exception {
		doThrow().when(transactionService);

		TransactionCreateRequestDto data = TransactionCreateRequestDto.builder().build();
		MockHttpServletResponse response = mvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
				.content(jsonTransaction.write(data).getJson())).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

}