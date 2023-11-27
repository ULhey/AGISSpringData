package br.edu.fateczl.AGISSpringData.Repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import br.edu.fateczl.AGISSpringData.Model.Aluno;

@Repository
public interface IAlunoRepository extends JpaRepository<Aluno, String> {
	Optional<Aluno> findById(String ra); 
	
	@Procedure(name = "Aluno.criarRA")
    String criarRA();
	
	@Procedure(name = "Aluno.validarCPF")
	boolean validarCPF(String cpf);
	
	@Procedure(name = "Aluno.criarEMAILCORP")
	String criarEMAILCORP(String nome);
	
	@Procedure(name = "Aluno.validaIDADE")
	boolean validaIDADE(LocalDate dataNasc);
	
	@Procedure(name = "Aluno.gerarSEMESTRE")
    int gerarSEMESTRE();
	
	@Procedure(name = "Aluno.gerarANO")
    int gerarANO();	
}