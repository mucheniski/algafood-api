package com.algaworks.algafood;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // o WebEnvironment.MOCK não levanta um server web real, ele é configurado por padrão, por isso precisamos alterar
public class CadastroCozinhaIT {
	
	@LocalServerPort // Essa anotação faz a porta levantada no RANDOM_PORT ser injetada na variável
	private int port;
	
	/*
	 * Essa anotação faz com que o método seja executado antes de cada teste
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
	public void testRetornarStatus201Test() {
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

}
