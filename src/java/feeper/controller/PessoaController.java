/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import feeper.entity.Pessoa;
import feeper.model.ETipoPessoa;
import feeper.model.HibernateUtil;
import java.util.List;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value="/pessoa")
public class PessoaController {
    
    /*
     * Métodos ALUNO
     */
    @RequestMapping(value="/addaluno", method=RequestMethod.GET)
    public String addAluno(Model model) {
        
        Pessoa pessoa = new Pessoa();
        pessoa.setTipoPessoa(ETipoPessoa.ALUNO);
        
        model.addAttribute(pessoa);
        model.addAttribute("IsAdd", true);
        
        return "pessoa/edit";
    }
    
    @RequestMapping(value="/editaluno/{id}", method=RequestMethod.GET)
    public String editAluno(@PathVariable int id, Model model) {
        
        Pessoa pessoa = new Pessoa();
        //FAZER UM GET BY ID NO REPOSITORIO
        
        model.addAttribute(pessoa);
        model.addAttribute("IsAdd", false);
        
        return "pessoa/edit";
    }
    
    /*
     * Métodos PROFESSOR
     */
    @RequestMapping(value="/addprofessor", method=RequestMethod.GET)
    public String addProfessor(Model model) {
        
        Pessoa pessoa = new Pessoa();
        pessoa.setTipoPessoa(ETipoPessoa.PROFESSOR);
        
        model.addAttribute(pessoa);
        model.addAttribute("IsAdd", true);
        
        return "pessoa/edit";
    }
    
    @RequestMapping(value="/editprofessor/{id}", method=RequestMethod.GET)
    public String editProfessor(@PathVariable int id, Model model) {
        
        Pessoa pessoa = new Pessoa();
        //FAZER UM GET BY ID NO REPOSITORIO
        
        model.addAttribute(pessoa);
        model.addAttribute("IsAdd", false);
        
        return "pessoa/edit";
    }
    
    @RequestMapping(value="/search/{word}", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public List<Pessoa> search(@PathVariable String word, Model model) {
        
        HibernateUtil<Pessoa> repo = new HibernateUtil<Pessoa>();
        List<Pessoa> lista = repo.pesquisar(Pessoa.class, "nome", word);
        
        return lista;
    }

    /*
    @RequestMapping(method=RequestMethod.GET)
    public String getCreateForm(Model model) {
        model.addAttribute(new Pessoa());
        return "pessoa/edit";
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public String create(@Valid Pessoa pessoa, BindingResult result) {
        if (result.hasErrors()) {
            return "pessoa/edit";
        }
        return "redirect:/pessoa/teste";
    }

    @RequestMapping(value="{id}", method=RequestMethod.GET)
    public String getView(@PathVariable String id, Model model) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(id);
        if (pessoa == null) {
            //throw new ResourceNotFoundException(id);
        }
        model.addAttribute(pessoa);
        model.addAttribute("nome", "maçã");
        return "pessoa/index";
    }
    */
}
