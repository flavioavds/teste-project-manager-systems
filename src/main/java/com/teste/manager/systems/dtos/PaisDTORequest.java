package com.teste.manager.systems.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "PostDTO País")
public class PaisDTORequest {
	
	@Schema(description = "Uruguai")
	@Size(max = 300)
	@NotBlank(message = "Nome não pode ser Vazio")
	private String nome;
	
	@Schema(description = "UY")
	@Size(max = 3)
	@NotBlank(message = "Sigla não pode ser Vazia")
	private String sigla;
	
	@Schema(description = "Uruguaio")
	@Size(max = 300)
	@NotBlank(message = "Nacionalidade não pode ser em Branco")
	private String gentilico;

}
