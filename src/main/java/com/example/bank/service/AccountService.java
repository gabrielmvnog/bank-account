package com.example.bank.service;

import com.example.bank.model.dto.AccountCreateRequestDto;

public interface AccountService {
	public void createAccount(AccountCreateRequestDto account);
};
