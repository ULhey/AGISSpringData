package br.edu.fateczl.AGISSpringData.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.fateczl.AGISSpringData.Model.Matricula;

@Repository
public interface IMatriculaRepository extends JpaRepository<Matricula, Long> {
	@Procedure(name = "Matricula.gerarSEMESTRE")
    int gerarSEMESTRE();
	
	@Procedure(name = "Matricula.gerarANO")
    int gerarANO();	
	
	@Query(value = "SELECT * FROM Matricula WHERE idTurma = :id", nativeQuery = true)
	List<Matricula> buscaMatriculasTurma(@Param("id") Long id);
	
	@Query(value = "SELECT * FROM Matricula WHERE raAluno = :ra", nativeQuery = true)
	List<Matricula> buscaMatriculasAluno(@Param("ra") String ra);
}