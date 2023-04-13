package com.teste.manager.systems.services;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.manager.systems.dtos.PaisDTORequest;
import com.teste.manager.systems.dtos.PaisDTOResponse;
import com.teste.manager.systems.dtos.PaisMapper;
import com.teste.manager.systems.entities.Pais;
import com.teste.manager.systems.repositories.PaisRepository;

@Service
public class PaisServiceImpl implements PaisService{
	
	@Autowired
	private final PaisRepository paisRepository;
	
	public PaisServiceImpl(PaisRepository paisRepository) {
		this.paisRepository = paisRepository;
	}

	@Override
	public List<PaisDTOResponse> findAll() {
		return paisRepository.findAll()
				.stream()
				.map(PaisMapper::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public PaisDTOResponse save(PaisDTORequest dto, Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pais> findByNomeContains(String nome) {
		return paisRepository.findByNomeContains(nome);
	}

	@Override
	public void delete(Long id, Locale locale) {
		// TODO Auto-generated method stub
		
	}

}
