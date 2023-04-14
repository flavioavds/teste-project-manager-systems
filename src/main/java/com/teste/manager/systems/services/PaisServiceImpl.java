package com.teste.manager.systems.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.teste.manager.systems.dtos.PaisDTORequest;
import com.teste.manager.systems.dtos.PaisDTOResponse;
import com.teste.manager.systems.dtos.PaisMapper;
import com.teste.manager.systems.entities.Pais;
import com.teste.manager.systems.exceptions.DatabaseException;
import com.teste.manager.systems.repositories.PaisRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PaisServiceImpl implements PaisService{
	
	@Autowired
	private final PaisRepository paisRepository;
	
	public PaisServiceImpl(PaisRepository paisRepository) {
		super();
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
	public List<Pais> findByNomeContains(String nome) {
		return paisRepository.findByNomeContains(nome);
	}

	@Override
	public PaisDTOResponse save(PaisDTORequest dto) {
		paisValidated(dto.getNome());
		paisValidated(dto.getSigla());
		paisValidated(dto.getGentilico());
		return PaisMapper.fromEntity(paisRepository.save(PaisMapper.fromDTO(dto)));
	}

	@Override
	public PaisDTOResponse alterar(Long id, PaisDTORequest dto) {
		Pais original = paisVerify(id);
		paisValidated(dto.getNome());
		original.setNome(dto.getNome());
		paisValidated(dto.getSigla());
		original.setSigla(dto.getSigla());
		paisValidated(dto.getGentilico());
		original.setGentilico(dto.getGentilico());
		
		return PaisMapper.fromEntity(paisRepository.save(original));
	}

	@Override
	public void delete(Long id) {
		try {
			Optional<Pais> optional = paisRepository.findById(id);
			optional.orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado!"));
			paisRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de Integridade!");
		}
		
	}
	
	private Pais paisVerify(Long id) {
		Optional<Pais> optional = paisRepository.findById(id);
		return optional.orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));
	}
	
	private void paisValidated(String nome) {
		try {
			Optional<Pais> optional = paisRepository.findByNomeIgnoreCase(nome);
			if(optional.isPresent()) {
				throw new EntityNotFoundException("Usuario já existente");
			}
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Usuario já existente!");
		}
	}


}
