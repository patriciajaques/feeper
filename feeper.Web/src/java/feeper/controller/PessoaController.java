package feeper.controller;

import feeper.Data.entity.Pessoa;
import feeper.Data.model.EPerfil;
import feeper.Data.model.ETipoLog;
import feeper.Data.model.Util;
import feeper.Data.service.PessoaService;
import feeper.Data.service.TurmaPessoaService;
import feeper.Data.service.TurmaService;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping(value="/pessoa")
public class PessoaController extends ApplicationController {
    
    private PessoaService service;
    
    public PessoaController()
    {
        this.service = new PessoaService();
    }
    
    @RequestMapping(method=RequestMethod.GET)
    public String list(Model model) {
        
        return list("", 1, 10, "id", "asc", "", model, null);
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public String list(
            @ModelAttribute("nome") String nome, 
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
        if (sortField != null && !sortField.isEmpty()) sortField = "ID";
        if (sortDirection != null && !sortDirection.isEmpty()) sortDirection = "Asc";
        //-------------------------
        
        PaginadorUtil<Pessoa> paginador = new PaginadorUtil<Pessoa>();
        
        String sql = "select * from Pessoa where nome like :p0 ";
        
        String[] params = new String[1];
        params[0] = "%"+ nome + "%";
        
        List<Pessoa> lista = paginador.Execute(
                                        Pessoa.class, 
                                        model, 
                                        sql, 
                                        params, 
                                        currentPage, 
                                        pageSize, 
                                        sortField, 
                                        sortDirection);
        
        model.addAttribute("listaPessoa", lista);
        model.addAttribute("nome", nome);
        
        return "pessoa/list";
    }
    
    @RequestMapping(value="/add", method=RequestMethod.GET)
    public String add(Model model) {
        
        Pessoa pessoa = new Pessoa();
        model.addAttribute(pessoa);
        model.addAttribute("IsAdd", true);
        
        return "pessoa/edit";
    }
    
    @RequestMapping(value="/saveadd", method=RequestMethod.POST)
    public ModelAndView saveadd(
            @ModelAttribute("pessoa") Pessoa pessoa, 
            HttpSession session,
            BindingResult result,
            final RedirectAttributes flash) {
        
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/pessoa", true, true, false));
        
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        
        pessoa.setDataCadastro(new Date());
        pessoa.setSenha("");

        if (service.insert(pessoa))
        {
            log(usuarioLogado.getId(), "SUCESSO: ID: " + pessoa.getId(), ETipoLog.CADASTRAR_PESSOA);
            flash.addFlashAttribute("MSG_SUCESSO", "Registro inserido com sucesso");
        }
        else
        {
            log(usuarioLogado.getId(), "ERRO: ID: " + pessoa.getId(), ETipoLog.CADASTRAR_PESSOA);
            flash.addFlashAttribute("MSG_ERRO", "Ocorreu um erro ao inserir o registro");
        }
        
        return mav;
    }
    
    @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
    public String edit(
            @PathVariable int id, 
            Model model) {
        
        Pessoa pessoa = service.getById(id);
        
        model.addAttribute(pessoa);
        model.addAttribute("IsAdd", false);
        
        return "pessoa/edit";
    }
    
    @RequestMapping(value="/saveedit", method=RequestMethod.POST)
    public ModelAndView saveedit(
            @ModelAttribute("pessoa") Pessoa pessoa, 
            HttpSession session,
            BindingResult result,
            final RedirectAttributes flash) {
        
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/pessoa", true, true, false));
        
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        Pessoa pessoaBanco = service.getById(pessoa.getId());
        
        pessoaBanco.setAtivo(pessoa.isAtivo());
        pessoaBanco.setEmail(pessoa.getEmail());
        pessoaBanco.setNome(pessoa.getNome());
   
        if (service.update(pessoaBanco))
        {
            log(usuarioLogado.getId(), "SUCESSO: ID: " + pessoa.getId(), ETipoLog.ALTERAR_PESSOA);
            flash.addFlashAttribute("MSG_SUCESSO", "Registro alterado com sucesso");
        }
        else
        {
            log(usuarioLogado.getId(), "ERRO: ID: " + pessoa.getId(), ETipoLog.ALTERAR_PESSOA);
            flash.addFlashAttribute("MSG_ERRO", "Ocorreu um erro ao alterar o registro");
        }
        
        return mav;
    }
    
    @RequestMapping(value="/search/{idPerfil}/{word}", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
    @ResponseBody
    public List<Object> search(
            @PathVariable int idPerfil, 
            @PathVariable String word, 
            Model model) {
        
        List<Object> lista = service.search("nome", word, " and IdPerfil = " + idPerfil, "id, nome, email");
        return lista;
    }

}
