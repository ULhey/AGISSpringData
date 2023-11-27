package br.edu.fateczl.AGISSpringData.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.fateczl.AGISSpringData.Model.HistoricoFaltasDTO;

@Repository
public interface IHistoricoFaltasDTORepository extends JpaRepository<HistoricoFaltasDTO, Long> {
	@Query(value = "SELECT * FROM situacaoNotaFaltas(?)", nativeQuery = true)
	List<HistoricoFaltasDTO> listaChamdasTurma(@Param("idTurma") Long idTurma);
}
