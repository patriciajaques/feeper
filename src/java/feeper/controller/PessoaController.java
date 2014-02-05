/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import feeper.entity.Pessoa;
import feeper.entity.TurmaPessoa;
import feeper.entity.TurmaPessoaId;
import feeper.model.EPerfil;
import feeper.model.HibernateUtil;
import feeper.model.PessoaService;
import feeper.model.TurmaPessoaService;
import feeper.model.TurmaService;
import feeper.model.Util;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping(value="/pessoa")
public class PessoaController extends ApplicationController {
    
    private PessoaService service;
    
    public PessoaController()
    {
        this.service = new PessoaService();
    }
    
    /*
     * Métodos ALUNO
     */
    @RequestMapping(value="/addaluno/{idTurma}", method=RequestMethod.GET)
    public String addAluno(@PathVariable int idTurma, Model model) {
        
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPerfil(EPerfil.ALUNO);
        
        model.addAttribute(pessoa);
        model.addAttribute("IsAdd", true);
        model.addAttribute("IdTurma", idTurma);
        
        return "pessoa/edit";
    }
    
    @RequestMapping(value="/saveadd", method=RequestMethod.POST)
    public ModelAndView saveadd(@ModelAttribute("pessoa") Pessoa pessoa, @ModelAttribute("idTurma") int idTurma, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/closemodal", true, true, false));
        
        pessoa.setDataCadastro(new Date());
        pessoa.setSenha(Util.gerarSenha(8));
        pessoa.setIdNivelDificuldade(1);
   
        if (service.insert(pessoa))
        {
            if (pessoa.getId() > 0)
            {
                TurmaPessoaService turmaPessoaService = new TurmaPessoaService();
                turmaPessoaService.insertIfNotExist(idTurma, pessoa.getId());
            }
        }
        return mav;
    }
    
    @RequestMapping(value="/editaluno/{id}", method=RequestMethod.GET)
    public String editAluno(@PathVariable String id, Model model) {
        
        int idTurma = Integer.parseInt(id.split("@")[0]);
        int idAluno = Integer.parseInt(id.split("@")[1]);
        
        Pessoa pessoa = service.getById(idAluno);
        
        model.addAttribute(pessoa);
        model.addAttribute("IsAdd", false);
        model.addAttribute("IdTurma", idTurma);
        
        return "pessoa/edit";
    }
    
    @RequestMapping(value="/saveedit", method=RequestMethod.POST)
    public ModelAndView saveedit(@ModelAttribute("pessoa") Pessoa pessoa, @ModelAttribute("idTurma") int idTurma, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/closemodal", true, true, false));
        
        pessoa.setDataCadastro(new Date());
        pessoa.setSenha(Util.gerarSenha(8));
        pessoa.setIdNivelDificuldade(1);
        
        Pessoa pessoaBanco = service.getById(pessoa.getId());
        
        pessoaBanco.setAtivo(pessoa.isAtivo());
        pessoaBanco.setEmail(pessoa.getEmail());
        pessoaBanco.setNome(pessoa.getNome());
   
        service.update(pessoaBanco);
        
        return mav;
    }
    
    @RequestMapping(value="/deleteturmaaluno/{id}", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public String delete(@PathVariable String id, Model model) {

        int idTurma = Integer.parseInt(id.split("@")[0]);
        int idAluno = Integer.parseInt(id.split("@")[1]);
        
        TurmaService turmaService = new TurmaService();
        
        if (!turmaService.removeAluno(idTurma, idAluno))
            return "err";
        
        return "ok";
    }
    
    /*
     * Métodos PROFESSOR
     */
    @RequestMapping(value="/addprofessor", method=RequestMethod.GET)
    public String addProfessor(Model model) {
        
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPerfil(EPerfil.PROFESSOR);
        
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
    
    @RequestMapping(value="/search/{word}", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
    @ResponseBody
    public List<Object> search(@PathVariable String word, Model model) {
        List<Object> lista = service.search("nome", word, "id, nome");
        return lista;
    }

}
