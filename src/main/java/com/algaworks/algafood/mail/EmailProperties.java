package com.algaworks.algafood.mail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/*
  O previxo vem do application.properties e o remetente que segue e o nome da próxima propriedade
  definindo como um componente o projeto spring já encontra
 */
@Validated // Classe validade pelo Spring
@Getter
@Setter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {

    @NotNull
    private String remetente;

    @NotNull
    private String servicoUsado;

}
