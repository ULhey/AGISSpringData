package br.edu.fateczl.AGISSpringData.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.fateczl.AGISSpringData.Model.HistoricoDTO;

public interface IHistoricoRepository extends JpaRepository<HistoricoDTO, Long> {
	@Query(value = "SELECT * FROM historicoAluno(:ra)", nativeQuery = true)
	List<HistoricoDTO> historicoAluno(@Param("ra") String ra);
}