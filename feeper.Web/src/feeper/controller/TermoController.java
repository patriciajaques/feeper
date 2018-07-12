package feeper.controller;

import feeper.Data.entity.Pessoa;
import feeper.Data.model.ETipoLeitor;
import feeper.Data.service.MensagemCabecalhoService;
import feeper.Data.service.MensagemLeitorService;
import feeper.Data.service.PessoaService;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value="/termo")
public class TermoController extends ApplicationController {
    
    PessoaService pessoaService = new PessoaService();
    
    @RequestMapping(method=RequestMethod.GET)
    public String list(Model model,HttpSession session) {
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        Pessoa pessoaDB = pessoaService.getById(usuarioLogado.getId());
        model.addAttribute("usuario", pessoaDB);
        return "mensagens/termo";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    
    public ModelAndView validate(HttpServletRequest request, final RedirectAttributes flash, HttpSession session) {
        Integer action = Integer.valueOf(request.getParameter("action"));
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        
        Pessoa pessoaDB = pessoaService.getById(usuarioLogado.getId());
        pessoaDB.setTermo(action);
        pessoaService.update(pessoaDB);
        
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/termo", true, true, false));
        return mav;
        
        
    }
    
}