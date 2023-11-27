package br.edu.fateczl.AGISSpringData.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Matricula")
@Inheritance(strategy = InheritanceType.JOINED)

public class Matricula {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long idMatricula;
	
	@Column(nullable = false)
	private int semestreMatricula;
	
	@Column(nullable = false)
	private int ano;
	
	@Column(length = 100, nullable = false)
	private String situacao;
	
	@ManyToOne(cascade = CascadeType.REMOVE, targetEntity = Aluno.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "raAluno", nullable = false)
	private Aluno aluno;
	
	@ManyToOne(cascade = CascadeType.REMOVE, targetEntity = Turma.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "idTurma", nullable = true)
	private Turma turma;
}