package com.teste.manager.systems.payload.request;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
	
	private String login;	
	private String senha;
	private String nome;
	private Set<String> administrador;

}
