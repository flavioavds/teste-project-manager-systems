package com.teste.manager.systems.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste.manager.systems.dtos.PaisDTORequest;
import com.teste.manager.systems.dtos.PaisDTOResponse;
import com.teste.manager.systems.services.PaisServiceImpl;
import com.teste.manager.systems.utils.fakeentity.PaisUtils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("PaisController Test")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class PaisControllerTest {
	
	@Mock
	private PaisServiceImpl paisService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private PaisDTORequest paisDTORequest;
	private PaisDTOResponse paisDTOResponse;
	
	public PaisControllerTest() {
		
	}
	
	@BeforeEach
	void setup() {
		paisDTORequest = PaisUtils.createFakePaisDTORequest();
		paisDTOResponse = PaisUtils.createFakePaisDTOResponse();
		objectMapper = new ObjectMapper();
		
		mockMvc = MockMvcBuilders.standaloneSetup(new PaisController(paisService))
					.build();
	}
	
	@Test
	@DisplayName("1. Should create a new País")
	void whenCreateThenReturnCreated() {
		when(paisService.save(any(PaisDTORequest.class))).thenReturn(paisDTOResponse);
		try {
			mockMvc.perform(post("/v1/pais/salvar")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(paisDTORequest))
					)
			.andExpect(status().isCreated());
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("1.2 When try create with exiting name should return Conflict")
	void whenCreateWhithExistingThenReturnConflict() {
		when(paisService.save(any(PaisDTORequest.class))).thenThrow(ValidationException.class);
		try {
			mockMvc.perform(post("/v1/pais/salvar")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(paisDTORequest))
					)
			.andExpect(status().isBadRequest());
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("2. When find all pais should return a list of PaisDTOResponse")
	void whenFindByAll() {
		when(paisService.findAll()).thenReturn(List.of(paisDTOResponse));
		try {
			mockMvc.perform(get("/v1/pais"))
					.andExpect(status().isOk());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("3. When find all pais should return a Empty list")
	void whenFindByAllThenReturnEmptyList() {
		when(paisService.findAll()).thenReturn(Collections.emptyList());
		try {
			mockMvc.perform(get("/v1/pais"))
					.andExpect(status().isOk());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	@DisplayName("4. When try update pais data with Unexisting id should return NotFound")
	void whenUpdatePaisDataByUnexistingIdThenReturNotFound() {
		doThrow(EntityNotFoundException.class).when(paisService)
		.alterar(any(Long.class), any(PaisDTORequest.class));
		try {
			mockMvc.perform(post("/v1/pais/salvar/{id}", paisDTOResponse.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(paisDTORequest))
					)
			.andExpect(status().isNotFound());
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("5. When try update pais data with existing id should return PaisDTOResponse")
	void whenUpdatePaisDataByExistingIdThenReturnOk() {
		when(paisService.alterar(any(Long.class), any(PaisDTORequest.class))).thenReturn(paisDTOResponse);
		try {
			mockMvc.perform(post("/v1/pais/salvar/{id}", paisDTOResponse.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(paisDTORequest))
					)
			.andExpect(status().isOk());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
