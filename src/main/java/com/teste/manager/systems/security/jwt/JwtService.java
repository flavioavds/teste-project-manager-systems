package com.teste.manager.systems.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.teste.manager.systems.exceptions.JwtAuthenticationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Service
public class JwtService {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

  @Value("${security.jwt.secret-key}")
  private String secretKey;
  @Value("${security.jwt.expiration}")
  private Long jwtExpiration;
  @Value("${security.jwt.refresh-token.expiration}")
  private Long refreshExpiration;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  public String generateToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails
  ) {
    return buildToken(extraClaims, userDetails, jwtExpiration);
  }

  public String generateRefreshToken(
      UserDetails userDetails
  ) {
    return buildToken(new HashMap<>(), userDetails, refreshExpiration);
  }

  private String buildToken(
          Map<String, Object> extraClaims,
          UserDetails userDetails,
          long expiration
  ) {
    return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .claim("roles", userDetails.getAuthorities())
            .claim("manager", "systems")
            .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
	    try {
	        return Jwts.parserBuilder()
	                .setSigningKey(getSignInKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	    } catch (ExpiredJwtException e) {
	        logger.warn("Token expirado: {}", e.getMessage());
	        throw new JwtAuthenticationException("Token expirado");
	    } catch (MalformedJwtException | SignatureException | IllegalArgumentException e) {
	        logger.error("Erro ao processar o token: {}", e.getMessage());
	        throw new JwtAuthenticationException("Token inválido");
	    } catch (JwtException e) {
	        logger.error("Erro ao processar o token: {}", e.getMessage());
	        throw new JwtAuthenticationException("Erro ao processar o token");
	    }
	}


  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
