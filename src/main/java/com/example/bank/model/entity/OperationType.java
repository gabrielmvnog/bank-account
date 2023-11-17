package com.example.bank.model.entity;

import com.example.bank.model.OperationTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "operation_type")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationType {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "description", nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private OperationTypeEnum description;
}
