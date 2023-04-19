package com.teste.manager.systems.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.teste.manager.systems.repositories.PaisRepository;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Test Services -> PaisService")
@ExtendWith(MockitoExtension.class)
public class PaisServicesTest {
	
	@InjectMocks
	private PaisServiceImpl service;
	
	@Mock
	private PaisRepository repository;
	
//	public void createPais_WithValidData_ReturnPais() {
//		when(repository.save(fromDTO)).thenReturn(PaisMapper.fromDTO(null));
//		
//		PaisMapper.fromEntity(repository.save(fromDTO);
//		
//	}
	

}
