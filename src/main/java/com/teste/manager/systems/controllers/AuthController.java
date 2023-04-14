package com.teste.manager.systems.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.manager.systems.entities.Token;
import com.teste.manager.systems.entities.Usuario;
import com.teste.manager.systems.exceptions.TokenRefreshException;
import com.teste.manager.systems.payload.request.LoginRequest;
import com.teste.manager.systems.payload.response.MessageResponse;
import com.teste.manager.systems.payload.response.UserInfoResponse;
import com.teste.manager.systems.repositories.UsuarioRepository;
import com.teste.manager.systems.security.jwt.JwtUtils;
import com.teste.manager.systems.security.services.UsuarioDetailsImpl;
import com.teste.manager.systems.services.TokenService;
import com.teste.manager.systems.services.UsuarioServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
	
	@Autowired
	private UsuarioServiceImpl service;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	TokenService tokenService;
	
	@PostMapping("/autenticar")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getSenha()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UsuarioDetailsImpl usuarioDetailsImpl = (UsuarioDetailsImpl) authentication.getPrincipal();
		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(usuarioDetailsImpl);
		List<String> roles = usuarioDetailsImpl.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		Token token = tokenService.createRefreshToken(usuarioDetailsImpl.getId());
		ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(token.getToken());
		
		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
				.header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
				.body(new UserInfoResponse(
						usuarioDetailsImpl.getId(),
						usuarioDetailsImpl.getUsername(),
						roles
						));
	}
	
	@PostMapping("/renovar-ticket")
	public ResponseEntity<?> refreshToken(HttpServletRequest request) {
		String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);
		if((refreshToken != null) && (refreshToken.length() > 0)) {
			return tokenService.findByToken(refreshToken)
					.map(tokenService::verifyExpirition)
					.map(Token::getUsuario)
					.map(user -> {
						ResponseCookie jwCookie = jwtUtils.generateJwtCookie(user);
						return ResponseEntity.ok()
								.header(HttpHeaders.SET_COOKIE, jwCookie.toString())
								.body(new MessageResponse("Token is refreshed sucessfolly"));
					})
					.orElseThrow(() -> new TokenRefreshException(refreshToken, "Refresh token is empty!"));
			}
			return ResponseEntity.badRequest().body(new MessageResponse("Refresh token is empty!"));
		
	}
	
	
	@GetMapping
	public List<Usuario> getAllUser(){
		return service.findAllUser();
	}

}
