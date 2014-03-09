package feeper.controller;

import feeper.Data.entity.Pessoa;
import feeper.Data.service.MensagemCabecalhoService;
import feeper.Data.service.MensagemLeitorService;
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
        
        //Mensagens recebidas como destinatário
        List<Object> listaDestinatario = repoMensagemCabecalho.getMensagensDestinatario(usuarioLogado.getId(), false, true);
        model.addAttribute("listaMensagensDestinatario", listaDestinatario);
        
        MensagemLeitorService repoMensagemLeitor = new MensagemLeitorService();
        repoMensagemLeitor.atualizarDataLeitura(usuarioLogado.getId(), 'D');
        
        //Mensagens enviadas como remetente
        List<Object> listaRemetente = repoMensagemCabecalho.getMensagensRemetente(usuarioLogado.getId(), false, true);
        model.addAttribute("listaMensagensRemetente", listaRemetente);
        
        repoMensagemLeitor.atualizarDataLeitura(usuarioLogado.getId(), 'R');
        
        return "mensagens/list";
    }
    
}