package com.algaworks.algafood.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /*
        Por padrão ao deixar assim todos os origins são aceitos no CORS de forma
        globla no projeto, também por padrão os métodos simples GET, HEAD e POST são permitidos
        porém nesse caso especificamos todos com allowedMathods(*)
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*");
    }

}
