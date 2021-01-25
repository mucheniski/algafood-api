package com.algaworks.algafood;

import static org.hamcrest.CoreMatchers.equalTo;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.entity.Cozinha;
import com.algaworks.algafood.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // o WebEnvironment.MOCK não levanta um server web real, ele é configurado por padrão, por isso precisamos alterar
@TestPropertySource("/application-test.properties")
public class CozinhaIT {
	
	private static final int COZINHA_ID_INEXISTENTE = 100;

	@LocalServerPort // Essa anotação faz a porta levantada no RANDOM_PORT ser injetada na variável
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private Cozinha cozinhaIndiana = new Cozinha();
	
	private Long totalRegistros = 0L;
	
	/*
	 * Essa anotação faz com que o método seja executado antes de cada um dos testes
	 * serve para preparar o ambiente para que os testes possam ser todos executados
	 * a partir dessa configuração
	 * */	
	@Before
	public void setUp() {
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); /* Apresenra um log da requisição e da resposta caso a validação falhe */
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
	
		databaseCleaner.clearTables(); // Limpa todas as tabelas antes de cada teste
		preparaDados();
		
	}
	
	@Test
	public void deveRetornarStatus200Test() {
		
		// Cenário, ação e validação
		RestAssured
			.given()
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.statusCode(HttpStatus.OK.value());
	}	
	
	/*
	 * em resources/db/migration;aferMigrate.sql são inseridas duas cozinhas quando o projeto sobe
	 * */
	@Test
	public void deveContarCozinhasTest() {
		
		/*
		 * Apresenra um log da requisição e da resposta caso a validação falhe
		 * */
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); 
		// Cenário, ação e validação
		RestAssured
			.given()
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.body("", Matchers.hasSize(totalRegistros.intValue())); // Valida se tem 2 itens na resposta
	}
	
	@Test
	public void deveCadastrarUmaCozinhaTest() {
		
		String cozinhaChinesaJson = ResourceUtils.getContentFromResource("/arquivosjson/cozinhaChinesa.json");
		
		RestAssured
			.given()
				.body(cozinhaChinesaJson)
				.contentType(ContentType.JSON) // Tipo de conteúdo que estou enviando na requisição
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.CREATED.value());
	}
		
	@Test
	public void deveRetornarRespostaESatatusCorretosTest() {		
		RestAssured
			.given()
				.pathParam("cozinhaId", cozinhaIndiana.getId())
				.accept(ContentType.JSON)
			.when()
				.get("/{cozinhaId}")
			.then()
				.statusCode(HttpStatus.OK.value())
				.body("nome", equalTo(cozinhaIndiana.getNome()));
	}
	
	@Test
	public void deveRetornarStatus404Test() {		
		RestAssured
			.given()
				.pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
				.accept(ContentType.JSON)
			.when()
				.get("/{cozinhaId}")
			.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void preparaDados() {
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		cozinhaRepository.save(cozinha1);		
		
		cozinhaIndiana.setNome("Indiana");
		cozinhaRepository.save(cozinhaIndiana);	
		
		totalRegistros = cozinhaRepository.count();
	}

}
