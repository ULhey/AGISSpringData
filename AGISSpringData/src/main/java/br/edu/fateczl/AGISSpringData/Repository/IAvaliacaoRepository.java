package br.edu.fateczl.AGISSpringData.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.fateczl.AGISSpringData.Model.Avaliacao;

public interface IAvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
	@Query(value = "SELECT * FROM avalicao WHERE idMatricula = :idMatricula", nativeQuery = true)
	Optional<Avaliacao> findByCodMat(@Param("idMatricula") Long idMatricula);
}