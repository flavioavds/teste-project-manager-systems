package com.teste.manager.systems.security.jwt;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.teste.manager.systems.entities.Usuario;
import com.teste.manager.systems.security.services.UsuarioDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.expiration}")
	private int jwtExpirationMs;
	
	@Value("${jwt.cookiename}")
	private String jwtCookie;
	
	@Value("${jwt.refreshcookiename}")
	private String jwtRefreshCookie;
	
	public ResponseCookie generateJwtCookie(UsuarioDetailsImpl usuarioDetailsImpl) {
		String jwt = generateTokenFromUsername(usuarioDetailsImpl.getUsername());
		return generateCookie(jwtCookie, jwt, "/v1");
	}
	
	public ResponseCookie generateJwtCookie(Usuario usuario) {
		String jwt = generateTokenFromUsername(usuario.getLogin());
		return generateCookie(jwtCookie, jwt, "/v1");
	}
	
	public ResponseCookie generateRefreshJwtCookie(String refreshToken) {
		return generateCookie(jwtRefreshCookie, refreshToken, "/v1/auth/refreshtoken");
	}
	
	public String getJwtFromCookies(HttpServletRequest request) {
		return getCookieValueByEmail(request, jwtCookie);
	}
	
	public String getJwtRefreshFromCookies(HttpServletRequest request) {
		return getCookieValueByEmail(request, jwtRefreshCookie);
	}
	
	public ResponseCookie getCleanJwtCookie() {
		ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/v1").build();
		return cookie;
	}
	
	public ResponseCookie getCleanJwtRefreshCookie() {
		ResponseCookie cookie = ResponseCookie.from(jwtRefreshCookie, null).path("/v1/auth/refreshtoken").build();
		return cookie;
	}
	
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims strring is empty: {}", e.getMessage());
		} catch (Exception e) {
			logger.error("Fail token: {}", e.getMessage());
		}
		return false;
	}
	
	public String generateTokenFromUsername(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(getKey(), SignatureAlgorithm.HS512)
				.claim("manager", "systems")
				.compact();
	}
	
	private ResponseCookie generateCookie(String email, String value, String path) {
		ResponseCookie cookie = ResponseCookie.from(email, value).path(path).maxAge(24 * 60 * 60).httpOnly(true).build();
		return cookie;
	}
	
	private Key getKey() {
		byte [] secretBytes = Decoders.BASE64URL.decode(jwtSecret);
		return Keys.hmacShaKeyFor(secretBytes);
	}
	
	private String getCookieValueByEmail(HttpServletRequest request, String login) {
		Cookie cookie = WebUtils.getCookie(request, login);
		if (cookie == null) {
			return null;
		}else {
			return cookie.getValue();
		}
	}

}
