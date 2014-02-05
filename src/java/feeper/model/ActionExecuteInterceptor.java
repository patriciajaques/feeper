/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import feeper.entity.Pessoa;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

@Component
public class ActionExecuteInterceptor extends HandlerInterceptorAdapter {
 
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
                if (content.indexOf("/") != content.lastIndexOf("/"))
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
    
    
}
