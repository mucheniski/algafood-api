package com.algaworks.algafood.documentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    /*
    * Docket é uma classe do Spring que representa a configuração da API para gerar a especificação OPEN API
    * seria o sumário da documentação
    * */
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                        .select().apis(RequestHandlerSelectors.any())
                   .build();
    }

}
