package com.teste.manager.systems.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pais")
public class Pais {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(max = 300)
	@NotBlank(message = "Nome não pode ser Vazio!")
	@Column(unique = true)
	private String nome;
	
	@Size(max = 3, message = "Sigla não pode ser superior a 3 Caracteres!")
	@NotBlank(message = "Sigla não pode ser Vazio!")
	@Column(unique = true)
	private String sigla;
	
	@Size(max = 300)
	@NotBlank(message = "Gentilico não pode ser Vazio!")
	@Column(unique = true)
	private String gentilico;

}
