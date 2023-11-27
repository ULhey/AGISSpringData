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

import br.edu.fateczl.AGISSpringData.Model.Avaliacao;
import br.edu.fateczl.AGISSpringData.Model.Matricula;
import br.edu.fateczl.AGISSpringData.Model.Turma;
import br.edu.fateczl.AGISSpringData.Repository.IAvaliacaoRepository;
import br.edu.fateczl.AGISSpringData.Repository.IMatriculaRepository;
import br.edu.fateczl.AGISSpringData.Repository.ITurmaRepository;

@Controller
@RequestMapping("/avaliacao")

public class AvaliacaoController {

	@Autowired
	private ITurmaRepository trp;
	
	@Autowired
	private IMatriculaRepository mrp;
	
	@Autowired
	private IAvaliacaoRepository avrp;
	
	private List<Turma> turmas = new ArrayList<>();
	private List<Matricula> matriculas = new ArrayList<>();
	
	@GetMapping
	private String get(ModelMap model) {
		turmas = trp.findAll();
		
		model.addAttribute("turmas", turmas);
		
		return ("avaliacao");
	}
	
	@PostMapping
	private String post(@RequestParam Map<String, String> param, ModelMap model) {
		String idTurma = param.get("idTurma");
		
		switch (param.get("botao")) {
		case "Buscar":
			matriculas = buscaMatriculasTurma(Long.parseLong(idTurma));
			model.addAttribute("matriculas", matriculas);
			model.addAttribute("tituloDisciplina", trp.findById(Long.parseLong(idTurma)).get().getDisciplina().getNomeDisciplina());
			model.addAttribute("metodoAvaliativo", trp.findById(Long.parseLong(idTurma)).get().getMetodoAvaliativo());
			break;
		case "Salvar":
			String idMatricula = param.get("idMatricula");
			String metodoAvaliativo = param.get("metodoAvaliativo");
			
			float P1 = Float.parseFloat(param.get("P1"));
            float P2 = Float.parseFloat(param.get("P2"));
            float T = 0;
            
            if (metodoAvaliativo.equals("m")) {
            	T = 0;
            } else {
            	T = Float.parseFloat(param.get("T"));	
            }
            
            inserirNotas(Long.parseLong(idMatricula), Long.parseLong(idTurma), P1, P2, T);
			break;
		}
		
		model.addAttribute("turmas", turmas);
		model.addAttribute("matriculas", matriculas);
		model.addAttribute("tituloDisciplina", trp.findById(Long.parseLong(idTurma)).get().getDisciplina().getNomeDisciplina());
		model.addAttribute("metodoAvaliativo", trp.findById(Long.parseLong(idTurma)).get().getMetodoAvaliativo());
		
		return ("avaliacao");
	}
	
	private void inserirNotas(Long idMatricula, Long idTurma, float P1, float P2, float T) {
		Optional<Avaliacao> av = avrp.findByCodMat(idMatricula);
		
		if (av.isPresent()) {
			Avaliacao avOld = av.get();
			avOld.setP1(P1);
			avOld.setP2(P2);
			avOld.setT(T);
			avrp.save(avOld);
		} else {
			Avaliacao avNew = new Avaliacao();
			avNew.setIdAvaliacao(1 + avrp.count());
			avNew.setMatricula(mrp.findById(idMatricula).get());
			avNew.setTurma(trp.findById(idTurma).get());
			avNew.setP1(P1);
			avNew.setP2(P2);
			avNew.setT(T);
			avrp.save(avNew);
		}
		
	}

	private List<Matricula> buscaMatriculasTurma(Long idTurma) {
		return mrp.buscaMatriculasTurma(idTurma);
	}
}
