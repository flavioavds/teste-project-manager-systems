package com.teste.manager.systems.security.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.teste.manager.systems.entities.Usuario;
import com.teste.manager.systems.repositories.UsuarioRepository;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService{
	private static Logger logger = LoggerFactory.getLogger(UsuarioDetailsServiceImpl.class);
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Optional<Usuario> user = usuarioRepository.findByLogin(login);
		if(user == null) {
			logger.error("Email not found: " + login);
		}
		System.out.println(user.get());
		return UsuarioDetailsImpl.build(user.get());
	}

}
