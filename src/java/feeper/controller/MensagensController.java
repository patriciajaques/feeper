package feeper.controller;

import feeper.entity.Pessoa;
import feeper.service.MensagemCabecalhoService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/mensagens")
public class MensagensController extends ApplicationController {
    
    @RequestMapping(method=RequestMethod.GET)
    public String list(
            Model model,
            HttpSession session) {
        
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        MensagemCabecalhoService repoMensagemCabecalho = new MensagemCabecalhoService();
        List<Object> lista = repoMensagemCabecalho.getMensagensDestinatario(usuarioLogado.getId(), false, true);
        
        model.addAttribute("listaMensagens", lista);
        
        return "mensagens/list";
    }
    
}