/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RequestFilter implements Filter {

    private static ThreadLocal<HttpServletRequest> localRequest = new ThreadLocal<HttpServletRequest>();


    public static HttpServletRequest getRequest() {
        return localRequest.get();
    }

    public static HttpSession getSession() {
        HttpServletRequest request = localRequest.get();
        return (request != null) ? request.getSession() : null;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            localRequest.set((HttpServletRequest) servletRequest);
        }

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            localRequest.remove();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}