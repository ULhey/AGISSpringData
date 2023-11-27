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
import br.edu.fateczl.AGISSpringData.Model.Disciplina;
import br.edu.fateczl.AGISSpringData.Repository.ICursoRespository;
import br.edu.fateczl.AGISSpringData.Repository.IDisciplinaRepository;

@Controller
@RequestMapping("/disciplina")
public class DisciplinaController {
	@Autowired
	private IDisciplinaRepository drp;

	@Autowired
	private ICursoRespository crp;

	private List<Disciplina> disciplinas = new ArrayList<>();
	private List<Curso> cursos = new ArrayList<>();

	@GetMapping
	private String get(ModelMap model) {
		if (!cursos.isEmpty()) {
			cursos.removeAll(cursos);
		}

		if (!disciplinas.isEmpty()) {
			disciplinas.removeAll(disciplinas);
		}
		cursos.addAll(crp.findAll());
		disciplinas.addAll(drp.findAll());

		model.addAttribute("cursos", cursos);
		model.addAttribute("disciplinas", disciplinas);

		return "disciplina";
	}

	@PostMapping
	public String disciplinapost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
		String idDisciplina = allRequestParam.get("idDisciplina");
		String nomeDisciplina = allRequestParam.get("nomeDisciplina");
		String aula = allRequestParam.get("aula");
		String tipoConteudo = allRequestParam.get("tipoConteudo");
		String semestreDisciplina = allRequestParam.get("semestreDisciplina");
		String idCurso = allRequestParam.get("idCurso");

		switch (allRequestParam.get("botao")) {
		case "Cadastrar":
			inserir(nomeDisciplina, Integer.parseInt(aula), tipoConteudo, Integer.parseInt(semestreDisciplina),
					Long.parseLong(idCurso));
			break;
		case "Buscar":
			model.addAttribute("disciplina", buscar(Long.parseLong(idDisciplina)));
			break;
		case "Alterar":
			atualizar(Long.parseLong(idDisciplina), nomeDisciplina, Integer.parseInt(aula), tipoConteudo,
					Integer.parseInt(semestreDisciplina), Long.parseLong(idCurso));
			break;
		case "Excluir":
			remover(Long.parseLong(idDisciplina));
			break;
		}

		model.addAttribute("cursos", cursos);
		model.addAttribute("disciplinas", disciplinas);

		return ("disciplina");
	}

	private void inserir(String nomeDisciplina, int aula, String tipoConteudo, int semestreDisciplina, Long idCurso) {
		Disciplina d = new Disciplina();

		d.setIdDisciplina(1001 + drp.count());
		d.setNomeDisciplina(nomeDisciplina);
		d.setQuantidadeAula(aula);
		d.setTipoConteudo(tipoConteudo);
		d.setSemestreDisciplina(semestreDisciplina);

		for (Curso c : cursos) {
			if (c.getIdCurso() == idCurso) {
				d.setCurso(c);
			}
		}

		drp.save(d);
		disciplinas.add(d);
	}

	private void atualizar(Long idDisciplina, String nomeDisciplina, int aula, String tipoConteudo, int semestreDisciplina, Long idCurso) {		
		Disciplina dNew = buscar(idDisciplina);
	    
	    dNew.setNomeDisciplina(nomeDisciplina);
	    dNew.setQuantidadeAula(aula);
	    dNew.setTipoConteudo(tipoConteudo);
	    dNew.setSemestreDisciplina(semestreDisciplina);
	    
	    for (Curso c : cursos) {
	        if (c.getIdCurso() == idCurso) {
	            dNew.setCurso(c);
	        }
	    }
	    
	    drp.save(dNew);
	    
	    disciplinas = drp.findAll();
	}

	private Disciplina buscar(Long idDisciplina) {
		Optional<Disciplina> optional = drp.findById(idDisciplina);
		return optional.get();
	}

	private void remover(Long idDisciplina) {
	    disciplinas.removeIf(d -> d.getIdDisciplina().equals(idDisciplina));

	    Disciplina d = buscar(idDisciplina);
        d.setCurso(null);
        drp.delete(d);
	}
}