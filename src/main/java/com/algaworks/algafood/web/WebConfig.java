package com.algaworks.algafood.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

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

    /*
        Etag serve para validar se houve alguma alteração desde a última requisição feita no servidor quando o status do cache está stale
        ou seja, quando o max-age de cache já estourou e não está mais fresh, porém caso nada tenha sido alterado no servidor, a requisição
        vai com a Etag e o header If-None-Match, é feita uma comparação dessa Etag com a Etag que está no servidor, quando não teve alteração
        a do servidor vai estar igual a do request, assim não é necessário que o servidor envie um novo response, evitando tráfego e consumo
        de rede desnecessários.

        Habilitando esse filtro como um Bean do spring ele já valida se a Etag foi alterada ou não, caso não tenha sido retorna um código
        304 Not Modified, do contrário deixa o request passar normalmente e adiciona a nova Etag.
     */
    @Bean
    public Filter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }


}
