package br.edu.fateczl.AGISSpringData.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity

public class ChamadaDTO {
	@Id
	private String RAAluno;
	private String NomeAluno;
	private String NomeDisciplina;
	private Long idTurma;
}