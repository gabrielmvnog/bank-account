package com.example.bank.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.example.bank.exception.UnprocessableEntityException;
import com.example.bank.model.OperationTypeEnum;
import com.example.bank.model.dto.TransactionCreateRequestDto;
import com.example.bank.model.dto.TransactionCreateResponseDto;
import com.example.bank.model.dto.TransactionDto;
import com.example.bank.model.entity.OperationType;
import com.example.bank.model.entity.Transaction;
import com.example.bank.repository.OperationTypeRepository;
import com.example.bank.repository.TransactionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private OperationTypeRepository operationTypeRepository;

	private void processOperation(TransactionCreateRequestDto transactionCreateRequestDto)
			throws UnprocessableEntityException {
		Optional<OperationType> operationType = operationTypeRepository
				.findById(transactionCreateRequestDto.getOperationType());

		int compared = transactionCreateRequestDto.getAmount().compareTo(new BigDecimal("0.0"));

		if ((operationType.isEmpty())
				|| (operationType.get().getDescription().equals(OperationTypeEnum.PAYMENT) && compared < 0)
				|| (!operationType.get().getDescription().equals(OperationTypeEnum.PAYMENT) && compared > 0)) {

			log.warn("Can not proccess operation: " + operationType.toString() + " for transaction: "
					+ transactionCreateRequestDto.toString());

			throw new UnprocessableEntityException("Unprocesable operation type");
		}

	}

	@Override
	public TransactionCreateResponseDto createTransaction(TransactionCreateRequestDto transactionCreateRequestDto)
			throws UnprocessableEntityException {
		log.debug("Creating transaction with payload: " + transactionCreateRequestDto.toString());

		processOperation(transactionCreateRequestDto);

		Transaction transaction = Transaction.builder().accountId(transactionCreateRequestDto.getAccountId())
				.operationType(transactionCreateRequestDto.getOperationType())
				.amount(transactionCreateRequestDto.getAmount()).build();

		transactionRepository.save(transaction);

		log.debug("Successfully created transcation: " + transactionCreateRequestDto.toString());

		return TransactionCreateResponseDto.builder().id(transaction.getId()).build();
	}

	@Override
	public List<TransactionDto> query(int offset, int limit) {
		Pageable pageable = PageRequest.of(offset, limit);
		List<Transaction> allTransactions = transactionRepository.findAll(pageable).getContent();

		return allTransactions.stream()
				.map(transaction -> TransactionDto.builder().id(transaction.getId())
						.accountId(transaction.getAccountId()).operationType(transaction.getOperationType())
						.amount(transaction.getAmount()).build())
				.collect(Collectors.toList());
	}

}
