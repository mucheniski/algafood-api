package com.algaworks.algafood;

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
	
	@Test
	public void deveRetornarStatus200Test() {
		
		/*
		 * Apresenra um log da requisição e da responsta caso a validação falhe
		 * */
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); 
		
		// Cenário, ação e validação
		RestAssured
			.given()
				.basePath("/cozinhas")
				.port(port)
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.statusCode(HttpStatus.OK.value());
		
	}

}
