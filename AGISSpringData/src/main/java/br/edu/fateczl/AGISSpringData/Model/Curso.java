package br.edu.fateczl.AGISSpringData.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Curso")
@Inheritance(strategy = InheritanceType.JOINED)

public class Curso {
	@Id
	@Column(nullable = false)
	private Long idCurso;
	
	@Column(length = 100, nullable = false)
	private String nomeCurso;
	
	@Column(nullable = false)
	private int cargaHorario;
	
	@Column(length = 100, nullable = false)
	private String sigla;
	
	@Column(nullable = false)
	private float ENADE;
}