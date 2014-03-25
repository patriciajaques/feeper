package feeper.controller;

import feeper.Data.entity.Pessoa;
import feeper.Data.entity.UploadTemp;
import feeper.Data.model.ETipoLog;
import feeper.Data.model.HibernateUtil;
import feeper.Data.model.StringResult;
import feeper.Data.model.Util;
import feeper.Data.service.PessoaService;
import feeper.Data.service.UploadTempService;
import feeper.model.PaginadorUtil;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
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
    
    @RequestMapping(value="/perfil", method=RequestMethod.GET)
    public String perfil(
            Model model, 
            HttpSession session) {
        
        Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
        model.addAttribute(pessoa);
        
        return "pessoa/perfil";
    }
    
    @RequestMapping(value="/saveperfil", method=RequestMethod.POST)
    public ModelAndView saveperfil(
            @ModelAttribute("nome") String nome, 
            @ModelAttribute("senha") String senha, 
            @ModelAttribute("confirmar") String confirmar, 
            @ModelAttribute("idUploadTemp") String idUploadTemp,
            HttpSession session,
            BindingResult result,
            final RedirectAttributes flash) {
        
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/pessoa/perfil", true, true, false));
        
        StringResult msgSaida = new StringResult();
        if (service.validaSenha(senha, confirmar, msgSaida))
        {
            flash.addFlashAttribute("MSG_ERRO", msgSaida.getResult());
            return mav;
        }
        
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        Pessoa pessoaBanco = service.getById(usuarioLogado.getId());
        
        pessoaBanco.setAtivo(true);
        pessoaBanco.setNome(nome);
        
        if (!senha.isEmpty())
            pessoaBanco.setSenha(Util.criptoMD5(senha));
        
        if (idUploadTemp != null && !idUploadTemp.isEmpty())
        {
            pessoaBanco.setPossuiFoto(true);
            UploadTempService repoUploadTemp = new UploadTempService();
            UploadTemp uploadTemp = repoUploadTemp.getByGuid(idUploadTemp);
            
            String path = session.getServletContext().getRealPath("/resources/img/photo/photo-" + usuarioLogado.getId() + ".png");
            
            Util.saveToPNG(
                    uploadTemp.getArquivo(),
                    path,
                    45,
                    45);
        }
   
        if (service.update(pessoaBanco))
        {
            log(usuarioLogado.getId(), "SUCESSO: ID: " + usuarioLogado.getId(), ETipoLog.ALTERAR_PESSOA);
            flash.addFlashAttribute("MSG_SUCESSO", "Registro alterado com sucesso");
        }
        else
        {
            log(usuarioLogado.getId(), "ERRO: ID: " + usuarioLogado.getId(), ETipoLog.ALTERAR_PESSOA);
            flash.addFlashAttribute("MSG_ERRO", "Ocorreu um erro ao alterar o registro");
        }
        return mav;
    }
    
    @RequestMapping(value="/uploadphoto", method=RequestMethod.POST, produces="application/json")
    @ResponseBody
    public String uploadphoto(MultipartHttpServletRequest request) {
        
        MultipartFile file = request.getFile("filedata");
        HttpSession session = request.getSession();
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        
        try {
            UploadTemp uploadTemp = new UploadTemp();
            uploadTemp.setGuid(UUID.randomUUID().toString());
            uploadTemp.setArquivo(file.getBytes());
            HibernateUtil<UploadTemp> repo = new HibernateUtil<UploadTemp>(UploadTemp.class);
            if (repo.insert(uploadTemp))
            {
                log(usuarioLogado.getId(), "SUCESSO: ID: " + uploadTemp.getId() + " NOME: " + file.getOriginalFilename(), ETipoLog.UPLOAD_EXERCICIO);
                return uploadTemp.getGuid();
            }
            else
            {
                log(usuarioLogado.getId(), "ERRO: NOME: " + file.getOriginalFilename(), ETipoLog.UPLOAD_EXERCICIO);
                return "erro";
            }
            
        } catch (IOException ex) {
            return "";
        }
    }

}
