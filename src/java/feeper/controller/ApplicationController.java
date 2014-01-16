/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author
 * fabioalves
 */
@Controller
@SessionAttributes({ "NomeUsuarioLogado", "Perfil" })
public class ApplicationController {
    
    public ApplicationController() {
        
    }

    @ModelAttribute("NomeUsuarioLogado")
    public String getInitializedNomeUsuarioLogado() {
        return "Fábio Pacheco Alves";
    }
    
    @ModelAttribute("Perfil")
    public char getInitializedPerfil() {
        return 'M';
    }
    
}
