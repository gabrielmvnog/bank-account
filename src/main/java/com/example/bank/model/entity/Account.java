package com.example.bank.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ColumnTransformer(read = """
			pgp_sym_decrypt(
			    document_number,
			    current_setting('encrypt.key')
			)
			""", write = """
			pgp_sym_encrypt(
			    ?,
			    current_setting('encrypt.key')
			)
			""")
	@Column(name = "document_number", unique = true, nullable = false, updatable = false, columnDefinition = "bytea")
	private String documentNumber;

	@Column(name = "available_credit_limit", nullable = false)
	private BigDecimal availableCreditLimit;

	@PrePersist
	private void fillHashedDocumentNumber() {
		this.hashedDocumentNumber = this.documentNumber;
	}

	@ColumnTransformer(write = """
			digest(
			    ?,
			    'sha256'
			)
			""")
	@Column(name = "hashed_document_number", unique = true, nullable = false, updatable = false, columnDefinition = "bytea")
	private String hashedDocumentNumber;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
}
