package br.edu.fateczl.AGISSpringData.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.fateczl.AGISSpringData.Model.HistoricoFaltasDTO;
import br.edu.fateczl.AGISSpringData.Model.Turma;
import br.edu.fateczl.AGISSpringData.Repository.IHistoricoFaltasDTORepository;
import br.edu.fateczl.AGISSpringData.Repository.ITurmaRepository;

@Controller
@RequestMapping("/historicofaltas")
public class HistoricoFaltasController {
	@Autowired
	private ITurmaRepository trp;
	
	@Autowired
	private IHistoricoFaltasDTORepository hfrp;
	
	private List<Turma> turmas = new ArrayList<>();
	private List<HistoricoFaltasDTO> historico = new ArrayList<>();
	
	@GetMapping
	private String get(ModelMap model) {
		turmas = trp.findAll();
		
		model.addAttribute("turmas", turmas);
		
		return ("historicofaltas");
	}
	
	@PostMapping
	private String post(@RequestParam Map<String, String> param, ModelMap model) {
		String idTurma = param.get("idTurma");
		
		switch (param.get("botao")) {
		case "Buscar":
			historico = buscaMatriculasTurma(Long.parseLong(idTurma));
			break;
		}
		
		model.addAttribute("turmas", turmas);
		model.addAttribute("historico", historico);
		model.addAttribute("tituloDisciplina", trp.findById(Long.parseLong(idTurma)).get().getDisciplina().getNomeDisciplina());
		model.addAttribute("metodoAvaliativo", trp.findById(Long.parseLong(idTurma)).get().getMetodoAvaliativo());
		
		return ("historicofaltas");
	}
	
	private List<HistoricoFaltasDTO> buscaMatriculasTurma(Long idTurma) {
		return hfrp.listaChamdasTurma(idTurma);
	}
}
