package com.example.bank.service;

import com.example.bank.model.dto.AccountCreateRequestDto;
import com.example.bank.model.dto.AccountDto;

public interface AccountService {
	public void createAccount(AccountCreateRequestDto account);
	public AccountDto getAccountById(int accountId);
};
