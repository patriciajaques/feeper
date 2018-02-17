package feeper.controller;

import feeper.Data.entity.MedalhaPessoa;
import feeper.Data.entity.Pessoa;
import feeper.Data.entity.Ranking;
import feeper.Data.entity.Turma;
import feeper.Data.service.MedalhaPessoaService;
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
@RequestMapping(value="/conquistas")
public class ConquistasController extends ApplicationController {
    
    
    private MedalhaPessoaService medalhaPessoaService = new MedalhaPessoaService();
    
    @RequestMapping(method=RequestMethod.GET)
    public String list() {
        return "conquistas/list";
    }
    
    @RequestMapping(value="/minhasconquistas", method=RequestMethod.GET)
    public String list(Model model, HttpSession session) {
        Pessoa userLogged = (Pessoa) session.getAttribute("UsuarioLogado");
        
        List<MedalhaPessoa> medalhas = medalhaPessoaService.findByIdAluno(userLogged.getId());
        model.addAttribute("medalhas", medalhas);
        
        return "conquistas/minhasconquistas";
    }
    
}