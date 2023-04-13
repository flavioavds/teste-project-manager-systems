package com.teste.manager.systems.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.manager.systems.entities.Usuario;
import com.teste.manager.systems.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImpl {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public List<Usuario> findAllUser(){
		return usuarioRepository.findAll();
	}
	
}
