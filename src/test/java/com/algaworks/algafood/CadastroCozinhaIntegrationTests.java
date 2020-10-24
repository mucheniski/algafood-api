package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.entity.Cozinha;
import com.algaworks.algafood.service.CozinhaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaIntegrationTests {
	
	/*
	 * Todo teste é dividido em 3 partes
	 * 1 - Cenário
	 * 2 - Aççao
	 * 3 - Validação
	 * 
	 * O teste deve testar apenas uma funcionalidade por teste
	 * 
	 * */

	@Autowired
	private CozinhaService cozinhaService;
	
	@Test
	public void deveCadastrarCozinhaComSucessoTest() {
		// Cenário
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Cozinha Teste");
		
		// Ação
		cozinha = cozinhaService.salvar(cozinha);
		
		// Validação
		assertThat(cozinha).isNotNull();
		assertThat(cozinha.getId()).isNotNull();
	}
	
	// Validação
	@Test(expected = Exception.class)
	public void deveFalharAoCadastrarCozinhaSemNomeTest() {
		// Cenário
		Cozinha cozinha = new Cozinha();
		cozinha.setNome(null);
		
		// Ação
		cozinha = cozinhaService.salvar(cozinha);
	}

}
