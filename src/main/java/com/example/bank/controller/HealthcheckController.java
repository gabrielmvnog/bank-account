package com.example.bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bank.model.dto.HealthResponseDto;


@RestController
public class HealthcheckController{
	
	@GetMapping("/health")
	public ResponseEntity<HealthResponseDto> getHealth() {
		HealthResponseDto response = HealthResponseDto.builder().status("Ok").build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}