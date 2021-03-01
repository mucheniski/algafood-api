package com.algaworks.algafood.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Configuration {

    @Autowired
    private ArmazenamentoProperties armazenamentoProperties;

    /*
        É anotado com @Bean para registrar que esse método é um bean spring que produz uma
        instância de amazonS3 para o projeto.
     */
    @Bean
    public AmazonS3 amazonS3() {

        var credenciais = new BasicAWSCredentials(armazenamentoProperties.getAmazonS3().getIdChaveAcesso(), armazenamentoProperties.getAmazonS3().getChaveAcessoSecreta());

        // Esse builer é do próprio SDK da amazon e cria uma instância de S3
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credenciais))
                .withRegion(armazenamentoProperties.getAmazonS3().getRegiao())
                .build();
    }

}
