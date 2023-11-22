package com.example.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.bank.exception.UnprocessableEntityException;
import com.example.bank.model.dto.TransactionCreateRequestDto;
import com.example.bank.model.dto.TransactionCreateResponseDto;
import com.example.bank.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/transactions")
@SecurityRequirement(name = "Authorization")
@Tag(name = "transaction", description = "Manage transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@Operation(summary = "Add a new transaction", description = "Add a new transaction")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Created", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = TransactionCreateResponseDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity") })
	@PostMapping
	public ResponseEntity<TransactionCreateResponseDto> createTransaction(
			@Valid @RequestBody TransactionCreateRequestDto transaction) {
		try {
			TransactionCreateResponseDto transactionCreateResponseDto = transactionService
					.createTransaction(transaction);

			return new ResponseEntity<TransactionCreateResponseDto>(transactionCreateResponseDto, HttpStatus.CREATED);
		} catch (UnprocessableEntityException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
