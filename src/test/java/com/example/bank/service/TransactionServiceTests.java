package com.example.bank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.bank.exception.UnprocessableEntityException;
import com.example.bank.model.OperationTypeEnum;
import com.example.bank.model.dto.TransactionCreateRequestDto;
import com.example.bank.model.dto.TransactionCreateResponseDto;
import com.example.bank.model.dto.TransactionDto;
import com.example.bank.model.entity.OperationType;
import com.example.bank.model.entity.Transaction;
import com.example.bank.repository.OperationTypeRepository;
import com.example.bank.repository.TransactionRepository;

@SpringBootTest
class TranscationServiceTests {

	@MockBean
	private TransactionRepository transactionRepository;

	@MockBean
	private OperationTypeRepository operationTypeRepository;

	@Autowired
	private TransactionService transactionService;

	@Test
	public void givenCreateTranscation_whenCreating_thenShouldReturnIdentifier() throws Exception {
		when(operationTypeRepository.findById((long) 1)).thenReturn(Optional.ofNullable(OperationType.builder().id((long) 1).description(OperationTypeEnum.PAYMENT).build()));
		
		TransactionCreateRequestDto transactionCreateRequestDto = TransactionCreateRequestDto.builder().amount(new BigDecimal("10.00")).operationType((long) 1).build();

		TransactionCreateResponseDto transactionCreateResponseDto = transactionService
				.createTransaction(transactionCreateRequestDto);

		assertThat(transactionCreateResponseDto.getId()).isNull();
	}

	@Test
	public void givenInvalidAmount_whenCreating_thenShouldReturnUnprocessable() throws Exception {
		when(operationTypeRepository.findById((long) 1)).thenReturn(Optional.ofNullable(OperationType.builder().id((long) 1).description(OperationTypeEnum.PAYMENT).build()));
		
		TransactionCreateRequestDto transactionCreateRequestDto = TransactionCreateRequestDto.builder().amount(new BigDecimal("-10.00")).operationType((long) 1).build();

		Exception exception = assertThrows(UnprocessableEntityException.class, () -> {
			transactionService
			.createTransaction(transactionCreateRequestDto);
		});

		assertThat(exception.getMessage()).isEqualTo("Unprocesable operation type");
	}

	@Test
	public void givenQuery_whenQuerying_thenShouldReturnTranscationList() throws Exception {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(new Transaction());

		Page<Transaction> pagedResponse = new PageImpl<Transaction>(transactions);

		when(transactionRepository.findAll(any(Pageable.class))).thenReturn(pagedResponse);

		List<TransactionDto> transactionsList = transactionService.query(0, 10);

		assertThat(transactionsList).hasAtLeastOneElementOfType(TransactionDto.class);
	}

	@Test
	public void givenQuery_whenQuerying_thenShouldReturnEmptyList() throws Exception {
		List<Transaction> transactions = new ArrayList<>();
		Page<Transaction> pagedResponse = new PageImpl<Transaction>(transactions);

		when(transactionRepository.findAll(any(Pageable.class))).thenReturn(pagedResponse);

		List<TransactionDto> transactionsList = transactionService.query(0, 10);

		assertThat(transactionsList).isEmpty();
	}
}
