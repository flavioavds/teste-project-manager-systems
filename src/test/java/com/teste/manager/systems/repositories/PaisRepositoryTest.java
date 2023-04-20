package com.teste.manager.systems.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.teste.manager.systems.entities.Pais;
import com.teste.manager.systems.utils.fakeentity.PaisUtils;

@DataJpaTest
public class PaisRepositoryTest {
	
	@Autowired
	private PaisRepository repository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	private Pais PAIS = PaisUtils.createFakeEntity();
	private Pais INVALID_PAIS = PaisUtils.createFakeInvalid();
	private Pais EMPTY_PAIS = PaisUtils.createFakeEmpty();
	
	@BeforeEach
	public void setup() {
		PAIS.setId(null);
	}
	
	@Test
	public void createPais_ReturnPais() {
		Pais pais = repository.save(PAIS);
		Pais sut = entityManager.find(Pais.class, pais.getId());
		assertThat(sut).isNotNull();
		assertThat(sut.getNome()).isEqualTo(PAIS.getNome());
	}
	
	@Test
	public void createPais_WithInvalid_ThrowsException() {
		assertThatThrownBy(() -> entityManager.persistAndFlush(EMPTY_PAIS)).isInstanceOf(RuntimeException.class);
		assertThatThrownBy(() -> entityManager.persistAndFlush(INVALID_PAIS)).isInstanceOf(RuntimeException.class);
	}
	
	@Test
	public void getPais_ByExistingId_ReturnPais() {
		Pais pais = entityManager.persistFlushFind(PAIS);
		
		Optional<Pais> paisOpt = repository.findById(pais.getId());
		
		assertThat(paisOpt).isNotEmpty();
		assertThat(paisOpt.get()).isEqualTo(pais);
	}
	
	@Test
	public void createPais_WithExistingName_TrowsException() {
		Pais pais = entityManager.persistFlushFind(PAIS);
		
		entityManager.detach(pais);
		pais.setId(null);
		
		assertThatThrownBy(() -> entityManager.persistAndFlush(pais)).isInstanceOf(RuntimeException.class);
	}
	
	@Test
	public void getPais_ByUnexistingId_ReturnNotFound() {
		Optional<Pais> paisOpt = repository.findById(11L);
		assertThat(paisOpt).isEmpty();
	}

}
