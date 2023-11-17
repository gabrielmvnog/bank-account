package com.example.bank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.bank.exception.ConflictException;
import com.example.bank.model.dto.TransactionCreateRequestDto;
import com.example.bank.model.dto.TransactionCreateResponseDto;
import com.example.bank.model.entity.Transaction;
import com.example.bank.repository.TransactionRepository;

@SpringBootTest
class TranscationServiceTests {

	@MockBean
	private TransactionRepository transactionRepository;

	@Autowired
	private TransactionService transactionService;

	@Test
	public void givenCreateTranscation_whenCreating_thenShouldReturnIdentifier() throws Exception {
		TransactionCreateRequestDto transactionCreateRequestDto = TransactionCreateRequestDto.builder().build();

		TransactionCreateResponseDto transactionCreateResponseDto = transactionService.createTransaction(transactionCreateRequestDto);
			
		assertThat(transactionCreateResponseDto.getId()).isNull();
	}

}
