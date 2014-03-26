package feeper.controller;

import feeper.Data.entity.Pessoa;
import feeper.Data.entity.Turma;
import feeper.Data.service.PessoaService;
import feeper.Data.service.TurmaService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/colegas")
public class ColegasController extends ApplicationController {
    
    @RequestMapping(method=RequestMethod.GET)
    public String list(Model model, HttpSession session) {
        
        Turma turma = (Turma)session.getAttribute("TurmaSelecionada");
        
        TurmaService repoTurma = new TurmaService();
        List<Pessoa> lista = repoTurma.getAlunos(turma.getId());
        
        PessoaService repoPessoa = new PessoaService();
        Pessoa professor = repoPessoa.getById(turma.getIdProfessor());
        
        model.addAttribute("listaTurma", lista);
        model.addAttribute("professor", professor);
        
        return "colegas/list";
    }
    
}