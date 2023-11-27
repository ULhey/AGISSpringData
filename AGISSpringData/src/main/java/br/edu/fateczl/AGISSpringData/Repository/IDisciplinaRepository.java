package br.edu.fateczl.AGISSpringData.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.fateczl.AGISSpringData.Model.Disciplina;

@Repository
public interface IDisciplinaRepository extends JpaRepository<Disciplina, Long> {
	
}