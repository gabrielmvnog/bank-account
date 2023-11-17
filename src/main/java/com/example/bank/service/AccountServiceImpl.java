package com.example.bank.service;

import org.springframework.stereotype.Service;

import com.example.bank.model.dto.AccountCreateRequestDto;
import com.example.bank.model.dto.AccountDto;

@Service
public class AccountServiceImpl implements AccountService {

	@Override
	public void createAccount(AccountCreateRequestDto account) {
		// TODO Auto-generated method stub

	}

	@Override
	public AccountDto getAccountById(int accountId) {
		return AccountDto.builder().build();	
	}
}
