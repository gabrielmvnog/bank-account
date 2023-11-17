package com.example.bank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bank.exception.UnprocessableEntityException;
import com.example.bank.model.OperationTypeEnum;
import com.example.bank.model.dto.TransactionCreateRequestDto;
import com.example.bank.model.dto.TransactionCreateResponseDto;
import com.example.bank.model.entity.OperationType;
import com.example.bank.model.entity.Transaction;
import com.example.bank.repository.OperationTypeRepository;
import com.example.bank.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private OperationTypeRepository operationTypeRepository;

	private void processOperation(TransactionCreateRequestDto transactionCreateRequestDto)
			throws UnprocessableEntityException {
		Optional<OperationType> operationType = operationTypeRepository
				.findById(transactionCreateRequestDto.getOperationType());

		if ((operationType.isEmpty())
				|| (operationType.get().getDescription().equals(OperationTypeEnum.PAYMENT)
						&& transactionCreateRequestDto.getAmount() < 0)
				|| (!operationType.get().getDescription().equals(OperationTypeEnum.PAYMENT)
						&& transactionCreateRequestDto.getAmount() > 0)) {
			throw new UnprocessableEntityException("Unprocesable operation type");
		}

	}

	@Override
	public TransactionCreateResponseDto createTransaction(TransactionCreateRequestDto transactionCreateRequestDto)
			throws UnprocessableEntityException {
		processOperation(transactionCreateRequestDto);

		Transaction transaction = Transaction.builder().accountId(transactionCreateRequestDto.getAccountId())
				.operationType(transactionCreateRequestDto.getOperationType())
				.amount(transactionCreateRequestDto.getAmount()).build();

		transactionRepository.save(transaction);

		return TransactionCreateResponseDto.builder().id(transaction.getId()).build();
	}

}
