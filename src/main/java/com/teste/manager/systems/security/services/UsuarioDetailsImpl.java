package com.teste.manager.systems.security.services;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teste.manager.systems.entities.Usuario;
import com.teste.manager.systems.entities.enums.RoleList;

public class UsuarioDetailsImpl implements UserDetails, Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String login;	
	
	@JsonIgnore
	private String senha;
	private Collection<? extends GrantedAuthority> authorities;

	public UsuarioDetailsImpl(Long id, String login, String senha, Set<RoleList> perfis) {
		super();
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
		}
	
	public static UsuarioDetailsImpl build(Usuario usuario) {
		return new UsuarioDetailsImpl(usuario.getId(), usuario.getLogin(), usuario.getSenha(), usuario.getRoleList());
	}
	
	public Long getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
		
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioDetailsImpl other = (UsuarioDetailsImpl) obj;
		return Objects.equals(id, other.id);
	}

	public boolean hasRole(RoleList perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}

}
