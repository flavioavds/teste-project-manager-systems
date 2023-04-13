package com.teste.manager.systems.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teste.manager.systems.entities.Token;
import com.teste.manager.systems.entities.Usuario;

public interface TokenRepository extends JpaRepository<Token, Long> {
	
	Optional<Token> findByToken(String token);
	
	String deleteByUsuario(Usuario usuario);

}
