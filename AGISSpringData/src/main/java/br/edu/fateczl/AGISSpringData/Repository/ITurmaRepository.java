package br.edu.fateczl.AGISSpringData.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.fateczl.AGISSpringData.Model.Turma;

public interface ITurmaRepository extends JpaRepository<Turma, Long> {
	
	@Query(value = "select t.* "
			+ "from Turma t "
			+ "	left join Matricula m on m.idTurma = t.idTurma and m.raAluno = :ra "
			+ "where m.raAluno is null", nativeQuery = true)
	List<Turma> listarTurmaNmatriculadas(@Param("ra") String ra);
}