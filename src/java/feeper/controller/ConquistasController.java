package feeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/conquistas")
public class ConquistasController extends ApplicationController {
    
    @RequestMapping(method=RequestMethod.GET)
    public String list() {
        return "conquistas/list";
    }
    
    @RequestMapping(value="/minhasconquistas", method=RequestMethod.GET)
    public String minhasconquistas() {
        return "conquistas/minhasconquistas";
    }
    
}