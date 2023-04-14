package com.teste.manager.systems.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaisException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	private String message;

	public PaisException(String message) {
		super(message);
		this.message = message;
	}
	
	

}
