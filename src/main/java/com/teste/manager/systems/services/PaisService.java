package com.teste.manager.systems.services;

import java.util.List;
import java.util.Locale;

import com.teste.manager.systems.dtos.PaisDTORequest;
import com.teste.manager.systems.dtos.PaisDTOResponse;
import com.teste.manager.systems.entities.Pais;

public interface PaisService {
	
	List<PaisDTOResponse> findAll();
	PaisDTOResponse save(PaisDTORequest dto, Locale locale);
	List<Pais> findByNomeContains(String nome);
	void delete(Long id, Locale locale);

}
