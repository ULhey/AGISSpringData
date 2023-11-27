package br.edu.fateczl.AGISSpringData.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.fateczl.AGISSpringData.Model.Curso;
import br.edu.fateczl.AGISSpringData.Repository.ICursoRespository;

@Controller
@RequestMapping("/curso")
public class CursoController {
	@Autowired
	private ICursoRespository crp;

	private List<Curso> cursos = new ArrayList<>();

	@GetMapping
	private String get(ModelMap model) {
		if (!cursos.isEmpty()) {
			cursos.removeAll(cursos);
		}

		cursos.addAll(crp.findAll());

		model.addAttribute("cursos", cursos);

		return "curso";
	}

	@PostMapping
	public String cursopost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
		String idCurso = allRequestParam.get("idCurso");
		String idCurso2 = allRequestParam.get("idCurso2");
		String nomeCurso = allRequestParam.get("nomeCurso");
		String cargaHorario = allRequestParam.get("cargaHorario");
		String sigla = allRequestParam.get("sigla");
		String ENADE = allRequestParam.get("ENADE");

		switch (allRequestParam.get("botao")) {
		case "Cadastrar":
			inserir(Long.parseLong(idCurso), nomeCurso, Integer.parseInt(cargaHorario), sigla,
					Float.parseFloat(ENADE));
			break;
		case "Buscar":
			model.addAttribute("curso", buscar(Long.parseLong(idCurso2)));
			break;
		case "Alterar":
			atualizar(Long.parseLong(idCurso), nomeCurso, Integer.parseInt(cargaHorario), sigla,
					Float.parseFloat(ENADE));
			break;
		case "Excluir":
			remover(Long.parseLong(idCurso));
			break;
		}

		model.addAttribute("cursos", cursos);

		return ("curso");
	}

	private void inserir(Long idCurso, String nomeCurso, int cargaHorario, String sigla, float ENADE) {
		Curso c = new Curso();

		c.setIdCurso(idCurso);
		c.setNomeCurso(nomeCurso);
		c.setCargaHorario(cargaHorario);
		c.setSigla(sigla);
		c.setENADE(ENADE);

		crp.save(c);
		cursos.add(c);
	}

	private void atualizar(Long idCurso, String nomeCurso, int cargaHorario, String sigla, float ENADE) {
		
		Curso cNew = buscar(idCurso);

		cNew.setNomeCurso(nomeCurso);
		cNew.setCargaHorario(cargaHorario);
		cNew.setSigla(sigla);
		cNew.setENADE(ENADE);

		crp.save(cNew);

		cursos = crp.findAll();
	}

	public Curso buscar(Long idCurso) {
		Optional<Curso> optional = crp.findById(idCurso);
		return optional.get();
	}

	private void remover(Long idCurso) {
	    cursos.removeIf(d -> d.getIdCurso().equals(idCurso));

	    Curso c = buscar(idCurso);
        crp.delete(c);
	}
}