package feeper.controller;

import feeper.Data.entity.Pessoa;
import feeper.Data.model.ETipoLeitor;
import feeper.Data.service.MensagemCabecalhoService;
import feeper.Data.service.MensagemLeitorService;
import java.util.Date;
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
    public String list(Model model,HttpSession session) {
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        MensagemCabecalhoService repoMensagemCabecalho = new MensagemCabecalhoService();
        List<Object> lista = repoMensagemCabecalho.getMensagens(usuarioLogado.getId(), 'A', false, true);
        model.addAttribute("listaMensagens", lista);
        Date dataLeitura = new Date();
        MensagemLeitorService repoMensagemLeitor = new MensagemLeitorService();
        repoMensagemLeitor.atualizarDataLeitura(usuarioLogado.getId(), ETipoLeitor.DESTINATARIO, dataLeitura);
        repoMensagemLeitor.atualizarDataLeitura(usuarioLogado.getId(), ETipoLeitor.REMETENTE, dataLeitura);
        return "mensagens/list";
    }
    
    @RequestMapping(method=RequestMethod.GET, value = "/ranking")
    public String list2(Model model,HttpSession session) {
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        MensagemCabecalhoService repoMensagemCabecalho = new MensagemCabecalhoService();
        List<Object> lista = repoMensagemCabecalho.getMensagens(usuarioLogado.getId(), 'A', false, true);
        model.addAttribute("listaMensagens", lista);
        Date dataLeitura = new Date();
        MensagemLeitorService repoMensagemLeitor = new MensagemLeitorService();
        repoMensagemLeitor.atualizarDataLeitura(usuarioLogado.getId(), ETipoLeitor.DESTINATARIO, dataLeitura);
        repoMensagemLeitor.atualizarDataLeitura(usuarioLogado.getId(), ETipoLeitor.REMETENTE, dataLeitura);
        return "mensagens/list";
    }
    
}