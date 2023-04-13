package com.teste.manager.systems.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.manager.systems.entities.Usuario;
import com.teste.manager.systems.services.UsuarioServiceImpl;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
	
	@Autowired
	private UsuarioServiceImpl service;
	
	@GetMapping
	public List<Usuario> getAllUser(){
		return service.findAllUser();
	}

}
