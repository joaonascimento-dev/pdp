package br.fatec.pdp.filter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Ivan
 */
@Configuration
public class FilterConfig {
            
    @Autowired
    private UsuarioFilter usuarioFilter;

    @Bean
    public FilterRegistrationBean<UsuarioFilter> registerUsuarioFilter() {
        FilterRegistrationBean<UsuarioFilter> bean = new FilterRegistrationBean<>();
        
        bean.setFilter(usuarioFilter);
        bean.addUrlPatterns("/*");
        
        return bean;
    }
    
}
