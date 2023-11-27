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
@Table(name = "Avalicao")
@Inheritance(strategy = InheritanceType.JOINED)

public class Avaliacao {
	@Id
	private Long idAvaliacao;
	
	@Column(nullable = false)
	private double p1;
	
	@Column(nullable = false)
	private double p2;
	
	@Column(nullable = false)
	private double t;
	
	@ManyToOne(cascade = CascadeType.REMOVE, targetEntity = Turma.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "idTurma", nullable = false)
	private Turma turma;
	
	@ManyToOne(cascade = CascadeType.REMOVE, targetEntity = Matricula.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "idMatricula", nullable = true)
	private Matricula matricula;
}