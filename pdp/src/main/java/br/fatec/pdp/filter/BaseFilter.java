package br.fatec.pdp.filter;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ivan
 */
@Component
public abstract class BaseFilter {
    
    protected boolean isStaticResource(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        String url = httpRequest.getRequestURL().toString();
        
        //System.out.println("url: " + url);
        
        //começa com N caracteres tem PONTO e mais N caracteres
        //Ex: http://localhost:8080/gerenciador/style.css
        return url.matches(".*\\..*");
    }
    
}
