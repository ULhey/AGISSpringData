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

import br.edu.fateczl.AGISSpringData.Model.Disciplina;
import br.edu.fateczl.AGISSpringData.Model.Turma;
import br.edu.fateczl.AGISSpringData.Repository.IDisciplinaRepository;
import br.edu.fateczl.AGISSpringData.Repository.ITurmaRepository;

@Controller
@RequestMapping("/turma")
public class TurmaController {
	@Autowired
	private IDisciplinaRepository drp;
	
	@Autowired
	private ITurmaRepository trp;
	
	private List<Turma> turmas = new ArrayList<>();
	private List<Disciplina> disciplinas = new ArrayList<>();
	
	@GetMapping
	public String get(ModelMap model) {
		disciplinas = drp.findAll();
		turmas = trp.findAll();
		
		model.addAttribute("disciplinas", disciplinas);
		
		return "turma";
	}
	
	@PostMapping
	public String post(@RequestParam Map<String, String> param, ModelMap model) {
		String idTurma = param.get("idTurma");
		String horarioAula = param.get("horarioAula");
		String periodoAula = param.get("periodoAula");
		String professor = param.get("professor");
		String metodoAvaliativo = param.get("metodoAvaliativo");
		String idDisciplina = param.get("idDisciplina");
		
		switch (param.get("botao")) {
		case "Cadastrar":
			inserir(horarioAula, periodoAula, professor, metodoAvaliativo, Long.parseLong(idDisciplina));
			break;
		case "Buscar":
			model.addAttribute("turma", buscar(Long.parseLong(idTurma)));
			break;
		case "Alterar":
			atualizar(Long.parseLong(idTurma), horarioAula, periodoAula, professor, metodoAvaliativo, Long.parseLong(idDisciplina));
			break;
		case "Excluir":
			remover(Long.parseLong(idTurma));
			break;
		}
		
		model.addAttribute("disciplinas", disciplinas);
		model.addAttribute("turmas", turmas);
		
		return "turma";
	}

	private void inserir(String horarioAula, String periodoAula, String professor, String metodoAvaliativo, Long idDisciplina) {
		Turma t = new Turma();
		
		t.setIdTurma(501 + trp.count());
		t.setHorarioAula(horarioAula);
		t.setPeriodoAula(periodoAula);
		t.setProfessor(professor);
		t.setMetodoAvaliativo(metodoAvaliativo);
		
		t.setDisciplina(drp.findById(idDisciplina).get());	
		
		trp.save(t);
		turmas.add(t);
	}

	private void atualizar(Long idTurma, String horarioAula, String periodoAula, String professor, String metodoAvaliativo, Long idDisciplina) {
		Turma t = buscar(idTurma);
		
		t.setHorarioAula(horarioAula);
		t.setPeriodoAula(periodoAula);
		t.setProfessor(professor);
		t.setMetodoAvaliativo(metodoAvaliativo);
		
		t.setDisciplina(drp.findById(idDisciplina).get());
		
		trp.save(t);
		
		turmas = trp.findAll();
	}
	
	private Turma buscar(Long idTurma) {
		Optional<Turma> optional = trp.findById(idTurma);
		return optional.get();
	}


	private void remover(Long idTurma) {
		turmas.removeIf(t -> t.getIdTurma() == idTurma);
		
		Turma t = buscar(idTurma);
		t.setDisciplina(null);
		trp.delete(t);	
		turmas = trp.findAll();
	}
}
