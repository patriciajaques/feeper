/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import com.google.common.net.HttpHeaders;
import feeper.entity.Exercicio;
import feeper.entity.Log;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/exercicios")
public class ExerciciosController {
    
    @RequestMapping(method=RequestMethod.GET)
    public String getIndex() {
        return "redirect:/exercicios/list";
    }
    
    @RequestMapping(value="/list", method=RequestMethod.GET)
    public String getList() {
        return "exercicios/list";
    }
    
    @RequestMapping(value="/responder", method=RequestMethod.GET)
    public String getResponder() {
        return "exercicios/responder";
    }
    
    @RequestMapping(value="/add", method=RequestMethod.GET)
    public String add(Model model) {
        
        Exercicio exercicio = new Exercicio();
        
        model.addAttribute(exercicio);
        model.addAttribute("IsAdd", true);
        
        return "exercicios/edit";
    }
    
    @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
    public String editAluno(@PathVariable int id, Model model) {
        
        Exercicio exercicio = new Exercicio();
        //FAZER UM GET BY ID NO REPOSITORIO
        
        model.addAttribute(exercicio);
        model.addAttribute("IsAdd", false);
        
        return "exercicios/edit";
    }
    
    @RequestMapping(value="/teste/{id}", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public Exercicio getJson(@PathVariable int id, Model model) {
        Exercicio exercicio = new Exercicio();
        exercicio.setNome("nome do exercicio");
        exercicio.setId(123);
        return exercicio;
    }
    
}