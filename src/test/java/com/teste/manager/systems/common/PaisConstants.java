package com.teste.manager.systems.common;

import com.teste.manager.systems.dtos.PaisDTOResponse;
import com.teste.manager.systems.entities.Pais;

public class PaisConstants {
	
	public static Pais fromDTO() {
		return new Pais(null, "Brasil", "BR", "Brasileiro");
	}
	
	public static PaisDTOResponse fromEntity(Pais pais) {
		return new PaisDTOResponse(pais.getId(), pais.getNome(), pais.getSigla(), pais.getGentilico());
	}

}
