package com.teste.manager.systems.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaisDTOResponse {
	
	private Long id;
	private String nome;
	private String sigla;
	private String gentilico;

}