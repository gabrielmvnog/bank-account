package com.example.bank.service;

import java.util.List;

import com.example.bank.exception.UnprocessableEntityException;
import com.example.bank.model.dto.TransactionCreateRequestDto;
import com.example.bank.model.dto.TransactionCreateResponseDto;
import com.example.bank.model.dto.TransactionDto;

public interface TransactionService {
	public TransactionCreateResponseDto createTransaction(TransactionCreateRequestDto transactionCreateRequestDto)
			throws UnprocessableEntityException;

	public List<TransactionDto> query(int offset, int limit);
}