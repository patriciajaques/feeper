/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author
 * fabioalves
 */
@Controller
@SessionAttributes("NomeUsuarioLogado")
public class ApplicationController {
    
    private String nomeUsuario;
    //private HttpSession session;
    
    public ApplicationController() {
        
        //this.session = session;
        
        //if (this.nomeUsuario == null || nomeUsuario.equals(""))
            //nomeUsuario = "Fábio Alves";
        
        //this.session.setAttribute("NomeUsuario", this.nomeUsuario);
    }

    public String getNomeUsuario() {
        //return this.session.getAttribute("NomeUsuario").toString();
        return "";
    }
    
    @ModelAttribute("NomeUsuarioLogado")
    public String getInitializedNomeUsuarioLogado() {
        return "Fábio Pacheco Alves";
    }
    
}
