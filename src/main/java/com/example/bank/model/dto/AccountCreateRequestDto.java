package com.example.bank.model.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateRequestDto {

	@NotBlank(message = "Document number is mandatory")
	@Size(min = 11, max = 11)
	private String documentNumber;

	@Builder.Default
	@Positive
	private BigDecimal availableCreditLimit = new BigDecimal("5000.00");
}
