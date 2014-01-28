/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import feeper.entity.Turma;
import feeper.model.HibernateUtil;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@SessionAttributes({ "MSG_SUCESSO", "MSG_ERRO" })
@RequestMapping(value="/turma")
public class TurmaController extends ApplicationController {
    
    @RequestMapping(method=RequestMethod.GET)
    public String list(Model model) {
        HibernateUtil<Turma> repo = new HibernateUtil<Turma>();
        List<Turma> lista = repo.selecionar(Turma.class);
        model.addAttribute("listaTurma", lista);
        
        return "turma/list";
    }
    
    @RequestMapping(value="/add", method=RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new Turma());
        model.addAttribute("IsAdd", true);
        return "turma/edit";
    }
    
    @RequestMapping(value="/saveadd", method=RequestMethod.POST)
    public ModelAndView saveadd(@ModelAttribute("turma") Turma turma, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/turma", true, true, false));
        
        turma.setAtivo(true);
        turma.setDataCadastro(new Date());
        
        HibernateUtil<Turma> repo = new HibernateUtil<Turma>();
        if (repo.inserir(turma))
            mav.addObject("MSG_SUCESSO", "Registro inserido com sucesso");
        else
            mav.addObject("MSG_ERRO", "Ocorreu um erro ao inserir o registro");
        
        return mav;
    }
    
    @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute(new Turma());
        model.addAttribute("IsAdd", false);
        return "turma/edit";
    }
    
    @RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
    public ModelAndView delete(@PathVariable int id, Model model) {
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/turma", true, true, false));
        
        HibernateUtil<Turma> repo = new HibernateUtil<Turma>();
        Turma turma = repo.selecionar(Turma.class, id);
        
        if (repo.excluir(turma))
            mav.addObject("MSG_SUCESSO", "Registro excluído com sucesso");
        else
            mav.addObject("MSG_ERRO", "Ocorreu um erro ao excluir o registro");
        
        return mav;
    }
    
    @RequestMapping(value="/details/{id}", method=RequestMethod.GET)
    public String details(@PathVariable int id, Model model) {
        Turma turma = new Turma();
        turma.setNome("Programação 1");
        
        model.addAttribute(turma);
        return "turma/details";
    }
    
}