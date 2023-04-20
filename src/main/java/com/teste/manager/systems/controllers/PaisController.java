package com.teste.manager.systems.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/pais")
@SecurityRequirement(name = "Bear Authentication")	
@Tag(name = "País", description = "Endpoints de Gerenciamento de Países")
public class PaisController {
	
	@Autowired
	private final PaisService paisService;
	
	public PaisController(PaisService paisService) {
		this.paisService = paisService;
	}

	@GetMapping
	@Operation(summary = "GET REST API Mostrando todos os países cadastrados", description = "Exibindo todos os países",
		tags = {"País"},
		responses = {
				@ApiResponse(description = "Success", responseCode = "200", content = {
						@Content( 
							mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = PaisDTORequest.class))
								)	
				}),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Err", responseCode = "500", content = @Content)
				
		})
	public ResponseEntity<List<PaisDTOResponse>> findAll(){
		return ResponseEntity.status(HttpStatus.OK).body(paisService.findAll());
	}
	
	@Operation(summary = "GET REST API Parameter Buscando por Nome País", description = "Exibindo todos os países com busca de Nome. Ex: digiar a letra 'b', ou as iniciais de um país 'bra',"
			+ " irá exibir o nome de todos na lista de acordo com interesse busca, por nome completo ou por iniciais do nome",
			tags = {"País"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200", 
							content = @Content( 
								mediaType = "application/json",
								array = @ArraySchema(schema = @Schema(implementation = PaisDTORequest.class)))),
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Err", responseCode = "500", content = @Content)
					
			})
	@GetMapping(value = "/search")
	public ResponseEntity<List<Pais>> findByNome(@RequestParam String nome){
		return ResponseEntity.ok(paisService.findByNomeContains(nome));
	}
	
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@Operation(summary = "POST REST API Salvando um país", description = "Salvando um país. Para salvar um país de exemplo copie e cole: "
			+ "{\r\n"
			+ "  \"nome\": \"Uruguaio\",\r\n"
			+ "  \"sigla\": \"UY\",\r\n"
			+ "  \"gentilico\": \"Uruguaios\"\r\n"
			+ "}"
			+ ". Lembrete que apenas usuário 'ADMIN' podem salvar um país.",
			tags = {"País"},
			responses = {
					@ApiResponse(description = "Save", responseCode = "200", 
							content = @Content( 
								mediaType = "application/json",
								array = @ArraySchema(schema = @Schema(implementation = PaisDTORequest.class)))),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Err", responseCode = "500", content = @Content)
					
			})
	@PostMapping(value = "/salvar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PaisDTOResponse> create(@RequestBody @Valid PaisDTORequest dto){
		return ResponseEntity.status(HttpStatus.CREATED).body(paisService.save(dto));
	}
	
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@Operation(summary = "POST REST API Atualizando um país", description = "Atualizando um país. Para atualizar informações de um país, digite um id existente no qual deseja atualizar e Como exemplo copie e cole: "
			+ "{\r\n"
			+ "  \"nome\": \"Chile\",\r\n"
			+ "  \"sigla\": \"CHL\",\r\n"
			+ "  \"gentilico\": \"Chilenos\"\r\n"
			+ "}"
			+ ". Lembrete que apenas usuário 'ADMIN' podem atualizar um país.",
			tags = {"País"},
			responses = {
					@ApiResponse(description = "Updated", responseCode = "200", 
							content = @Content( 
								mediaType = "application/json",
								array = @ArraySchema(schema = @Schema(implementation = PaisDTORequest.class)))),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Err", responseCode = "500", content = @Content)
					
			})
	@PostMapping(value = "/salvar/{id}")
	public ResponseEntity<PaisDTOResponse> update(@PathVariable Long id, @RequestBody @Valid PaisDTORequest dto){
		return ResponseEntity.status(HttpStatus.OK).body(paisService.alterar(id, dto));
	}
	
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@Operation(summary = "DELETE REST API Excluíndo um País", description = "Excluíndo um País. Para excluir um país, apenas selecione, "
			+ "o id que deseja excluir e execute a deleção. Lembrando que apenas usuários 'ADMIN' Podem excluir um país.",
			tags = {"País"},
			responses = {
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Err", responseCode = "500", content = @Content)
					
			})
	@DeleteMapping(value = "/excluir/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id){
		paisService.delete(id);
		return ResponseEntity.ok("Objeto com id " + id + " excluído com sucesso!");
	}

}
