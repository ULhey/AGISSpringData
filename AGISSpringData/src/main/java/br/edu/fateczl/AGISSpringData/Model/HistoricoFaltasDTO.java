package br.edu.fateczl.AGISSpringData.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class HistoricoFaltasDTO {
	private int idDisciplina;
	private String nomeDisciplina;
	private String professor;
	private String metodoAvaliativo;
	@Id
	private float mediaFinal;
	private int totalFaltas;
	private String statusAluno;
}
