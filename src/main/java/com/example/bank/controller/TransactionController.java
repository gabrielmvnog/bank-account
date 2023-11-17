package com.example.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bank.exception.UnprocessableEntityException;
import com.example.bank.model.dto.TransactionCreateRequestDto;
import com.example.bank.model.dto.TransactionCreateResponseDto;
import com.example.bank.service.TransactionService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/transactions")
@SecurityRequirement(name = "Authorization")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping
	public ResponseEntity<?> createTransaction(@RequestBody TransactionCreateRequestDto transaction) {
		try {
			TransactionCreateResponseDto transactionCreateResponseDto = transactionService
					.createTransaction(transaction);

			return new ResponseEntity<TransactionCreateResponseDto>(transactionCreateResponseDto, HttpStatus.CREATED);
		} catch (UnprocessableEntityException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
