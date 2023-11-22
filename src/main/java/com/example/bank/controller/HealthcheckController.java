package com.example.bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bank.model.dto.HealthResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "healthcheck", description = "Check status of the service")
public class HealthcheckController {

	@Operation(summary = "Retrive health of the service", description = "Retrive health of the service")
	@ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = HealthResponseDto.class)) })
	@GetMapping("/health")
	public ResponseEntity<HealthResponseDto> getHealth() {
		HealthResponseDto response = HealthResponseDto.builder().status("Ok").build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}