package com.teste.manager.systems.security.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Autenticação de Usuario")
public class AuthenticationRequest {

	@Schema(description = "admin")
	@Size(min = 3, max = 300)
	@NotBlank(message = "Login não pode ser em Branco")
	private String login;
	
	@Schema(description = "suporte")
	@Size(min = 3, max = 300)
	@NotBlank(message = "Senha não pode ser em Branco")
	String senha;
}
