package com.teste.manager.systems.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.teste.manager.systems.entities.Pais;

public interface PaisRepository extends JpaRepository<Pais, Long>{

	Optional<Pais> findByNome(String nome);
	Optional<Pais> findBySigla(String sigla);
	Optional<Pais> findByGentilico(String gentilico);
	
	@Query("SELECT obj FROM Pais obj WHERE LOWER(obj.nome) LIKE LOWER(CONCAT('%',:nome,'%'))")
	List<Pais> findByNomeContains(String nome);
	
	Boolean existsByNome(String nome);
	Boolean existsBySigla(String sigla);
	Boolean existsByGentilico(String gentilico);
	
}
