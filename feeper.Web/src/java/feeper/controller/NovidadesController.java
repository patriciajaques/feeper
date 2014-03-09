package feeper.controller;

import feeper.Data.entity.Pessoa;
import feeper.Data.model.EPerfil;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/novidades")
public class NovidadesController extends ApplicationController {
    
    @RequestMapping(method=RequestMethod.GET)
    public String getView() {
        return "novidades/list";
    }
    
}