package com.teste.manager.systems.exceptions;

public class InvalidExpirationDateException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidExpirationDateException(String message) {
        super(message);
    }
}
