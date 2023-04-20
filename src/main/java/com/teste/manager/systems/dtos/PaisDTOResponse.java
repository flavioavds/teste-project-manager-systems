package com.teste.manager.systems.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaisDTOResponse {
	
	private Long id;
	private String nome;
	private String sigla;
	private String gentilico;

}
