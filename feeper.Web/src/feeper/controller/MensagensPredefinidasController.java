/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import feeper.Data.entity.MensagemPredefinida;
import feeper.Data.entity.Pessoa;
import feeper.Data.service.MensagemPredefinidaService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author gilvani
 */
@Controller
@RequestMapping(value = "/mensagenspredefinidas")
public class MensagensPredefinidasController extends ApplicationController {

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit() {
        return "mensagenspredefinidas/edit";
    }

    @RequestMapping(value = "/getJson", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<MensagemPredefinida> getJson(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Pessoa usuarioLogado = (Pessoa) session.getAttribute("UsuarioLogado");

        MensagemPredefinidaService service = new MensagemPredefinidaService();
        return service.getbyIdAutor(usuarioLogado.getId());
    }

    @RequestMapping(value = "/saveJson", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public boolean saveJson(@RequestBody List<MensagemPredefinida> mensagens, HttpServletRequest request) {

        try {
            HttpSession session = request.getSession();
            Pessoa usuarioLogado = (Pessoa) session.getAttribute("UsuarioLogado");

            MensagemPredefinidaService service = new MensagemPredefinidaService();
            service.SaveMensagens(usuarioLogado.getId(), mensagens);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<Object> search(@RequestParam(value = "term", defaultValue = "") String term, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Pessoa usuarioLogado = (Pessoa) session.getAttribute("UsuarioLogado");

        MensagemPredefinidaService service = new MensagemPredefinidaService();

        List<Object> lista = service.search("MensagemPredefinida", term, " and IdAutor = " + usuarioLogado.getId(), "MensagemPredefinida");
        return lista;
    }
}
