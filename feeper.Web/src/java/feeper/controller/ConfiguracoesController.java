/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import feeper.Data.entity.ConfiguracaoSistema;
import feeper.Data.service.ConfiguracaoService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author gilvani
 */
@Controller
@RequestMapping(value = "/configuracoes")
public class ConfiguracoesController extends ApplicationController {

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit() {
        return "configuracoes/edit";
    }

    @RequestMapping(value = "/getJson", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ConfiguracaoSistema getJson(HttpServletRequest request) {

        ConfiguracaoService service = new ConfiguracaoService();
        return service.getConfiguracao();
    }

    @RequestMapping(value = "/saveJson", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public boolean saveJson(@RequestBody ConfiguracaoSistema configuracao) {

        try {
            ConfiguracaoService service = new ConfiguracaoService();
            service.saveConfiguracao(configuracao);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
