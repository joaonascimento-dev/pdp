package br.fatec.pdp.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

/**
 *
 * @author Ivan
 */
@Component
public class UsuarioFilter extends BaseFilter implements Filter {
 

    /* @SuppressWarnings("unchecked") */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession();

        session.setMaxInactiveInterval(43200); //2022-11-04 Limite de tempo da sessão

        request.setAttribute("usuarioSessao", session.getAttribute("usuario"));

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig fc) throws ServletException {
        //
    }

    @Override
    public void destroy() {
        //
    }
    
}
