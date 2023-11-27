package br.edu.fateczl.AGISSpringData.Controller;

import java.time.LocalDate;
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

import br.edu.fateczl.AGISSpringData.Model.Aluno;
import br.edu.fateczl.AGISSpringData.Model.Chamada;
import br.edu.fateczl.AGISSpringData.Model.ChamadaDTO;
import br.edu.fateczl.AGISSpringData.Model.Turma;
import br.edu.fateczl.AGISSpringData.Repository.IAlunoRepository;
import br.edu.fateczl.AGISSpringData.Repository.IChamadaDTORespository;
import br.edu.fateczl.AGISSpringData.Repository.IChamadaRepository;
import br.edu.fateczl.AGISSpringData.Repository.ITurmaRepository;

@Controller
@RequestMapping("/chamada")
public class ChamadaController {
	@Autowired
	private IChamadaRepository ccrp;

	@Autowired
	private IChamadaDTORespository cdtorp;

	@Autowired
	private IAlunoRepository arp;

	@Autowired
	private ITurmaRepository trp;

	private List<Aluno> alunos = new ArrayList<>();
	private List<Chamada> chamadas = new ArrayList<>();
	private List<Turma> turmas = new ArrayList<>();

	@GetMapping
	private String get(ModelMap model) {
		chamadas = ccrp.findAll();
		alunos = arp.findAll();
		turmas = trp.findAll();

		model.addAttribute("turmas", turmas);
		
		return "chamada";
	}

	@PostMapping
	public String chamadapost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
		String RA = allRequestParam.get("ra");
		String idTurma = allRequestParam.get("idTurma");
		
		String falta = allRequestParam.get("faltas");
		String data = allRequestParam.get("data");

		switch (allRequestParam.get("botao")) {
		case "Realizar chamada":
			model.addAttribute("chamadas", geraListaChamada(Long.parseLong(idTurma)));
			break;
		case "Salvar":
			inserir(RA, Long.parseLong(idTurma), Integer.parseInt(falta));
			break;
		case "Consultar":
			model.addAttribute("dataChamada", data);
			model.addAttribute("chamadas2", buscaChamadasTurma(Long.parseLong(idTurma), LocalDate.parse(data)));
			break;
		case "Alterar":
			String idChamada = allRequestParam.get("idChamada");
			alterarFalta(Long.parseLong(idChamada), Integer.parseInt(falta));
			model.addAttribute("dataChamada", data);
			break;
		}
		
		if (allRequestParam.get("botao").equals("Salvar") || allRequestParam.get("botao").equals("Alterar")) {
			if (allRequestParam.get("voltaLista").equals("chamada1")) {
				model.addAttribute("chamadas", geraListaChamada(Long.parseLong(idTurma)));
			} else {
				model.addAttribute("chamadas2", buscaChamadasTurma(Long.parseLong(idTurma), LocalDate.parse(data)));
			}
		}
		
		model.addAttribute("turmas", turmas);

		return ("chamada");
	}
	
	private List<ChamadaDTO> geraListaChamada(Long idTurma) {
		return cdtorp.geraListaChamada(idTurma);
	}
	
	private List<Chamada> buscaChamadasTurma(Long idTurma, LocalDate data) {
		return ccrp.buscaChamadasTurma(idTurma, data);
	}
	
	private void inserir(String RA, Long idTurma, int falta) {
		Chamada c = new Chamada();
		
		c.setIdChamada(1 + ccrp.count());
		
		for (Aluno a : alunos) {
			if (a.getRA().equals(RA)) {
				c.setAluno(a);
			}
		}

		c.setTurma(trp.findById(idTurma).get());
		
		c.setDataChamada(LocalDate.now());
		c.setFaltas(falta);

		ccrp.save(c);
	}

	private void alterarFalta(Long idChamada, int falta) {
		Optional<Chamada> optional = ccrp.findById(idChamada);
		
		if (optional.isPresent()) {
			Chamada c = optional.get();
			c.setFaltas(falta);
			ccrp.save(c);
		}
	}
}