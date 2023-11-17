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

import com.example.bank.exception.ConflictException;
import com.example.bank.exception.NotFoundException;
import com.example.bank.model.dto.AccountCreateRequestDto;
import com.example.bank.model.dto.AccountCreateResponseDto;
import com.example.bank.model.dto.AccountDto;
import com.example.bank.service.AccountService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/accounts")
@SecurityRequirement(name = "Authorization")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping
	public ResponseEntity<?> createAccount(@RequestBody AccountCreateRequestDto account) {
		try {
			AccountCreateResponseDto accountCreateResponseDto = accountService.createAccount(account);

			return new ResponseEntity<AccountCreateResponseDto>(accountCreateResponseDto, HttpStatus.CREATED);
		} catch (ConflictException e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{accountId}")
	public ResponseEntity<?> getAccountById(@PathVariable Long accountId) {
		try {
			AccountDto accountDto = accountService.getAccountById(accountId);
			return new ResponseEntity<AccountDto>(accountDto, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
