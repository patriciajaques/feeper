/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import feeper.entity.Pessoa;
import feeper.model.DontValidateAccess;
import feeper.model.PessoaService;
import feeper.model.TurmaService;
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
            TurmaService repoTurma = new TurmaService();
            
            pessoa.setTurmas(repoTurma.getTurmasByIdPessoa(pessoa.getId()));
            
            HttpSession session = request.getSession(true);
            session.setAttribute("UsuarioLogado", pessoa);
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
