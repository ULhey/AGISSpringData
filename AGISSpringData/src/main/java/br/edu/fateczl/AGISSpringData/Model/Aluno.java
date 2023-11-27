package br.edu.fateczl.AGISSpringData.Model;

import java.time.LocalDate;

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
@Table(name = "Aluno")
@Inheritance(strategy = InheritanceType.JOINED)

public class Aluno {
	@Id
	@Column(length = 9, nullable = false)
	private String RA;
	
	@Column(length = 11, nullable = false)
	private String CPF;
	
	@Column(length = 100, nullable = false)
	private String nome;
	
	@Column(length = 100, nullable = false)
	private String nomeSocial;
	
	@Column(columnDefinition = "DATE", nullable = false)
	private LocalDate dataNasc;
	
	@Column(length = 11, nullable = false)
	private String telefone;
	
	@Column(length = 100, nullable = false)
	private String email;
	
	@Column(length = 100, nullable = false)
	private String emailCorp;
	
	@Column(columnDefinition = "DATE", nullable = false)
	private LocalDate dataMedio;

	@Column(length = 100, nullable = false)
	private String instituicaoMedio;
	
	@Column(nullable = false)
	private double pontVestibular;
	
	@Column(length = 100, nullable = false)
	private int posiVestibular;
	
	@Column(nullable = false)
	private int anoInicio;
	
	@Column(nullable = false)
	private int semesInicio;
	
	@Column(nullable = false)
	private int semesConclusao;
	
	@Column(nullable = false)
	private int anoLimite;
	
	@Column(length = 100, nullable = false)
	private String turno;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Curso.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "idCurso", nullable = true)
	private Curso curso;
}