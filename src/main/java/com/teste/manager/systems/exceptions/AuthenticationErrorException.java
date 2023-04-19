package com.teste.manager.systems.exceptions;

import java.io.Serializable;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationErrorException extends AuthenticationException implements Serializable{
    private static final long serialVersionUID = 1L;

	public AuthenticationErrorException(String message) {
        super(message);
    }
}
