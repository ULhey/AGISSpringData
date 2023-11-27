package br.edu.fateczl.AGISSpringData.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.fateczl.AGISSpringData.Model.Curso;

@Repository
public interface ICursoRespository extends JpaRepository<Curso, Long> {

}