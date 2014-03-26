/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import feeper.Data.entity.FilaNovaSenha;
import feeper.Data.entity.Pessoa;
import feeper.Data.model.ETipoLog;
import feeper.Data.model.StringResult;
import feeper.Data.model.Util;
import feeper.Data.service.FilaNovaSenhaService;
import feeper.Data.service.PessoaService;
import feeper.model.DontValidateAccess;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@DontValidateAccess
@Controller
@RequestMapping(value="/senha")
public class SenhaController extends ApplicationController {
    
    @RequestMapping(value="/esqueciminhasenha", method=RequestMethod.GET)
    public String esqueciminhasenha(HttpServletRequest request) {
        return "senha/esqueciminhasenha";
    }
    
    @RequestMapping(value="/confirmaresqueciminhasenha", method=RequestMethod.POST)
    public ModelAndView confirmaresqueciminhasenha(
            HttpServletRequest request
            , final RedirectAttributes flash) {
        
        ModelAndView mav = new ModelAndView();
        
        String email = request.getParameter("email");
        
        PessoaService repoPessoa = new PessoaService();
        Pessoa pessoa = repoPessoa.validaLogin(email);
        if (pessoa == null || pessoa.getId() == 0)
        {
            mav.setView(new RedirectView("/senha/esqueciminhasenha", true, true, false));
            flash.addFlashAttribute("MSG_ERRO", "E-mail inválido!");
        }
        else
        {
            log(pessoa.getId(), "IP: " + request.getRemoteAddr(), ETipoLog.LOGIN);
            
            FilaNovaSenhaService repoFila = new FilaNovaSenhaService();
            if (repoFila.enviaEmailChave(pessoa.getId()))
                flash.addFlashAttribute("MSG_SUCESSO", "Enviamos com sucesso uma mensagem para seu endereço de e-mail! Verifique-a e siga os passos da mensagem.");
            else
                flash.addFlashAttribute("MSG_ERRO", "Ocorreu um erro ao enviar o e-mail para a troca de senha");
            
            mav.setView(new RedirectView("/login", true, true, false));                
        }
        return mav;
    }
    
    @RequestMapping(value="/trocarsenha", method=RequestMethod.GET)
    public ModelAndView trocarsenha(
            HttpServletRequest request,
            HttpSession session) {
        
        ModelAndView mav = new ModelAndView();
        
        if (session.getAttribute("TrocaSenha") == null || !Boolean.parseBoolean(session.getAttribute("TrocaSenha").toString()))
        {
            session.setAttribute("TrocaSenha", null);
            mav.setView(new RedirectView("/login", true, true, false));
            return mav;
        }
        
        //mav.addObject("Pessoa", session.getAttribute("Pessoa"));
        
        mav.setViewName("senha/trocarsenha");
        return mav;
    }
    
    @RequestMapping(value="/salvartrocarsenha", method=RequestMethod.POST)
    public ModelAndView salvartrocarsenha(
            @ModelAttribute("senha") String senha, 
            @ModelAttribute("confirmar") String confirmar,
            HttpSession session,
            HttpServletRequest request,
            final RedirectAttributes flash) {
        
        FilaNovaSenhaService repoFila = new FilaNovaSenhaService();
        FilaNovaSenha filaNovaSenha = (FilaNovaSenha)session.getAttribute("FilaNovaSenha");
        
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/senha/trocarsenha/", true, true, false));
        
        PessoaService repoPessoa = new PessoaService();
        StringResult msgSaida = new StringResult();
        if (!repoPessoa.validaSenha(senha, confirmar, msgSaida))
        {
            flash.addFlashAttribute("MSG_ERRO", msgSaida.getResult());
            return mav;
        }
        
        Pessoa pessoaSession = (Pessoa) session.getAttribute("PessoaTrocaSenha");
        Pessoa pessoaBanco = repoPessoa.getById(pessoaSession.getId());
        pessoaBanco.setAtivo(true);
        pessoaBanco.setSenha(Util.criptoMD5(senha));
        
        if (repoPessoa.update(pessoaBanco))
        {
            session.setAttribute("FilaNovaSenha", null);
            session.setAttribute("PessoaTrocaSenha", null);
            session.setAttribute("TrocaSenha", null);
            
            repoFila.deleteByIdPessoa(pessoaBanco.getId());
            
            log(pessoaBanco.getId(), "SUCESSO: ID: " + pessoaBanco.getId(), ETipoLog.ALTERAR_PESSOA);
            flash.addFlashAttribute("MSG_SUCESSO", "Senha alterada com sucesso");
        }
        else
        {
            log(pessoaBanco.getId(), "ERRO: ID: " + pessoaBanco.getId(), ETipoLog.ALTERAR_PESSOA);
            flash.addFlashAttribute("MSG_ERRO", "Ocorreu um erro ao alterar a senha");
        }
        
        mav.setView(new RedirectView("/login", true, true, false));
        return mav;
    }
    
    @RequestMapping(value="/validarchave/{chave}", method=RequestMethod.GET)
    public ModelAndView validarchave(
            @PathVariable String chave,
            HttpSession session,
            final RedirectAttributes flash) {
        
        ModelAndView mav = new ModelAndView();
        
        try {
            UUID uuid = UUID.fromString(chave);
            if (!uuid.toString().equals(chave))
                throw new Exception();
        } catch (Exception e) {
            flash.addFlashAttribute("MSG_ERRO", "Chave para troca de senha inválida!");
            mav.setView(new RedirectView("/login", true, true, false));
            return mav;
        }

        FilaNovaSenhaService repoFila = new FilaNovaSenhaService();
        FilaNovaSenha filaNovaSenha = repoFila.getByChave(chave);
        if (filaNovaSenha == null)
        {
            flash.addFlashAttribute("MSG_ERRO", "Chave para troca de senha inválida!");
            mav.setView(new RedirectView("/login", true, true, false));
            return mav;
        }
        
        boolean prazoExcedido = repoFila.validaPrazoChave(chave);
        if (prazoExcedido)
        {
            repoFila.deleteByChave(chave);
            flash.addFlashAttribute("MSG_ERRO", "Chave para troca de senha já expirou!");
            mav.setView(new RedirectView("/login", true, true, false));
            return mav;
        }
        
        PessoaService repoPessoa = new PessoaService();
        Pessoa pessoa = repoPessoa.getById(filaNovaSenha.getIdPessoa());
        
        session.setAttribute("FilaNovaSenha", filaNovaSenha);
        session.setAttribute("PessoaTrocaSenha", pessoa);
        session.setAttribute("TrocaSenha", true);
            
        mav.setView(new RedirectView("/senha/trocarsenha", true, true, false));
        return mav;
    }
    
}
