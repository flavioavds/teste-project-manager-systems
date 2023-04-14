package com.teste.manager.systems.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teste.manager.systems.dtos.PaisDTORequest;
import com.teste.manager.systems.dtos.PaisDTOResponse;
import com.teste.manager.systems.entities.Pais;
import com.teste.manager.systems.services.PaisService;

import jakarta.validation.Valid;

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
	
	@PostMapping(value = "/salvar")
	public ResponseEntity<PaisDTOResponse> create(@RequestBody @Valid PaisDTORequest dto){
		return ResponseEntity.status(HttpStatus.CREATED).body(paisService.save(dto));
	}
	
	@PostMapping(value = "/salvar/{id}")
	public ResponseEntity<PaisDTOResponse> update(@PathVariable Long id, @RequestBody @Valid PaisDTORequest dto){
		return ResponseEntity.status(HttpStatus.OK).body(paisService.alterar(id, dto));
	}
	
	@DeleteMapping(value = "/excluir/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id){
		paisService.delete(id);
		return ResponseEntity.ok("Objeto com id " + id + " excluído com sucesso!");
	}

}
