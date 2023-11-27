package br.edu.fateczl.AGISSpringData.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Turma")
@Inheritance(strategy = InheritanceType.JOINED)

public class Turma {
	@Id
	@Column(length = 9, nullable = false)
	private Long idTurma;
	
	@Column(length = 30, nullable = false)
	private String horarioAula;
	
	@Column(length = 1, nullable = false)
	private String periodoAula;
	
	@Column(length = 100, nullable = false)
	private String professor;
	
	@Column(length = 30, nullable = false)
	private String metodoAvaliativo;
	
	@ManyToOne(cascade = CascadeType.REMOVE, targetEntity = Disciplina.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "idDisciplina", nullable = true)
	private Disciplina disciplina;
}