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
import br.edu.fateczl.AGISSpringData.Model.Curso;
import br.edu.fateczl.AGISSpringData.Repository.IAlunoRepository;
import br.edu.fateczl.AGISSpringData.Repository.ICursoRespository;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

	@Autowired
	private IAlunoRepository arp;

	@Autowired
	private ICursoRespository crp;

	private List<Aluno> alunos = new ArrayList<>();
	private List<Curso> cursos = new ArrayList<>();

	@GetMapping
	private String get(ModelMap model) {
		if (!cursos.isEmpty()) {
			cursos.removeAll(cursos);
		}
		if (!alunos.isEmpty()) {
			alunos.removeAll(alunos);
		}

		alunos.addAll(arp.findAll());
		cursos.addAll(crp.findAll());

		model.addAttribute("alunos", alunos);
		model.addAttribute("cursos", cursos);

		return ("aluno");
	}

	@PostMapping
	public String alunopost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
		String RA = allRequestParam.get("RA");
		String CPF = allRequestParam.get("CPF");
		String nome = allRequestParam.get("nome");
		String nomeSocial = allRequestParam.get("nomeSocial");
		String dataNasc = allRequestParam.get("dataNasc");
		String telefone = allRequestParam.get("telefone");
		String email = allRequestParam.get("email");
		String dataMedio = allRequestParam.get("dataMedio");
		String instituicaoMedio = allRequestParam.get("instituicaoMedio");
		String pontVestibular = allRequestParam.get("pontVestibular");
		String posiVestibular = allRequestParam.get("posiVestibular");
		String turno = allRequestParam.get("turno");
		String idCurso = allRequestParam.get("idCurso");

		switch (allRequestParam.get("botao")) {
		case "Cadastrar":
			inserir(CPF, nome, nomeSocial, LocalDate.parse(dataNasc), telefone, email, LocalDate.parse(dataMedio),
					instituicaoMedio, Double.parseDouble(pontVestibular), Integer.parseInt(posiVestibular), turno,
					Long.parseLong(idCurso));
			break;
		case "Buscar":
			model.addAttribute("aluno", buscar(RA));
			break;
		case "Alterar":
			atualizar(RA, CPF, nome, nomeSocial, LocalDate.parse(dataNasc), telefone, email, LocalDate.parse(dataMedio),
					instituicaoMedio, Double.parseDouble(pontVestibular), Integer.parseInt(posiVestibular), turno,
					Long.parseLong(idCurso));
			break;
		case "Excluir":
			remover(RA);
			break;
		}

		model.addAttribute("alunos", alunos);
		model.addAttribute("cursos", cursos);

		return ("aluno");
	}

	private void inserir(String CPF, String nome, String nomeSocial, LocalDate dataNasc, String telefone, String email,
			LocalDate dataMedio, String instituicaoMedio, double pontVestibular, int posiVestibular, String turno,
			Long idCurso) {
		Aluno a = new Aluno();

		a.setRA(arp.criarRA());
		a.setEmailCorp(arp.criarEMAILCORP(nome));
		a.setSemesInicio(arp.gerarSEMESTRE());
		a.setSemesConclusao(arp.gerarSEMESTRE());
		a.setAnoInicio(arp.gerarANO());
		a.setAnoLimite(arp.gerarANO() + 5);
		a.setCPF(CPF);
		a.setNome(nome);
		a.setNomeSocial(nomeSocial);
		a.setDataNasc(dataNasc);
		a.setTelefone(telefone);
		a.setEmail(email);
		a.setDataMedio(dataMedio);
		a.setInstituicaoMedio(instituicaoMedio);
		a.setPontVestibular(pontVestibular);
		a.setPosiVestibular(posiVestibular);
		a.setTurno(turno);

		for (Curso c : cursos) {
			if (c.getIdCurso() == idCurso) {
				a.setCurso(c);
			}
		}

		if (arp.validarCPF(CPF) && arp.validaIDADE(dataNasc)) {
			arp.save(a);
			alunos.add(a);
		}
	}

	private void atualizar(String RA, String CPF, String nome, String nomeSocial, LocalDate dataNasc, String telefone,
			String email, LocalDate dataMedio, String instituicaoMedio, double pontVestibular, int posiVestibular,
			String turno, Long idCurso) {

		Aluno aNew = buscar(RA);

		aNew.setCPF(CPF);
		aNew.setNome(nome);
		aNew.setNomeSocial(nomeSocial);
		aNew.setDataNasc(dataNasc);
		aNew.setTelefone(telefone);
		aNew.setEmail(email);
		aNew.setDataMedio(dataMedio);
		aNew.setInstituicaoMedio(instituicaoMedio);
		aNew.setPontVestibular(pontVestibular);
		aNew.setPosiVestibular(posiVestibular);
		aNew.setTurno(turno);
		
		arp.save(aNew);

		alunos = arp.findAll();
	}

	public Aluno buscar(String RA) {
		Optional<Aluno> optional = arp.findById(RA);
		return optional.get();
	}

	private void remover(String RA) {
		alunos.removeIf(a -> a.getRA().equals(RA));

		Aluno a = buscar(RA);
		a.setCurso(null);
		arp.delete(a);
	}
}