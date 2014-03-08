package feeper.controller;

import feeper.Data.entity.Pessoa;
import feeper.Data.entity.Turma;
import feeper.Data.model.ETipoLog;
import feeper.Data.service.TurmaService;
import feeper.model.PaginadorUtil;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value="/turma")
public class TurmaController extends ApplicationController {
    
    private TurmaService service;
    
    public TurmaController()
    {
        this.service = new TurmaService();
    }
    
    @RequestMapping(method=RequestMethod.GET)
    public String list(Model model) {
        
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
        if (gridAction.equals("PageSizeChanged") || gridAction.equals("Sorted") || gridAction.equals("Searched"))
            currentPage = 1;

        if (currentPage == 0) currentPage = 1;
        if (pageSize == 0) pageSize = 10;
        if (sortField != null && !sortField.isEmpty()) sortField = "T.ID";
        if (sortDirection != null && !sortDirection.isEmpty()) sortDirection = "Asc";
        //-------------------------
        
        PaginadorUtil<Turma> paginador = new PaginadorUtil<Turma>();
        
        String sql = "select * from Turma as T " +
                    "inner join Pessoa as P " +
                    "on P.ID = T.IdProfessor " +
                    "where T.nome like :p0 " +
                    "and P.nome like :p1 ";
        
        String[] params = new String[2];
        params[0] = "%"+ nome + "%";
        params[1] = "%"+ professor + "%";
        
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
        
        return "turma/list";
    }
    
    @RequestMapping(value="/add", method=RequestMethod.GET)
    public String add(Model model) {
        
        model.addAttribute(new Turma());
        model.addAttribute("IsAdd", true);
        return "turma/edit";
    }
    
    @RequestMapping(value="/saveadd", method=RequestMethod.POST)
    public ModelAndView saveadd(
            @ModelAttribute("turma") Turma turma, 
            BindingResult result,
            HttpSession session,
            final RedirectAttributes flash) {
        
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/turma", true, true, false));
        
        turma.setDataCadastro(new Date());        
        
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        if (service.insert(turma))
        {
            log(usuarioLogado.getId(), "SUCESSO: ID: " + turma.getId(), ETipoLog.CADASTRAR_TURMA);
            flash.addFlashAttribute("MSG_SUCESSO", "Registro inserido com sucesso");
        }
        else
        {
            log(usuarioLogado.getId(), "ERRO: ID: " + turma.getNome(), ETipoLog.CADASTRAR_TURMA);
            flash.addFlashAttribute("MSG_ERRO", "Ocorreu um erro ao inserir o registro");
        }
        
        return mav;
    }
    
    @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
    public String edit(@PathVariable int id, Model model) {
        
        Turma turma = service.getById(id);
        turma.setAlunos(service.getAlunos(id));
        
        model.addAttribute(turma);
        model.addAttribute("IsAdd", false);
        
        return "turma/edit";
    }
    
    @RequestMapping(value="/saveedit", method=RequestMethod.POST)
    public ModelAndView saveedit(
            @ModelAttribute("turma") Turma turma, 
            BindingResult result,
            HttpSession session,
            final RedirectAttributes flash) {
        
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/turma", true, true, false));
        
        Turma turmaBanco = service.getById(turma.getId());
        
        turmaBanco.setAtivo(turma.isAtivo());
        turmaBanco.setDataEncerramento(turma.getDataEncerramento());
        turmaBanco.setIdProfessor(turma.getIdProfessor());
        turmaBanco.setNome(turma.getNome());
        
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        if (service.update(turmaBanco))
        {
            log(usuarioLogado.getId(), "SUCESSO: ID: " + turma.getId(), ETipoLog.ALTERAR_TURMA);
            flash.addFlashAttribute("MSG_SUCESSO", "Registro alterado com sucesso");
        }
        else
        {
            log(usuarioLogado.getId(), "ERRO: ID: " + turma.getId(), ETipoLog.ALTERAR_TURMA);
            flash.addFlashAttribute("MSG_ERRO", "Ocorreu um erro ao alterar o registro");
        }
        
        return mav;
    }
    
    @RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
    public ModelAndView delete(
            @PathVariable int id, 
            Model model,
            HttpSession session,
            final RedirectAttributes flash) {
        
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/turma", true, true, false));
        
        Turma turma = service.getById(id);
        
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        if (service.delete(turma))
        {
            log(usuarioLogado.getId(), "ERRO: ID: " + turma.getId(), ETipoLog.EXCLUIR_TURMA);
            flash.addFlashAttribute("MSG_SUCESSO", "Registro excluído com sucesso");
        }
        else
        {
            log(usuarioLogado.getId(), "ERRO: ID: " + turma.getId(), ETipoLog.EXCLUIR_TURMA);
            flash.addFlashAttribute("MSG_ERRO", "Ocorreu um erro ao excluir o registro");
        }
        
        return mav;
    }
    
    @RequestMapping(value="/details/{id}", method=RequestMethod.GET)
    public String details(@PathVariable int id, Model model) {
        
        Turma turma = new Turma();
        turma.setNome("Programação 1");
        
        model.addAttribute(turma);
        return "turma/details";
    }
    
    @RequestMapping(value="/change/{id}", method=RequestMethod.GET)
    public ModelAndView change(@PathVariable int id, HttpServletRequest request) {
        
        HttpSession session = request.getSession(false);
        
        if (session != null)
        {
            Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
            List<Turma> minhasTurmas = pessoa.getTurmas();
            for (Turma turma : minhasTurmas) {
                if (turma.getId() == id)
                {
                    session.setAttribute("TurmaSelecionada", turma);
                    break;
                }
            }
        }
        
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/", true, true, false));
        return mav;
    }
    
}