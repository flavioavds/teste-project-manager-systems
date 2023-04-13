package com.teste.manager.systems.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teste.manager.systems.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Optional<Usuario> findByLogin(String login);

}
