package com.teste.manager.systems.exceptions;

public class InvalidTokenException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
