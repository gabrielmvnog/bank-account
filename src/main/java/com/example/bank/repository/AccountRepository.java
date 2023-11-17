package com.example.bank.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.bank.model.entity.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
