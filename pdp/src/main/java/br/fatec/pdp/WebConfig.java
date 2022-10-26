package br.fatec.pdp;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        
        //Path uploadDir = Paths.get("./img"); //WINDOWS
        Path uploadDir = Paths.get("img"); // LINUX
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        //registry.addResourceHandler("/img/**").addResourceLocations("file:/" + uploadPath + "/") //WINDOWS
        registry.addResourceHandler("/img/**").addResourceLocations("file:///" + uploadPath + "/") //LINUX
                .setCachePeriod(3600*24);
        
    }
}
