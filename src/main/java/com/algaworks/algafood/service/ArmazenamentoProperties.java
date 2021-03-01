package com.algaworks.algafood.service;

import com.amazonaws.regions.Regions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@ConfigurationProperties("algafood.armazenamento")
@Getter
@Setter
public class ArmazenamentoProperties {

    /**
     * A classe interna local já precisa ser instanciada senão fica nula e acaba não fazendo
     * o bind corretamente
     */
    private Local local =  new Local();
    private AmazonS3 amazonS3 = new AmazonS3();

    /**
     * É atribuido no bean o caminho porque seque a nomenclatura definida no application.properties
     * algafood.armazenamento.local.diretorioFotos
     * o spring já converte a string do valor em um Path
     */
    @Getter
    @Setter
    public class Local {
        private Path diretorioFotos;
    }

    /**
     * Da mesma forma que a classe Local a AmazonS3 também segue o mesmo padrão da nomenclatura
     * algafood.armazenamento.amazonS3...
     */
    @Getter
    @Setter
    public class AmazonS3 {
        private String idChaveAcesso;
        private String chaveAcessoSecreta;
        private String nomeBucket;
        private Regions regiao;
        private String diretorioFotos;
    }

}
