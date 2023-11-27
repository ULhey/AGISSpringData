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
import br.edu.fateczl.AGISSpringData.Model.Matricula;
import br.edu.fateczl.AGISSpringData.Model.Turma;
import br.edu.fateczl.AGISSpringData.Repository.IAlunoRepository;
import br.edu.fateczl.AGISSpringData.Repository.IMatriculaRepository;
import br.edu.fateczl.AGISSpringData.Repository.ITurmaRepository;

@Controller
@RequestMapping("/matricula")
public class MatriculaController {
	private String RA;
	
	@Autowired
	private IMatriculaRepository mrp;

	@Autowired
	private ITurmaRepository trp;

	@Autowired
	private IAlunoRepository arp;

	private List<Turma> turmas = new ArrayList<>();
	private List<Matricula> matriculas = new ArrayList<>();

	@GetMapping
	private String get(ModelMap model) {
		if (!matriculas.isEmpty()) {
			matriculas.removeAll(matriculas);
		}
		if (!turmas.isEmpty()) {
			turmas.removeAll(turmas);
		}
		
		matriculas.addAll(mrp.findAll());
		turmas.addAll(trp.findAll());

		return "matricula";
	}

	@PostMapping
	public String matriculapost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
		if (!matriculas.isEmpty()) {
			matriculas.removeAll(matriculas);
		}
		if (!turmas.isEmpty()) {
			turmas.removeAll(turmas);
		}

		String RA = allRequestParam.get("RA");

		switch (allRequestParam.get("botao")) {
		case "Buscar":
			matriculas.addAll(buscaMatriculasAluno(RA));
			turmas.addAll(listarTurmaNmatriculadas(RA));
			this.RA = RA;
			break;
		case "Dispensar":
			String idMatricula = allRequestParam.get("idMatricula");
			dispensar(Long.parseLong(idMatricula));
			break;
		case "Matricular":
			String idTurma = allRequestParam.get("idTurma");
			inserir(this.RA, Long.parseLong(idTurma));
			break;
		}
			
		model.addAttribute("matriculas", matriculas);
		model.addAttribute("turmas", turmas);

		return("matricula");
	}

	private void dispensar(Long idMatricula) {
		Optional<Matricula> optional = mrp.findById(idMatricula);
		Matricula mNew = optional.get();
		mNew.setSituacao("Dispensado");
		mrp.save(mNew);
	}

	private void inserir(String RA, Long idTurma) {
		Matricula m = new Matricula();

		for (Aluno a : arp.findAll()) {
			if (a.getRA().equals(RA)) {
				m.setAluno(a);
			}
		}

		m.setTurma(trp.findById(idTurma).get());
		
		m.setSituacao("Cursando");
		m.setAno(mrp.gerarANO());
		m.setSemestreMatricula(mrp.gerarSEMESTRE());
		
		mrp.save(m);
	}
	
	private List<Turma> listarTurmaNmatriculadas(String RA) {
		for (Aluno a : arp.findAll()) {
			if (a.getRA().equals(RA)) {
				return trp.listarTurmaNmatriculadas(a.getRA());
			}
		}
		return null;
	}

	private List<Matricula> buscaMatriculasAluno(String RA) {
		for (Aluno a : arp.findAll()) {
			if (a.getRA().equals(RA)) {
				return mrp.buscaMatriculasAluno(a.getRA());
			}
		}
		return null;
	}
}