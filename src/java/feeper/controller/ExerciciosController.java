/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import feeper.entity.Exercicio;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
    
    @RequestMapping(value="/upload", method=RequestMethod.POST, produces="application/json")
    @ResponseBody
    public Exercicio upload(HttpServletRequest request,
		HttpServletResponse response, Object command, BindException errors)
		throws Exception {
        
        //FileUpload file = (FileUpload)command;
        MultipartFile file = (MultipartFile)command;
 
        //MultipartFile multipartFile = file.

        String fileName="";

        //if(multipartFile!=null){
        //        fileName = multipartFile.getOriginalFilename();
                //do whatever you want
        //}

        Exercicio exercicio = new Exercicio();
        exercicio.setNome("nome do exercicio");
        exercicio.setId(123);
        return exercicio;
    }
    
    /*
    @RequestMapping(value="/teste/{id}", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public Exercicio getJson(@PathVariable int id, Model model) {
        Exercicio exercicio = new Exercicio();
        exercicio.setNome("nome do exercicio");
        exercicio.setId(123);
        return exercicio;
    }
    */
}