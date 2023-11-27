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
@Table(name = "Disciplina")
@Inheritance(strategy = InheritanceType.JOINED)

public class Disciplina {
	@Id
	@Column(nullable = false)
	private Long idDisciplina;
	
	@Column(length = 100, nullable = false)
	private String nomeDisciplina;
	
	@Column(nullable = false)
	private int quantidadeAula;
	
	@Column(length = 100, nullable = false)
	private String tipoConteudo;
	
	@Column(nullable = false)
	private int semestreDisciplina;
	
	@ManyToOne(cascade = CascadeType.REMOVE, targetEntity = Curso.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "idCurso", nullable = true)
	private Curso curso;
}