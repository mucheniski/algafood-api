package com.algaworks.algafood.mail;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class Email {

    private Set<String> destinatarios;
    private String assunto;
    private String corpo;

}
