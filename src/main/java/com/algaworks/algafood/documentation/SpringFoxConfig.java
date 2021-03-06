package com.algaworks.algafood.documentation;

import com.algaworks.algafood.dto.CozinhaDTO;
import com.algaworks.algafood.dto.PedidoResumoDTO;
import com.algaworks.algafood.exception.Problema;
import com.fasterxml.classmate.TypeResolver;
import lombok.var;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// TODO: Continuar a partir da aula 18:29 após terminar o curso, finalizar a documentação do projeto
/*
* A interface Spring WebMvcConfigurer é usada para customizar o Spring MVC no projeto.
* */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

    /*
    * Docket é uma classe do Spring que representa a configuração da API para gerar a especificação OPEN API
    * seria o sumário da documentação
    * */
    @Bean
    public Docket apiDocket() {

        var typeResolver = new TypeResolver();

        var tagCidades          = new Tag("Cidades", "Gerencia as cidades");
        var tagGrupos           = new Tag("Grupos",  "Gerencia os grupos de permissao");
        var tagCozinhas         = new Tag("Cozinhas", "Gerencia as Cozinhas");
        var tagFormaPagamento   = new Tag("FormasPagamento", "Gerencia as formas de pagamento");
        var tagPedidos          = new Tag("Pedidos", "Gerencia os pedidos");

//        Usado apenas para globalOperationParameters(Arrays.asList(parametroCampos)) por isso está comentado
//        var parametroCampos =
//                new ParameterBuilder()
//                    .name("campos")
//                    .description("Nome das propriedades para filtrar o response")
//                    .parameterType("query")
//                    .modelRef(new ModelRef("string"))
//                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                        .select()
                            .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood"))
//                            .paths(PathSelectors.ant("/restaurantes/**")) // Caso queira buscar de endpoints específicos apenas
                   .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, retornosGlobalGET())
                .globalResponseMessage(RequestMethod.POST, retornosGlobalPUTePOST())
                .globalResponseMessage(RequestMethod.PUT, retornosGlobalPUTePOST())
                .globalResponseMessage(RequestMethod.DELETE, retornosGlobalDELETE())
                /*
                * Essa configuração é necessária para especificar globalmente os parametros implícitos, como no exemplo de PedidoController.pesquisarComFiltro
                * na documentação não aparecem os filtros passados no PedidoFiltro, por isso precisa ser especificado aqui.
                * no caso aqui está comentado pois em SquigglyConfig deixamos habilitados apenas para os endpoints /pedidos e /restaurantes
                * */
                //.globalOperationParameters(Arrays.asList(parametroCampos))
                /* A Classe Problema usada para receber os problemas das exceptions da api não é mapeada no swagger porque não estã sendo usada
                   em nenhum controller, por isso para que ela apareça na documentação é preciso apontar manualmente conforme abaixo.
                * */
                .additionalModels(typeResolver.resolve(Problema.class))
                /*
                * Substitui as propriedades apenas para fins de documentação, quando o controller tiver métodos
                * retornando um Pageable.
                * */
                .directModelSubstitute(Pageable.class, PropriedadesPageableOpenAPI.class)
                .ignoredParameterTypes(ServletWebRequest.class)
                /*
                 * Substitui o Page de Cozinha com as propriedades corretas da classe PageCozinhasOpenAPI apenas na documentação
                 * */
                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Page.class, CozinhaDTO.class), PageCozinhasOpenAPI.class))
                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Page.class, PedidoResumoDTO.class), PagePedidosOpenAPI.class))
                .apiInfo(customApiInfo())
                .tags(tagCidades, tagGrupos, tagCozinhas, tagFormaPagamento, tagPedidos);
    }

    private List<ResponseMessage> retornosGlobalGET() {

        var erro406 = new ResponseMessageBuilder()
                .code(HttpStatus.NOT_ACCEPTABLE.value())
                .message("Tipo de recurso não aceito, apenas JSON")
                .build();

        var erro500 = new ResponseMessageBuilder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Erro interno do servidor")
                .responseModel(new ModelRef("Problema"))
                .build();

        return Arrays.asList(erro406, erro500);

    }

    private List<ResponseMessage> retornosGlobalPUTePOST() {

        var erro404 = new ResponseMessageBuilder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Requisição inválida")
                .responseModel(new ModelRef("Problema"))
                .build();

        var erro406 = new ResponseMessageBuilder()
                .code(HttpStatus.NOT_ACCEPTABLE.value())
                .message("Tipo de recurso não aceito, apenas JSON")
                .build();

        var erro415 = new ResponseMessageBuilder()
                .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .message("Requisição recusada porque o corpo está em um formato não suportado")
                .responseModel(new ModelRef("Problema"))
                .build();

        var erro500 = new ResponseMessageBuilder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Erro interno do servidor")
                .responseModel(new ModelRef("Problema"))
                .build();

        return Arrays.asList(erro404, erro406, erro415, erro500);

    }

    private List<ResponseMessage> retornosGlobalDELETE() {

        var erro404 = new ResponseMessageBuilder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Requisição inválida")
                .responseModel(new ModelRef("Problema"))
                .build();

        var erro500 = new ResponseMessageBuilder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Erro interno do servidor")
                .responseModel(new ModelRef("Problema"))
                .build();

        return Arrays.asList(erro404, erro500);

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
