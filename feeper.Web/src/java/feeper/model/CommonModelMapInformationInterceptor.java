/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

public class CommonModelMapInformationInterceptor extends HandlerInterceptorAdapter {
    // This method is unused as this Interceptor is for post handle only
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception { }

    // This method is unused as this Interceptor is for post handle only
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        boolean isRedirectView = modelAndView.getView() instanceof RedirectView;
        boolean isViewObject = modelAndView.getView() == null;
        // if the view name is null then set a default value of true
        boolean viewNameStartsWithRedirect = (modelAndView.getViewName() == null ? true : modelAndView.getViewName().startsWith(UrlBasedViewResolver.REDIRECT_URL_PREFIX));

        if(modelAndView.hasView() && ((isViewObject && !isRedirectView) || (!isViewObject && !viewNameStartsWithRedirect))){
            addCommonModelData(modelAndView);
        }
    }

    public void addCommonModelData(ModelAndView modelAndView){
        modelAndView.addObject("stuff", "importantStuff");
        modelAndView.addObject("moreStuff", "moreImportantStuff");
    }
}