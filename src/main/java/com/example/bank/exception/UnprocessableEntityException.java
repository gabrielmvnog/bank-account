package com.example.bank.exception;

public class UnprocessableEntityException extends Exception {

	public UnprocessableEntityException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;
}