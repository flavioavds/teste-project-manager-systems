package com.teste.manager.systems.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.teste.manager.systems.entities.Token;
import com.teste.manager.systems.exceptions.TokenRefreshException;
import com.teste.manager.systems.repositories.TokenRepository;
import com.teste.manager.systems.repositories.UsuarioRepository;

@Service
public class TokenService {
	
	@Value("${jwt.refreshexpiration}")
	private Long refreshTokenDurationMs;
	
	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Optional<Token> findByToken(String token){
		return tokenRepository.findByToken(token);
	}
	
	public Token createRefreshToken(Long userId) {
		Token token = new Token();
		token.setUsuario(usuarioRepository.findById(userId).get());
		token.setExpiracao(Instant.now().plusMillis(refreshTokenDurationMs));
		token.setToken(UUID.randomUUID().toString());
		
		token = tokenRepository.save(token);
		return token;
	}
	
	public Token verifyExpirition(Token token) {
		if(token.getExpiracao().compareTo(Instant.now()) < 0) {
			tokenRepository.delete(token);
			throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
		}
		return token;
	}
	
	public String deleteByUserId(Long userId) {
		return tokenRepository.deleteByUsuario(usuarioRepository.findById(userId).get());
	}

}
