package com.example.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bank.model.dto.AccountCreateRequestDto;
import com.example.bank.model.dto.AccountCreateResponseDto;
import com.example.bank.model.dto.AccountDto;
import com.example.bank.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping
	public ResponseEntity<?> createAccount(@RequestBody AccountCreateRequestDto account) {
		try {
			accountService.createAccount(account);
			return new ResponseEntity<AccountCreateResponseDto>(AccountCreateResponseDto.builder().id("1").build(),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{accountId}")
	public ResponseEntity<?> getAccountById(@PathVariable int accountId) {
		try {
			AccountDto data = accountService.getAccountById(accountId);
			return new ResponseEntity<AccountDto>(data, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
