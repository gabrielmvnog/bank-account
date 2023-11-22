package com.example.bank.model.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCreateRequestDto {
	@NotNull(message = "Account ID is mandatory")
	private Long accountId;

	@NotNull(message = "Operation Type is mandatory")
	private Long operationType;

	@NotNull(message = "Amount is mandatory")
	@Digits(integer = 6, fraction = 2)
	private BigDecimal amount;
}