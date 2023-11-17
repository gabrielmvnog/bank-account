package com.example.bank.service;

import com.example.bank.exception.NotFoundException;
import com.example.bank.model.dto.AccountCreateRequestDto;
import com.example.bank.model.dto.AccountCreateResponseDto;
import com.example.bank.model.dto.AccountDto;

public interface AccountService {
	public AccountCreateResponseDto createAccount(AccountCreateRequestDto accountCreateRequestDto) throws Exception;

	public AccountDto getAccountById(Long accountId) throws NotFoundException;
};
