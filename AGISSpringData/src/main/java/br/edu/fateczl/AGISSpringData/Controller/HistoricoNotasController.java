package br.edu.fateczl.AGISSpringData.Controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.fateczl.AGISSpringData.Model.HistoricoNotaDTO;
import br.edu.fateczl.AGISSpringData.Model.Turma;
import br.edu.fateczl.AGISSpringData.Persistence.GenericDAO;
import br.edu.fateczl.AGISSpringData.Repository.ITurmaRepository;
import br.edu.fateczl.AGISSpringData.Repository.iHistoricoNotasDTORepository;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
@RequestMapping("/historiconota")

public class HistoricoNotasController {

	@Autowired
	private ITurmaRepository trp;
	
	@Autowired
	private iHistoricoNotasDTORepository hnrp;
	
	private List<Turma> turmas = new ArrayList<>();
	private List<HistoricoNotaDTO> historico = new ArrayList<>();
	
	@GetMapping
	private String get(ModelMap model) {
		turmas = trp.findAll();
		
		model.addAttribute("turmas", turmas);
		
		return ("historiconota");
	}
	
	@PostMapping
	private String post(@RequestParam Map<String, String> param, ModelMap model) {
		String idTurma = param.get("idTurma");
		
		switch (param.get("botao")) {
		case "Buscar":
			historico = buscaMatriculasTurma(Long.parseLong(idTurma));
			break;
		case "Gerar PDF":
			gerarRelatorio(idTurma);
			break;
		}
		
		model.addAttribute("turmas", turmas);
		model.addAttribute("historico", historico);
		model.addAttribute("tituloDisciplina", trp.findById(Long.parseLong(idTurma)).get().getDisciplina().getNomeDisciplina());
		model.addAttribute("metodoAvaliativo", trp.findById(Long.parseLong(idTurma)).get().getMetodoAvaliativo());
		
		return ("historiconota");
	}
	
	//nãofunciona
	@SuppressWarnings({"rawtypes", "unchecked"})
    private ResponseEntity gerarRelatorio(String idTurma) {
        GenericDAO gdao = new GenericDAO();
        String erro = "";

        Map<String, Object> paramRelatorio = new HashMap<>();
        paramRelatorio.put("idTurma", idTurma);

        byte[] bytes = null;

        InputStreamResource resource = null;
        HttpStatus status = null;
        HttpHeaders headers = new HttpHeaders();

        try {
            Connection c = gdao.conexao();
            File arq = ResourceUtils.getFile("classpath:reports/notasTurma.jasper");
            JasperReport report = (JasperReport) JRLoader.loadObjectFromFile(arq.getAbsolutePath());
            bytes = JasperRunManager.runReportToPdf(report, paramRelatorio, c);
        } catch (ClassNotFoundException | SQLException | FileNotFoundException | JRException e) {
            e.printStackTrace();
            erro = e.getMessage();
            status = HttpStatus.BAD_REQUEST;
        } finally {
            if (erro.equals("")) {
                ByteArrayInputStream is = new ByteArrayInputStream(bytes);
                resource = new InputStreamResource(is);
                headers.setContentLength(bytes.length);
                headers.setContentType(MediaType.APPLICATION_PDF);
                status = HttpStatus.OK;
            }
        }

        return new ResponseEntity(resource, headers, status);
    }

	private List<HistoricoNotaDTO> buscaMatriculasTurma(Long idTurma) {
		return hnrp.listaHistoricoTurmas(idTurma);
	}
}