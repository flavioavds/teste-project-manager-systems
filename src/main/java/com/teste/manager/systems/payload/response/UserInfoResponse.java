package com.teste.manager.systems.payload.response;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInfoResponse {
	
	private Long id;
	private String login;	
	private String nome;
	private Set<String> administrador;

}
