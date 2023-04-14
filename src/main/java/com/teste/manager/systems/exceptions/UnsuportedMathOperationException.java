package com.teste.manager.systems.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class UnsuportedMathOperationException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public UnsuportedMathOperationException(String exception) {
		super(exception);
	}

}
