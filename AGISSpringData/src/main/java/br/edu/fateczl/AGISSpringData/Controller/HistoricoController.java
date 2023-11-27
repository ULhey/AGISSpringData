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

import br.edu.fateczl.AGISSpringData.Model.Aluno;
import br.edu.fateczl.AGISSpringData.Model.HistoricoDTO;
import br.edu.fateczl.AGISSpringData.Repository.IAlunoRepository;
import br.edu.fateczl.AGISSpringData.Repository.IHistoricoRepository;

@Controller
@RequestMapping("/historico")
public class HistoricoController {

	@Autowired
	private IHistoricoRepository hrp;

	@Autowired
	private IAlunoRepository arp;

	private List<Aluno> alunos = new ArrayList<>();

	@GetMapping
	private String get(ModelMap model) {
		alunos = arp.findAll();
		
		return ("historico");
	}

	@PostMapping
	public void historicopost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
		String RA = allRequestParam.get("RA");
		try {
			switch (allRequestParam.get("botao")) {
			case "Buscar":
				Aluno a = buscar(RA);
				model.addAttribute("aluno", a);
				model.addAttribute("historicos", chamaHistorico(a));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Aluno buscar(String RA) {
		for (Aluno a : alunos) {
			if (a.getRA().equals(RA)) {
				Optional<Aluno> optional = arp.findById(RA);
				return optional.get();
			}
		}
		return null;
	}

	public List<HistoricoDTO> chamaHistorico(Aluno a) {
		return hrp.historicoAluno(a.getRA());
	}
}