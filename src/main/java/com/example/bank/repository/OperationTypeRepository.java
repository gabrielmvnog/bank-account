package com.example.bank.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.bank.model.entity.OperationType;

public interface OperationTypeRepository extends CrudRepository<OperationType, Long> {
}
