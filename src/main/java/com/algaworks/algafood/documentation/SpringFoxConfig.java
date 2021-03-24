package com.algaworks.algafood.documentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
* A interface Spring WebMvcConfigurer é usada para customizar o Spring MVC no projeto.
* */
@Configuration
@EnableSwagger2
public class SpringFoxConfig implements WebMvcConfigurer {

    /*
    * Docket é uma classe do Spring que representa a configuração da API para gerar a especificação OPEN API
    * seria o sumário da documentação
    * */
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                        .select()
                            .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood"))
//                            .paths(PathSelectors.ant("/restaurantes/**")) // Caso queira buscar de endpoints específicos apenas
                   .build()
                .apiInfo(customApiInfo());
    }

    /*
    * Informações da API que serão exibidas no swagger-ui
    * */
    public ApiInfo customApiInfo() {
        return new ApiInfoBuilder()
                    .title("Algafood API")
                    .description("Api do curso Algaworks")
                    .version("1")
                    .contact(new Contact("Developer", "github.com", "developer@example.com"))
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }
}
