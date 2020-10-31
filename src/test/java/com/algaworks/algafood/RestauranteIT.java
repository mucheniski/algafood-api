package com.algaworks.algafood;

import java.math.BigDecimal;

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
import com.algaworks.algafood.entity.Restaurante;
import com.algaworks.algafood.repository.CozinhaRepository;
import com.algaworks.algafood.repository.RestauranteRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // o WebEnvironment.MOCK não levanta um server web real, ele é configurado por padrão, por isso precisamos alterar
@TestPropertySource("/application-test.properties")
public class RestauranteIT {
	
	private static final int RESTAURANTE_INEXISTENTE = 1000;

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Before
	public void setUp() {
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/restaurantes";
		
		databaseCleaner.clearTables();
		preparaDados();
		
	}
	
	@Test
	public void deveRetornarStatus200Test() {
		RestAssured
			.given()
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarNotFound() {
		RestAssured
			.given()
				.pathParam("restauranteId", RESTAURANTE_INEXISTENTE)
				.accept(ContentType.JSON)
			.when()
				.get("/{restauranteId}")
			.then()
				.statusCode(HttpStatus.NOT_FOUND.value());			
	}
	
	@Test
	public void deveCadastrarUmRestauranteTest() {
		
		String restauranteTesteJson = ResourceUtils.getContentFromResource("/arquivosjson/restauranteOk.json");
		
		RestAssured
			.given()
				.body(restauranteTesteJson)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.CREATED.value());
	}	
	
	@Test
	public void deveApresentarErroRestauranteSemNome() {
		String restauranteTesteJson = ResourceUtils.getContentFromResource("/arquivosjson/restauranteSemNome.json");
		
		RestAssured
			.given()
				.body(restauranteTesteJson)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
		
	}
	
	private void preparaDados() {
		
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Cozinha1");
		cozinhaRepository.save(cozinha1);
		
		Restaurante restaurante1 = new Restaurante();
		restaurante1.setNome("Restaurante1");
		restaurante1.setCozinha(cozinha1);
		restaurante1.setTaxaFrete(new BigDecimal(5.0));
		
		restauranteRepository.save(restaurante1);
	}

}
