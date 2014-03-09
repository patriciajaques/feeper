package feeper.controller;

import feeper.Data.entity.Pessoa;
import feeper.Data.model.EPerfil;
import feeper.Data.model.ETipoLog;
import feeper.Data.service.PessoaService;
import feeper.Data.service.TurmaService;
import feeper.model.DontValidateAccess;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@DontValidateAccess
@Controller
@RequestMapping(value="/login")
public class LoginController extends ApplicationController {
    
    @RequestMapping(method=RequestMethod.GET)
    public String list() {
        return "login/login";
    }
    
    @RequestMapping(value="/validate", method=RequestMethod.POST)
    public ModelAndView validate(HttpServletRequest request, final RedirectAttributes flash) {
        
        ModelAndView mav = new ModelAndView();
        
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        
        PessoaService repoPessoa = new PessoaService();
        Pessoa pessoa = repoPessoa.validaLoginSenha(email, senha);
        if (pessoa == null || pessoa.getId() == 0)
        {
            mav.setView(new RedirectView("/login", true, true, false));
            flash.addFlashAttribute("MSG_ERRO", "Dados inválidos!");
        }
        else
        {
            log(pessoa.getId(), "IP: " + request.getRemoteAddr(), ETipoLog.LOGIN);
            
            TurmaService repoTurma = new TurmaService();
            
            pessoa.setTurmas(repoTurma.getTurmasByIdPessoa(pessoa.getId()));
            
            HttpSession session = request.getSession(true);
            session.setAttribute("UsuarioLogado", pessoa);
            
            if (pessoa.getTurmas().size() > 0)
                session.setAttribute("TurmaSelecionada", pessoa.getTurmas().get(0));
            
            mav.setView(new RedirectView("/", true, true, false));                
        }
        return mav;
    }
    
    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        
        HttpSession session = request.getSession(false);
        if (session != null)
            session.invalidate();
        
        return "redirect:/";
    }
    
}
