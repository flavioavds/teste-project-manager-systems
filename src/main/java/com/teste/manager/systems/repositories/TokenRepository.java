package com.teste.manager.systems.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.teste.manager.systems.entities.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {
	
	 @Query(value = """
		      select t from Token t inner join Usuario u\s
		      on t.usuario.id = u.id\s
		      where u.id = :id and (t.expired = false or t.revoked = false)\s
		      """)
	List<Token> findAllValidTokenByUsuario(Integer id);
	
	Optional<Token> findByToken(String token);

}
