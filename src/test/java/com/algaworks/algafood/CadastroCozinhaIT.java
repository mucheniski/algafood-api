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

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // o WebEnvironment.MOCK não levanta um server web real, ele é configurado por padrão, por isso precisamos alterar
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {
	
	@LocalServerPort // Essa anotação faz a porta levantada no RANDOM_PORT ser injetada na variável
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	/*
	 * Essa anotação faz com que o método seja executado antes de cada um dos testes
	 * serve para preparar o ambiente para que os testes possam ser todos executados
	 * a partir dessa configuração
	 * */	
	@Before
	public void setUp() {
		
		/*
		 * Apresenra um log da requisição e da resposta caso a validação falhe
		 * */
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); 
		
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
	public void deveConter2CozinhasTest() {
		
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
				.body("", Matchers.hasSize(2)) // Valida se tem 2 itens na resposta
				.body("nome", Matchers.hasItems("Tailandesa", "Indiana")); // Valida o campo nome
	}
	
	@Test
	public void deveRetornarStatus201Test() {
		RestAssured
			.given()
				.body("{ \"nome\": \"Chinesa\" }")
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
				.pathParam("cozinhaId", 2)
				.accept(ContentType.JSON)
			.when()
				.get("/{cozinhaId}")
			.then()
				.statusCode(HttpStatus.OK.value())
				.body("nome", equalTo("Indiana"));
	}
	
	@Test
	public void deveRetornarStatus404Test() {
		RestAssured
			.given()
				.pathParam("cozinhaId", 100) // Cozinha não existe
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
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Indiana");
		cozinhaRepository.save(cozinha2);					 
	}

}
