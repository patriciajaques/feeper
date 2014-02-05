/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import feeper.entity.Pessoa;
import feeper.entity.Turma;
import feeper.model.DontValidateAccess;
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
//@SessionAttributes({ "UsuarioLogado" })
public class ApplicationController {
    
    public ApplicationController() {
        
    }
    
    @DontValidateAccess
    @RequestMapping(value="/closemodal", method=RequestMethod.GET)
    public String closeModal() {
        return "closemodal";
    }
    
}
