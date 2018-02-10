package feeper.controller;

import feeper.Data.entity.Pessoa;
import feeper.Data.entity.Ranking;
import feeper.Data.entity.Turma;
import feeper.Data.service.PessoaService;
import feeper.Data.service.RankingService;
import feeper.Data.service.TurmaService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/ranking")
public class RankingController {
    
    @RequestMapping(method=RequestMethod.GET)
    public String list(Model model, HttpSession session) {
        Turma turma = (Turma)session.getAttribute("TurmaSelecionada");
        TurmaService repoTurma = new TurmaService();
        List<Pessoa> lista = repoTurma.getAlunos(turma.getId());
        
        RankingService rankingService = new RankingService();
        
        List<Ranking> gg = rankingService.findGlobalRanking();
        List<Ranking> rankingTurma = rankingService.findTurmaRanking(turma.getId());
        
        PessoaService repoPessoa = new PessoaService();
        Pessoa professor = repoPessoa.getById(turma.getIdProfessor());
        model.addAttribute("listaTurma", gg);
        model.addAttribute("rankingTurma", rankingTurma);
        model.addAttribute("professor", professor);
        return "ranking/list";
    }
    
}