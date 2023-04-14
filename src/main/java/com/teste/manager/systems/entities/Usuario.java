package com.teste.manager.systems.entities;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.teste.manager.systems.entities.enums.RoleList;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "tb_usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String login;
	
	private String senha;
	private String nome;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "roles")
	private Set<Integer> administrador = new HashSet<>();
	
	public Usuario() {
		addRoles(RoleList.CONVIDADO);
	}
	
	public Set<RoleList> getRoleList(){
		return administrador.stream().map(x -> RoleList.toEnum(x)).collect(Collectors.toSet());
	}
	
	public void addRoles(RoleList perfil) {
		administrador.add(perfil.getCod());
	}

	

}
