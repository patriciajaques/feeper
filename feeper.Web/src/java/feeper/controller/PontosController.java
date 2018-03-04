package feeper.controller;

import feeper.Data.entity.ExercicioPontos;
import feeper.Data.entity.Pessoa;
import feeper.Data.entity.Ranking;
import feeper.Data.entity.Turma;
import feeper.Data.model.ETipoLog;
import feeper.Data.service.ExercicioPontosService;
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
@RequestMapping(value="/pontos")
public class PontosController  extends ApplicationController {
    
    @RequestMapping(method=RequestMethod.GET)
    public String list(Model model, HttpSession session) {
        PessoaService repoPessoa = new PessoaService();
        Pessoa usuarioLogado = (Pessoa) session.getAttribute("UsuarioLogado");
        
        ExercicioPontosService exercicioPontosService = new ExercicioPontosService();
        Integer totalPontos = exercicioPontosService.getPointsByIdPessoa(usuarioLogado.getId());
        List<ExercicioPontos> lista = exercicioPontosService.findByIdAluno(usuarioLogado.getId());
        
        model.addAttribute("totalPontos", totalPontos);
        model.addAttribute("listaPontos", lista);
        
        log(usuarioLogado.getId(), "", ETipoLog.VISUALIZA_PONTOS);
        
        return "pontos/list";
    }
    
}