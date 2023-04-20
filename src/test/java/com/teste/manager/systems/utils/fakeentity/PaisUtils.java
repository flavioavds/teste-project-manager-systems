package com.teste.manager.systems.utils.fakeentity;

import com.teste.manager.systems.dtos.PaisDTORequest;
import com.teste.manager.systems.dtos.PaisDTOResponse;
import com.teste.manager.systems.entities.Pais;

public class PaisUtils {
	
	public static final Long ID = 10L;
	public static String NOME = "Paraguai";
	public static String SIGLA = "PY";
	public static String GENTILICO = "Paraguaio";
	
	public static Pais createFakeEntity() {
		return new Pais(
				ID, 
				NOME, 
				SIGLA, 
				GENTILICO);
	}
	
	public static PaisDTORequest createFakePaisDTORequest() {
		return new PaisDTORequest(NOME, SIGLA, GENTILICO);
	}
	
	public static PaisDTOResponse createFakePaisDTOResponse() {
		return new PaisDTOResponse(ID, NOME, SIGLA, GENTILICO);
	}
	
	public static Pais createFakeInvalid() {
		return new Pais(null, null, null, null);
	}
	
	public static Pais createFakeEmpty() {
		return new Pais(null, "", "", "");
	}

}
