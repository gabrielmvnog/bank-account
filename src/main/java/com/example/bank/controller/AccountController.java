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
import org.springframework.web.server.ResponseStatusException;

import com.example.bank.exception.ConflictException;
import com.example.bank.exception.NotFoundException;
import com.example.bank.model.dto.AccountCreateRequestDto;
import com.example.bank.model.dto.AccountCreateResponseDto;
import com.example.bank.model.dto.AccountDto;
import com.example.bank.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/accounts")
@SecurityRequirement(name = "Authorization")
@Tag(name = "account", description = "Manage accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Operation(summary = "Add a new account", description = "Add a new account")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Created", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AccountCreateResponseDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request") })
	@PostMapping(consumes = { "application/json" })
	public ResponseEntity<AccountCreateResponseDto> createAccount(@Valid @RequestBody AccountCreateRequestDto account) {
		try {
			AccountCreateResponseDto accountCreateResponseDto = accountService.createAccount(account);

			return new ResponseEntity<AccountCreateResponseDto>(accountCreateResponseDto, HttpStatus.CREATED);
		} catch (ConflictException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@Operation(summary = "Retrieve a account by id", description = "Retrieve a account by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = AccountDto.class)) }),
			@ApiResponse(responseCode = "404", description = "Not Found") })
	@GetMapping("/{accountId}")
	public ResponseEntity<AccountDto> getAccountById(@PathVariable Long accountId) {
		try {
			AccountDto accountDto = accountService.getAccountById(accountId);
			return new ResponseEntity<AccountDto>(accountDto, HttpStatus.OK);
		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
