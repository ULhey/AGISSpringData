package br.edu.fateczl.AGISSpringData.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.fateczl.AGISSpringData.Model.Chamada;

@Repository
public interface IChamadaRepository extends JpaRepository<Chamada, Long>{
	
	@Query(value = "SELECT * FROM chamada WHERE idTurma = :id AND dataChamada = :data", nativeQuery = true)
	List<Chamada> buscaChamadasTurma(@Param("id") Long idTurma, @Param("data") LocalDate data);
	
}