package com.example.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bank.model.dto.TransactionCreateRequestDto;
import com.example.bank.model.dto.TransactionCreateResponseDto;
import com.example.bank.model.entity.Transaction;
import com.example.bank.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public TransactionCreateResponseDto createTransaction(TransactionCreateRequestDto transactionCreateRequestDto) {
		Transaction transaction = Transaction.builder().accountId(transactionCreateRequestDto.getAccountId())
				.operationType(transactionCreateRequestDto.getOperationType())
				.amount(transactionCreateRequestDto.getAmount()).build();

		transactionRepository.save(transaction);

		return TransactionCreateResponseDto.builder().id(transaction.getId()).build();
	}

}
