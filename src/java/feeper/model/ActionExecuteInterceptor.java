/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import feeper.service.TelaService;
import feeper.service.MensagemService;
import feeper.service.TelaPerfilService;
import feeper.service.NovidadeService;
import feeper.entity.Pessoa;
import feeper.entity.Turma;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

@Component
public class ActionExecuteInterceptor extends HandlerInterceptorAdapter {

    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView
            ) throws Exception {
        
        try {
            if (request.getRequestURI().startsWith(request.getContextPath() + "/resources"))
                return;
            
            if (handler instanceof HandlerMethod)
            {   
                if (((HandlerMethod)handler).getMethodAnnotation(DontValidateAccess.class) != null)
                    return;
                if (((HandlerMethod)handler).getMethod().getDeclaringClass().getAnnotation(DontValidateAccess.class) != null)
                    return;
            }
        } catch (Exception e) {
        }
        
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("UsuarioLogado") != null)
        {
            if (((Pessoa)session.getAttribute("UsuarioLogado")).getIdPerfil() > 1)
            {
                carregaBadges(modelAndView, session);
                carregaTurmas(session);
            }
            
        }
    }
    
    
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        
        String view = "";
        
        try {
            if (request.getRequestURI().startsWith(request.getContextPath() + "/resources"))
                return true;
            
            if (handler instanceof HandlerMethod)
            {   
                if (((HandlerMethod)handler).getMethodAnnotation(DontValidateAccess.class) != null)
                    return true;
                if (((HandlerMethod)handler).getMethod().getDeclaringClass().getAnnotation(DontValidateAccess.class) != null)
                    return true;
            }
        } catch (Exception e) {
        }
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("UsuarioLogado") == null)
        {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        else
        {
            view = getFullViewName(handler, request);
            
            if (!validaAcesso(view, (Pessoa)session.getAttribute("UsuarioLogado")))
            {
                response.sendRedirect(request.getContextPath() + "/");
                return false;
            }
            return true;
        }
        
    }
    
    private boolean validaAcesso(String view, Pessoa usuario)
    {
        //System.out.println("View:" + view);
        
        TelaService repoTela = new TelaService();
        TelaPerfilService repoTelaPerfil = new TelaPerfilService();
        
        int idTela = repoTela.getIdTelaByDescricao(view);
        return repoTelaPerfil.verificaAcesso(idTela, usuario.getId());
    }
    
    private String getFullViewName(Object handler, HttpServletRequest request)
    {
        String content = "";
        try
        {
            if (handler instanceof HandlerMethod)
            {
                String[] controllerName = ((HandlerMethod)handler).getMethod().getDeclaringClass().getAnnotation(RequestMapping.class).value();
                String[] viewName = ((HandlerMethod)handler).getMethodAnnotation(RequestMapping.class).value();
                
                if (controllerName.length > 0)
                    content += controllerName[0].replace("/", "");
                if (viewName.length > 0)
                    content += viewName[0];
                while (content.indexOf("/") != content.lastIndexOf("/"))
                    content = content.substring(0, content.lastIndexOf("/"));
            }
            else if (handler instanceof ParameterizableViewController)
            {
                content = ((ParameterizableViewController)handler).getViewName();
            }
            else
            {
                content = request.getRequestURI().replace("/feeper/", "");
            }
            return content;
        } catch (Exception e) {
            return "";
        }
    }

    private void carregaBadges(ModelAndView modelAndView, HttpSession session) {
        
        int idPessoa = ((Pessoa)session.getAttribute("UsuarioLogado")).getId();
        int contBadgeNovidades = 0;
        int contBadgeMensagens = 0;
        MensagemService repoMensagem = new MensagemService();
        NovidadeService repoNovidade = new NovidadeService();
        
        //contBadgeNovidades = repoNovidade.getCountMinhasNovidades(idPessoa);
        contBadgeMensagens = repoMensagem.getCountMinhasMensagens(idPessoa);
        
        modelAndView.addObject("BadgeNovidades", contBadgeNovidades > 0 ? "<span class=\"badge badge-important\">"+ contBadgeNovidades +"</span>" : "");
        modelAndView.addObject("BadgeMensagens", contBadgeMensagens > 0 ? "<span class=\"badge badge-important\">"+ contBadgeMensagens +"</span>" : "");
    }
    
    private void carregaTurmas(HttpSession session) {
        
        if (session.getAttribute("MinhasTurmas") != null) return;
            
        Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
        List<Turma> turmas = pessoa.getTurmas();
        StringBuilder html = new StringBuilder();
        
        for (final Turma turma : turmas)
        {
            html.append("<li><a href=\"#\" onclick=\"changeTurma(").append(turma.getId()).append(");\">").append(turma.getNome()).append("</a></li>");
        }
        session.setAttribute("MinhasTurmas", html.toString());
    }
    
    
}
