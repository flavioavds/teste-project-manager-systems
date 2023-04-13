package com.teste.manager.systems.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teste.manager.systems.dtos.PaisDTOResponse;
import com.teste.manager.systems.entities.Pais;
import com.teste.manager.systems.services.PaisService;

@RestController
@RequestMapping("/v1/pais")
public class PaisController {
	
	@Autowired
	private final PaisService paisService;
	
	public PaisController(PaisService paisService) {
		this.paisService = paisService;
	}

	@GetMapping
	public ResponseEntity<List<PaisDTOResponse>> findAll(){
		return ResponseEntity.status(HttpStatus.OK).body(paisService.findAll());
	}
	
	@GetMapping(value = "/search")
	public ResponseEntity<List<Pais>> findByNome(@RequestParam String nome){
		return ResponseEntity.ok(paisService.findByNomeContains(nome));
	}

}
