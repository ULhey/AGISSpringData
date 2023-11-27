package br.edu.fateczl.AGISSpringData.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity

public class HistoricoNotaDTO {
	private Long idTurma;
	private String periodoAula;
	private String nomeDisciplina;
	private String metodoAvaliativo;
	private float p1;
	private float p2;
	private float t;
	@Id
	private float mediaFinal;
}