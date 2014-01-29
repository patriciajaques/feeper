/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import feeper.entity.Pessoa;
import feeper.entity.Turma;
import feeper.model.HibernateUtil;
import feeper.model.PaginadorUtil;
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
        //HibernateUtil<Turma> repo = new HibernateUtil<Turma>();
        //PaginadorUtil<Turma> paginador = new PaginadorUtil<Turma>();
        
        //String sql = "select * from Turma";
        //List<Turma> lista = paginador.Execute(Turma.class, model, sql, new String[]{}, 1, 2, null, null);
        
        //model.addAttribute("listaTurma", lista);
        
        //return "turma/list";
        
        
        return list("", "", 1, 10, "id", "asc", "", model, null);
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public String list(
            @ModelAttribute("nome") String nome, 
            @ModelAttribute("professor") String professor, 
            @ModelAttribute("currentPage") int currentPage,
            @ModelAttribute("pageSize") int pageSize,
            @ModelAttribute("sortField") String sortField, 
            @ModelAttribute("sortDirection") String sortDirection, 
            @ModelAttribute("gridAction") String gridAction, 
            Model model, 
            BindingResult result) {
        
        //Configurações do Paginador
        if (gridAction != null && !gridAction.isEmpty())
            currentPage = 0;
        else if (gridAction.equals("PageSizeChanged") || gridAction.equals("Sorted"))
            currentPage = 1;

        if (currentPage == 0) currentPage = 1;
        if (pageSize == 0) pageSize = 10;
        if (sortField != null && !sortField.isEmpty()) sortField = "ID";
        if (sortDirection != null && !sortDirection.isEmpty()) sortDirection = "Asc";
        //-------------------------
        
        
        HibernateUtil<Turma> repo = new HibernateUtil<Turma>();
        PaginadorUtil<Turma> paginador = new PaginadorUtil<Turma>();
        
        String sql = "select * from Turma as T\n" +
                    "inner join Pessoa as P\n" +
                    "on P.ID = T.IdProfessor\n" +
                    "where T.nome like '%:p0%'\n" +
                    "and P.nome like '%:p1%'";
        
        String[] params = new String[2];
        params[0] = nome;
        params[1] = professor;
        
        List<Turma> lista = paginador.Execute(
                                        Turma.class, 
                                        model, 
                                        sql, 
                                        params, 
                                        currentPage, 
                                        pageSize, 
                                        sortField, 
                                        sortDirection);
        
        model.addAttribute("listaTurma", lista);
        model.addAttribute("nome", nome);
        model.addAttribute("professor", professor);
        model.addAttribute("MSG_SUCESSO", "");
        model.addAttribute("MSG_ERRO", "");
        
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
        
        HibernateUtil<Turma> repo = new HibernateUtil<Turma>();
        Turma turma = repo.selecionar(Turma.class, id);
        
        model.addAttribute(turma);
        model.addAttribute("IsAdd", false);
        
        return "turma/edit";
    }
    
    @RequestMapping(value="/saveedit", method=RequestMethod.POST)
    public ModelAndView saveedit(@ModelAttribute("turma") Turma turma, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/turma", true, true, false));
        
        HibernateUtil<Turma> repo = new HibernateUtil<Turma>();
        Turma turmaBanco = repo.selecionar(Turma.class, turma.getId());
        
        turmaBanco.setAtivo(turma.isAtivo());
        turmaBanco.setDataEncerramento(turma.getDataEncerramento());
        turmaBanco.setIdProfessor(turma.getIdProfessor());
        turmaBanco.setNome(turma.getNome());
        
        if (repo.atualizar(turmaBanco))
            mav.addObject("MSG_SUCESSO", "Registro alterado com sucesso");
        else
            mav.addObject("MSG_ERRO", "Ocorreu um erro ao alterar o registro");
        
        return mav;
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