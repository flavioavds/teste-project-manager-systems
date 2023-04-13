package com.teste.manager.systems.dtos;

import com.teste.manager.systems.entities.Pais;

public class PaisMapper {
	
	public static Pais fromDTO(PaisDTORequest dto) {
		return new Pais(null, dto.getNome(), dto.getSigla(), dto.getGentilico());
	}
	
	public static PaisDTOResponse fromEntity(Pais pais) {
		return new PaisDTOResponse(pais.getId(), pais.getNome(), pais.getSigla(), pais.getGentilico());
	}

}
