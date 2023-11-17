package com.example.bank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.bank.exception.ConflictException;
import com.example.bank.exception.NotFoundException;
import com.example.bank.model.dto.AccountCreateRequestDto;
import com.example.bank.model.dto.AccountCreateResponseDto;
import com.example.bank.model.dto.AccountDto;
import com.example.bank.model.entity.Account;
import com.example.bank.repository.AccountRepository;

@SpringBootTest
class AccountServiceTests {

	@MockBean
	private AccountRepository accountRepository;

	@Autowired
	private AccountService accountService;

	@Test
	public void givenCreateAccount_whenCreating_thenShouldReturnIdentifier() throws Exception {
		AccountCreateRequestDto accountCreateRequestDto = AccountCreateRequestDto.builder().documentNumber("123")
				.build();

		AccountCreateResponseDto accountCreateResponseDto = accountService.createAccount(accountCreateRequestDto);

		assertThat(accountCreateResponseDto.getId()).isNull();
	}

	@Test
	public void givenCreateAccount_whenDocumentAlreadyExists_thenShouldRaiseConflict() throws Exception {
		when(accountRepository.save(any(Account.class))).thenThrow(new DataIntegrityViolationException("duplicate key value violates unique constraint"));
		
		AccountCreateRequestDto accountCreateRequestDto = AccountCreateRequestDto.builder().documentNumber("123").build();

		Exception exception = assertThrows(ConflictException.class, () -> {
			accountService.createAccount(accountCreateRequestDto);
		});

		assertThat(exception.getMessage()).isEqualTo("Account already exists");
	}

	@Test
	public void givenCreateAccount_whenSaveBreaks_thenShouldRaiseError() throws Exception {
		when(accountRepository.save(any(Account.class))).thenThrow(new DataIntegrityViolationException("Random Error"));
		
		AccountCreateRequestDto accountCreateRequestDto = AccountCreateRequestDto.builder().documentNumber("123").build();

		Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
			accountService.createAccount(accountCreateRequestDto);
		});

		assertThat(exception.getMessage()).isEqualTo("Random Error");
	}

	@Test
	public void givenGetAccount_whenSuccess_thenShouldReturnAccount() throws Exception {
		Optional<Account> account = Optional.ofNullable(Account.builder().id((long) 1).build());

		when(accountRepository.findById(anyLong())).thenReturn(account);

		AccountDto accountCreateResponseDto = accountService.getAccountById((long) 1);

		assertThat(accountCreateResponseDto.getId()).isEqualTo((long) 1);
	}

	@Test
	public void givenGetAccount_whenNotFound_thenShouldRaiseNotFound() throws Exception {
		when(accountRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

		Exception exception = assertThrows(NotFoundException.class, () -> {
			accountService.getAccountById((long) 1);
		});

		assertThat(exception.getMessage()).isEqualTo("Account not found");
	}
}
