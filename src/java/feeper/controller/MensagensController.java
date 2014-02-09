package feeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/mensagens")
public class MensagensController extends ApplicationController {
    
    @RequestMapping(method=RequestMethod.GET)
    public String getView() {
        return "mensagens/list";
    }
    
}