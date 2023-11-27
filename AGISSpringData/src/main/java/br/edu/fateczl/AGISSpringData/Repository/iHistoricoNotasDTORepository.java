package br.edu.fateczl.AGISSpringData.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.fateczl.AGISSpringData.Model.HistoricoNotaDTO;

@Repository
public interface iHistoricoNotasDTORepository extends JpaRepository<HistoricoNotaDTO, Long> {
	@Query(value = "SELECT * FROM historicoNotas(:idTurma)", nativeQuery = true)
	List<HistoricoNotaDTO> listaHistoricoTurmas(@Param("idTurma") Long idTurma);
}
