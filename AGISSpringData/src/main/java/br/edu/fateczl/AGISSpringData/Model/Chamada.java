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
@Table(name = "Chamada")
@Inheritance(strategy = InheritanceType.JOINED)

public class Chamada {
	@Id
	@Column(nullable = false)
	private Long idChamada;
	
	@Column(nullable = false)
	private int faltas;
	
	@Column(columnDefinition = "DATE", nullable = false)
	private LocalDate dataChamada;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Aluno.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "raAluno", nullable = false)
	private Aluno aluno;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Turma.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "idTurma", nullable = true)
	private Turma turma;
}