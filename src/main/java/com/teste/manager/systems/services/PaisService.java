package com.teste.manager.systems.services;

import java.util.List;

import com.teste.manager.systems.dtos.PaisDTORequest;
import com.teste.manager.systems.dtos.PaisDTOResponse;
import com.teste.manager.systems.entities.Pais;

public interface PaisService {
	
	List<PaisDTOResponse> findAll();
	PaisDTOResponse save(PaisDTORequest dto);
	PaisDTOResponse alterar(Long id, PaisDTORequest dto);
	List<Pais> findByNomeContains(String nome);
	void delete(Long id);

}
