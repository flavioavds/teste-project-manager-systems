package com.teste.manager.systems.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.manager.systems.dtos.PaisDTORequest;
import com.teste.manager.systems.security.auth.AuthenticationRequest;
import com.teste.manager.systems.security.auth.AuthenticationResponse;
import com.teste.manager.systems.services.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação Usuario", description = "Endpoints de Autenticação")
public class AuthenticationController {

  private final AuthenticationService service;

  @Operation(summary = "POST REST API Gerando token, autenticando com Login e Senha", description = " Copie e cole o codigo para Gerar Token Autenticando com 'ADMIN' = "
  		+ "{\r\n"
  		+ "  \"login\": \"admin\",\r\n"
  		+ "  \"senha\": \"suporte\"\r\n"
  		+ "}"
  		+ " ou Copie e cole o codigo para Gerar Token 'CONVIDADO' = "
  		+ "{\r\n"
  		+ "  \"login\": \"convidado\",\r\n"
  		+ "  \"senha\": \"manager\"\r\n"
  		+ "}"
  		+ " e um Token deverá ser gerado",
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
  @PostMapping("/autenticar")
  public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @Operation(summary = "POST REST API Refresh Token", description = "Para gerar o refresh token, copie o 'refresh_token' gerado pela autenticação e cole no Cadeado ao lado de Bear Authentication,"
  		+ " se Token expirou em 5 minutos ou se logado não esqueça de fazer o Logout e copiar o 'refresh_token' e adiconar ao Bear Authentication. Lembre-se que para gerar o Refresh Token,"
  		+ " é necessario do 'refresh_token' gerado ao Autenticar com Login e senha primeiro.",
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
  @SecurityRequirement(name = "Bear Authentication")	
  @PostMapping("/renovar-ticket")  public void refreshToken(HttpServletRequest request,  HttpServletResponse response) throws IOException {
    service.refreshToken(request, response);
  }


}