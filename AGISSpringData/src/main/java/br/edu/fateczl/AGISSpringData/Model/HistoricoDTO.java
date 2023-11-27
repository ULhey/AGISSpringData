package br.edu.fateczl.AGISSpringData.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity

public class HistoricoDTO {
	@Id
	private Long idDisciplina;
	private String nomeDisciplina;
	private String professor;
	private String notaFinal;
	private String metodoAvaliativo;
	private int totalFaltas;
}