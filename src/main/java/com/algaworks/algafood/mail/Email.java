package com.algaworks.algafood.mail;

import lombok.*;

import java.util.Map;
import java.util.Set;

@ToString
@Getter
@Builder
public class Email {

    /*
        O Singular do lombok serve para ao inves de passar o Set, ele singulariza
        assim onde for usado pode ser passada de um a um ex:
        .destinatario("Destinatario 1")
        .destinatario("Destinarario 2")
        Assim os valores são adicionados ao Set
     */
    @Singular
    private Set<String> destinatarios;

    @NonNull
    private String assunto;

    @NonNull
    private String corpo;

    // Também pode ser especificado qual o nome no singular
    @Singular("variavel")
    private Map<String, Object> variaveis;

}
