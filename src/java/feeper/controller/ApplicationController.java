/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import feeper.entity.Turma;
import feeper.model.DontValidateAccess;
import feeper.model.TurmaService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
