package com.example.bank.service;

import com.example.bank.exception.UnprocessableEntityException;
import com.example.bank.model.dto.TransactionCreateRequestDto;
import com.example.bank.model.dto.TransactionCreateResponseDto;

public interface TransactionService {
	public TransactionCreateResponseDto createTransaction(TransactionCreateRequestDto transactionCreateRequestDto) throws UnprocessableEntityException;
}