package com.teste.manager.systems.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.teste.manager.systems.dtos.PaisDTORequest;
import com.teste.manager.systems.dtos.PaisDTOResponse;
import com.teste.manager.systems.entities.Pais;
import com.teste.manager.systems.repositories.PaisRepository;
import com.teste.manager.systems.utils.fakeentity.PaisUtils;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Test Services -> PaisService")
@ExtendWith(MockitoExtension.class)
public class PaisServicesTest {
	
	private Pais pais;
	private PaisDTORequest paisDTORequest;
	private PaisDTOResponse paisDTOResponse;
	
	private static final Long ID = 10L;
	private static final String NOME = "Paraguai";
	private static final String SIGLA = "PY";
	private static final String GENTILICO = "Paraguaio";
	
	@InjectMocks
	@Spy
	private PaisServiceImpl service;
	
	@Mock
	private PaisRepository repository;
		
	@BeforeEach
	void setup() {
		pais = PaisUtils.createFakeEntity();
		paisDTORequest = PaisUtils.createFakePaisDTORequest();
		paisDTOResponse = PaisUtils.createFakePaisDTOResponse();
		
		service = new PaisServiceImpl(repository);
	}
	
	@Test
	@DisplayName("1. Quando procurar por os país, deve retornar uma lista PaisDTOResponse")
	void whenFindByAllThenShouldReturnPaisDTOResponse() {
		List<Pais> paisEntityList = new ArrayList<>();
		paisEntityList.add(pais);
		
		when(repository.findAll()).thenReturn(paisEntityList);
		
		List<PaisDTOResponse> result = service.findAll();
		
		assertNotNull(result);
		assertInstanceOf(List.class, result);
		assertEquals(paisDTOResponse, result.get(0));
		
	}
	
	@Test
	@DisplayName("2. Quando procurar pelo nome, deve retornar uma lista PaisDTOResponse")
	void whenFindByNomeThenShouldReturnNomePaisContainsDTOResponse() {
		
		when(repository.findByNomeContains(NOME)).thenReturn(List.of(pais));
		
		List<Pais> p = service.findByNomeContains(NOME);
		
		assertNotNull(p);
		assertEquals(ID, pais.getId());
		assertEquals(NOME, pais.getNome());
		assertEquals(SIGLA, pais.getSigla());
		assertEquals(GENTILICO, pais.getGentilico());
	}
	
	@Test
	@DisplayName("3. Quando criar pais pelo nome, deve retornar uma lista PaisDTOResponse")
	void whenCreatePaisThenShouldReturnNomePaisContainsDTOResponse() {
		
		when(repository.save(any(Pais.class))).thenReturn(pais);
		
		paisDTOResponse = service.save(paisDTORequest);
		assertAll(
				"Check if pais saved and PaisDTOResponse was returned"
				, () -> verify(repository,times(1)).save(any(Pais.class))
				, () -> assertNotNull(paisDTOResponse)
				, () -> assertInstanceOf(PaisDTOResponse.class, paisDTOResponse)
				, () -> assertEquals(ID, pais.getId())
				, () -> assertEquals(NOME, pais.getNome())
				, () -> assertEquals(SIGLA, pais.getSigla())
				, () -> assertEquals(GENTILICO, pais.getGentilico())
				);
	}
	
	@Test
	@DisplayName("4. Quando atualizar pais pelo nome, deve retornar uma lista PaisDTOResponse")
	void whenUpdateExistengPaisThenShouldReturnNomePaisContainsDTOResponse() {
		
		when(repository.save(any(Pais.class))).thenReturn(pais);
		when(repository.findById(ID)).thenReturn(Optional.of(pais));
		
		paisDTOResponse = service.alterar(ID, paisDTORequest);
		assertAll(
				"Check if pais updated and PaisDTOResponse was returned"
				, () -> verify(repository,times(1)).save(any(Pais.class))
				, () -> assertNotNull(paisDTOResponse)
				, () -> assertInstanceOf(PaisDTOResponse.class, paisDTOResponse)
				, () -> assertEquals(ID, pais.getId())
				, () -> assertEquals(NOME, pais.getNome())
				, () -> assertEquals(SIGLA, pais.getSigla())
				, () -> assertEquals(GENTILICO, pais.getGentilico())
				);
	}

}
