/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import feeper.entity.Turma;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/turma")
public class TurmaController extends ApplicationController {
    
    @RequestMapping(method=RequestMethod.GET)
    public String getView() {
        return "turma/list";
    }
    
    @RequestMapping(value="/add", method=RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new Turma());
        model.addAttribute("IsAdd", true);
        return "turma/edit";
    }
    
    @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
    public String add(@PathVariable int id, Model model) {
        model.addAttribute(new Turma());
        model.addAttribute("IsAdd", false);
        return "turma/edit";
    }
    
    @RequestMapping(value="/details/{id}", method=RequestMethod.GET)
    public String details(@PathVariable int id, Model model) {
        Turma turma = new Turma();
        turma.setNome("Programação 1");
        
        model.addAttribute(turma);
        return "turma/details";
    }
    
}