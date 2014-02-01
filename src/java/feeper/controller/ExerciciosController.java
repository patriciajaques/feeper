/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import feeper.entity.Exercicio;
import feeper.entity.Pessoa;
import feeper.model.ExercicioService;
import feeper.model.PaginadorUtil;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@SessionAttributes({ "MSG_SUCESSO", "MSG_ERRO", "UsuarioLogado" })
@RequestMapping(value="/exercicios")
public class ExerciciosController extends ApplicationController {
    
    ExercicioService service;
    
    public ExerciciosController()
    {
        this.service = new ExercicioService();
    }
    
    @RequestMapping(method=RequestMethod.GET)
    public String list(Model model) {
        return list("", "", 1, 2, "id", "asc", "", model, null);
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public String list(
            @ModelAttribute("nome") String nome, 
            @ModelAttribute("autor") String autor, 
            @ModelAttribute("currentPage") int currentPage,
            @ModelAttribute("pageSize") int pageSize,
            @ModelAttribute("sortField") String sortField, 
            @ModelAttribute("sortDirection") String sortDirection, 
            @ModelAttribute("gridAction") String gridAction, 
            Model model, 
            BindingResult result) {
        
        //Configurações do Paginador
        if (gridAction.equals("PageSizeChanged") || gridAction.equals("Sorted") || gridAction.equals("Searched"))
            currentPage = 1;

        if (currentPage == 0) currentPage = 1;
        if (pageSize == 0) pageSize = 10;
        if (sortField != null && !sortField.isEmpty()) sortField = "E.ID";
        if (sortDirection != null && !sortDirection.isEmpty()) sortDirection = "Asc";
        //-------------------------
        
        PaginadorUtil<Exercicio> paginador = new PaginadorUtil<Exercicio>();
        
        String sql = "select * from Exercicio as E " +
                    "inner join Pessoa as P " +
                    "on P.ID = E.IdAutor " +
                    "inner join NivelDificuldade as ND " +
                    "on ND.ID = E.IdNivelDificuldade " +
                    "where E.nome like :p0 " +
                    "and P.nome like :p1 ";
        
        String[] params = new String[2];
        params[0] = "%"+ nome + "%";
        params[1] = "%"+ autor + "%";
        
        List<Exercicio> lista = paginador.Execute(
                                        Exercicio.class, 
                                        model, 
                                        sql, 
                                        params, 
                                        currentPage, 
                                        pageSize, 
                                        sortField, 
                                        sortDirection);
        
        model.addAttribute("listaExercicios", lista);
        model.addAttribute("nome", nome);
        model.addAttribute("autor", autor);
        model.addAttribute("MSG_SUCESSO", "");
        model.addAttribute("MSG_ERRO", "");
        
        return "exercicios/list";
    }
    
    @RequestMapping(value="/add", method=RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new Exercicio());
        model.addAttribute("IsAdd", true);
        return "exercicios/edit";
    }
    
    @RequestMapping(value="/saveadd", method=RequestMethod.POST)
    public ModelAndView saveadd(
            @ModelAttribute("exercicio") Exercicio exercicio, 
            @ModelAttribute("htmlcontent") String html,
            HttpSession session,
            BindingResult result) {
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/exercicios", true, true, false));
        
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        
        exercicio.setDataCadastro(new Date());
        exercicio.setIdAutor(usuarioLogado.getId());
        exercicio.setDescricaoHtml(html);
        
        if (service.insert(exercicio))
            mav.addObject("MSG_SUCESSO", "Registro inserido com sucesso");
        else
            mav.addObject("MSG_ERRO", "Ocorreu um erro ao inserir o registro");
        
        return mav;
    }
    
    @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
    public String edit(@PathVariable int id, Model model) {
        
        Exercicio exercicio = service.getById(id);
        exercicio.setValidacoes(service.getValidacoes(id));
        
        model.addAttribute(exercicio);
        model.addAttribute("IsAdd", false);
        
        return "exercicios/edit";
    }
    
    @RequestMapping(value="/saveedit", method=RequestMethod.POST)
    public ModelAndView saveedit(
            @ModelAttribute("exercicio") Exercicio exercicio, 
            @ModelAttribute("htmlcontent") String html,
            BindingResult result) {
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/exercicios", true, true, false));
        
        Exercicio exercicioBanco = service.getById(exercicio.getId());
        
        exercicioBanco.setAtivo(exercicio.isAtivo());
        exercicioBanco.setDescricao(html.getBytes());
        exercicioBanco.setIdNivelDificuldade(exercicio.getIdNivelDificuldade());
        exercicioBanco.setNome(exercicio.getNome());
        
        if (service.update(exercicioBanco))
            mav.addObject("MSG_SUCESSO", "Registro alterado com sucesso");
        else
            mav.addObject("MSG_ERRO", "Ocorreu um erro ao alterar o registro");
        
        return mav;
    }
    
    @RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
    public ModelAndView delete(@PathVariable int id, Model model) {
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/exercicios", true, true, false));
        
        Exercicio exercicio = service.getById(id);
        
        if (service.delete(exercicio))
            mav.addObject("MSG_SUCESSO", "Registro excluído com sucesso");
        else
            mav.addObject("MSG_ERRO", "Ocorreu um erro ao excluir o registro");
        
        return mav;
    }
    
    @RequestMapping(value="/meusexercicios", method=RequestMethod.GET)
    public String meusexercicios() {
        return "exercicios/meusexercicios";
    }
    
    @RequestMapping(value="/responder", method=RequestMethod.GET)
    public String getResponder() {
        return "exercicios/responder";
    }
    
    @RequestMapping(value="/upload", method=RequestMethod.POST, produces="application/json")
    @ResponseBody
    public String upload(MultipartHttpServletRequest request) {
        
        MultipartFile file = request.getFile("filedata");
        
        //file.getBytes()
 
        //String s = request.getParameter("myParamName"); 

        return file.getOriginalFilename();
    }
    
}