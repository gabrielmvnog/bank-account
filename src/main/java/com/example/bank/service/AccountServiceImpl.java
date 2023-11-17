package com.example.bank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.bank.exception.ConflictException;
import com.example.bank.exception.NotFoundException;
import com.example.bank.model.dto.AccountCreateRequestDto;
import com.example.bank.model.dto.AccountCreateResponseDto;
import com.example.bank.model.dto.AccountDto;
import com.example.bank.model.entity.Account;
import com.example.bank.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public AccountCreateResponseDto createAccount(AccountCreateRequestDto accountCreateRequestDto) throws Exception {
		Account account = Account.builder().documentNumber(accountCreateRequestDto.getDocumentNumber()).build();

		try {
			accountRepository.save(account);
		} catch (DataIntegrityViolationException e) {
			if (e.getMessage().contains("duplicate key")) {
				throw new ConflictException("Account already exists");
			}

			throw e;
		}

		return AccountCreateResponseDto.builder().build();
	}

	@Override
	public AccountDto getAccountById(Long accountId) throws NotFoundException {
		Optional<Account> account = accountRepository.findById(accountId);

		if (account.isEmpty()) {
			throw new NotFoundException("Account not found");
		}

		return AccountDto.builder().id(account.get().getId()).documentNumber(account.get().getDocumentNumber()).build();
	}
}
