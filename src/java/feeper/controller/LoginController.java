/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import feeper.entity.Pessoa;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({ "MSG_SUCESSO", "MSG_ERRO" })
@RequestMapping(value="/login")
public class LoginController extends ApplicationController {
    
    @RequestMapping(method=RequestMethod.GET)
    public String list() {
        return "login/login";
    }
    
    @RequestMapping(value="/validate", method=RequestMethod.POST)
    public String validate(HttpServletRequest request) {
        
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        
        Pessoa pessoa = new Pessoa();
        pessoa.setIp("127.0.0.1");
        pessoa.setNome("Fábio Pacheco Alves");
        pessoa.setId(1);
        pessoa.setTipoPessoa('A');
        pessoa.setDataUltimoAcesso(new Date());
        pessoa.setEmail("arnistrong@gmail.com");
        
        HttpSession session = request.getSession(true);
        session.setAttribute("UsuarioLogado", pessoa);
        
        return "redirect:/";
    }
    
    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        
        HttpSession session = request.getSession(false);
        if (session != null)
            session.invalidate();
        
        return "redirect:/";
    }
    
}
