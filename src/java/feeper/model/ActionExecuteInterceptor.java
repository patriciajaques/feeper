/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import feeper.entity.Pessoa;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class ActionExecuteInterceptor extends HandlerInterceptorAdapter {
 
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        if (request.getRequestURI().startsWith(request.getContextPath() + "/resources") || request.getRequestURI().startsWith(request.getContextPath() + "/login"))
            return true;
        
        String view = request.getRequestURI().replace("/feeper/", "");
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("UsuarioLogado") == null)
        {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        else
        {
            if (!ValidaAcesso(view, (Pessoa)session.getAttribute("UsuarioLogado")))
            {
                response.sendRedirect(request.getContextPath() + "/");
                return false;
            }
            return true;
        }
        
    }
    
    private boolean ValidaAcesso(String view, Pessoa usuario)
    {
        //        if (controller.equalsIgnoreCase("turma"))
        //            return false;
        return true;
    }
    
}
