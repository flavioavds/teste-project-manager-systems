package com.teste.manager.systems.security.jwt;

import com.teste.manager.systems.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String login;
  private String nome;
  private String senha;
  private Role administrador;
}