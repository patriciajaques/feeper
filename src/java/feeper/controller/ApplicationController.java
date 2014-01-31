/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import feeper.entity.Pessoa;
import feeper.entity.Turma;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author
 * fabioalves
 */
@Controller
@SessionAttributes({ "UsuarioLogado" })
public class ApplicationController {
    
    public ApplicationController() {
        
    }

    @ModelAttribute("UsuarioLogado")
    public Pessoa getInitializedUsuarioLogado() {
        Pessoa pessoa = new Pessoa();
        
        pessoa.setIp("127.0.0.1");
        pessoa.setNome("Fábio Pacheco Alves");
        pessoa.setId(1);
        pessoa.setTipoPessoa('A');
        pessoa.setDataUltimoAcesso(new Date());
        pessoa.setEmail("arnistrong@gmail.com");
        
        return pessoa;
    }
    
    @RequestMapping(value="/closemodal", method=RequestMethod.GET)
    public String closeModal() {
        return "closemodal";
    }
    
}
