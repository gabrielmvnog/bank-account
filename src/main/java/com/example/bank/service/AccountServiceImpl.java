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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public AccountCreateResponseDto createAccount(AccountCreateRequestDto accountCreateRequestDto) throws Exception {
		log.debug("Creating account with payload: " + accountCreateRequestDto.toString());

		Account account = Account.builder().documentNumber(accountCreateRequestDto.getDocumentNumber()).build();

		try {
			accountRepository.save(account);
		} catch (DataIntegrityViolationException e) {

			if (e.getMessage().contains("duplicate key")) {
				log.warn("");
				throw new ConflictException("Account already exists");
			}

			log.error("Error trying to create new account, error: " + e.toString());

			throw e;
		}

		log.debug("Successfully created account: " + accountCreateRequestDto.toString());

		return AccountCreateResponseDto.builder().id(account.getId()).build();
	}

	@Override
	public AccountDto getAccountById(Long accountId) throws NotFoundException {
		log.debug("Querying account with id: " + accountId.toString());

		Optional<Account> account = accountRepository.findById(accountId);

		if (account.isEmpty()) {
			log.warn("Account with id: " + accountId.toString() + " not found");
			throw new NotFoundException("Account not found");
		}

		log.debug("Successfully queried account with id: " + accountId.toString());

		return AccountDto.builder().id(account.get().getId()).documentNumber(account.get().getDocumentNumber()).build();
	}
}
