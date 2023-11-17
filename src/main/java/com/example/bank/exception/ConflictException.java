package com.example.bank.exception;

public class ConflictException extends Exception {

	public ConflictException(String message) {
        super(message);
    }
	
	private static final long serialVersionUID = 1L;
}