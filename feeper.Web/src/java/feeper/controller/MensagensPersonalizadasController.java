/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import feeper.Data.entity.ConfiguracaoSistema;
import feeper.Data.entity.MensagemPersonalizada;
import feeper.Data.entity.Pessoa;
import feeper.Data.service.ConfiguracaoService;
import feeper.Data.service.MensagemPersonalizadaService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping(value = "/mensagenspersonalizadas")
public class MensagensPersonalizadasController extends ApplicationController {

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit() {
        return "mensagenspersonalizadas/edit";
    }

    @RequestMapping(value = "/getJson", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<MensagemPersonalizada> getJson(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Pessoa usuarioLogado = (Pessoa) session.getAttribute("UsuarioLogado");

        MensagemPersonalizadaService service = new MensagemPersonalizadaService();
        return service.getbyIdAutor(usuarioLogado.getId());
    }

    @RequestMapping(value = "/saveJson", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public boolean saveJson(@RequestBody List<MensagemPersonalizada> mensagens, HttpServletRequest request) {

        try {
            HttpSession session = request.getSession();
            Pessoa usuarioLogado = (Pessoa) session.getAttribute("UsuarioLogado");

            MensagemPersonalizadaService service = new MensagemPersonalizadaService();
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

        MensagemPersonalizadaService service = new MensagemPersonalizadaService();

        List<Object> lista = service.search("MensagemPersonalizada", term, " and IdAutor = " + usuarioLogado.getId(), "MensagemPersonalizada");
        return lista;
    }
}
