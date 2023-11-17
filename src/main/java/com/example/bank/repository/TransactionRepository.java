package com.example.bank.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.bank.model.entity.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}