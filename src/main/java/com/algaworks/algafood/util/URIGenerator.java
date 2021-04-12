package com.algaworks.algafood.util;

import lombok.experimental.UtilityClass;
import lombok.var;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

/*
* Anotação do Lombok que transforma a classe em final para não ser extendida e não pode ser declarado construtor
* todos os métodos são estáticos e não precisa ser instanciada
* */
@UtilityClass
public class URIGenerator {

    public static void addUriInHeaderLocation(Object resourceId) {
        /*
         * Essa classe ajuda a criar uma URI usando as informações da requisição atual, por exemplo:
         * protocolo(http/https), domínio, porta, etc...
         * Já busca automáticamente essas informações
         * o path é o caminho da requisição até aqui, ex. http://localhost:8080/cidades
         * */
        var uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(resourceId)
                .toUri();

        // Adiciona a uri ao cabeçalho location.
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setHeader(HttpHeaders.LOCATION, uri.toString());
    }

}
