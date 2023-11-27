package br.edu.fateczl.AGISSpringData.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.fateczl.AGISSpringData.Model.ChamadaDTO;

public interface IChamadaDTORespository extends JpaRepository<ChamadaDTO, String> {
	@Query(value="select * from GerarListaChamada(:idTurma)", nativeQuery = true)
	List<ChamadaDTO> geraListaChamada(@Param("idTurma") Long idTurma);
}