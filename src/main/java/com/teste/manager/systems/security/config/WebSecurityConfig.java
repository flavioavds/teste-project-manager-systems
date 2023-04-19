package com.teste.manager.systems.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.teste.manager.systems.security.jwt.AuthEntryPointJwt;
import com.teste.manager.systems.security.jwt.JwtAuthenticationFilter;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@SecurityScheme(
		name = "Bear Authentication",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
		)
public class WebSecurityConfig {
	
	@Autowired
	private AuthEntryPointJwt unauthorizeHandler;
	
	private static final String[] AUTH_WHITELIST = {
			
			"/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
			"/configuration/security", "/swagger-ui.html", "/webjars/**",
			"/v3/api-docs/**", "/swagger-ui/**", "/api-docs/**",
			"/swagger-ui.html","swagger-ui/**"
	};

	  private final JwtAuthenticationFilter jwtAuthFilter;
	  private final AuthenticationProvider authenticationProvider;
	  private final LogoutHandler logoutHandler;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
      .authorizeHttpRequests(auth -> auth
              .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll())
      .headers(headers -> headers.frameOptions().disable())
      .csrf(csrf -> csrf
              .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")));
		
		http
      .csrf()
      .disable()
      .authorizeHttpRequests()
      .requestMatchers("/v1/auth/**")
        .permitAll()
       .requestMatchers(AUTH_WHITELIST)
       	.permitAll()
      .anyRequest()
        .authenticated()
      .and()
      	.exceptionHandling().authenticationEntryPoint(unauthorizeHandler).and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .authenticationProvider(authenticationProvider)
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
      .logout()
      .logoutUrl("/v1/auth/logout")
      .addLogoutHandler(logoutHandler)
      .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
		
		return http.build();
	}

}
