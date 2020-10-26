package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.entity.Cozinha;
import com.algaworks.algafood.exception.CozinhaNaoEncotradaException;
import com.algaworks.algafood.exception.EntidadeEmUsoException;
import com.algaworks.algafood.service.CozinhaService;


/*
 * O plugin
 * 			<plugin>
				<artifactId>maven-failsafe-plugin</artifactId>
			</plugin>
	Por padrão precisa que as classes de teste terminem com o sufixo IT (Integration Test)
	o plugin já vem configurado dessa forma, ele faz com que os testes de integração não 
	sejam executados no build do projeto, porque pode ser muito custoso executar esse tipo
	de teste no build do projeto.
	Eles serão executados em outro momento pelo plugin
	O Plugin é apenas para testes de integração, os testes unitários continuam normalmente.
 * */


@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaIT {
	
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
	@Test(expected = ConstraintViolationException.class)
	public void deveFalharAoCadastrarCozinhaSemNomeTest() {
		// Cenário
		Cozinha cozinha = new Cozinha();
		cozinha.setNome(null);
		
		// Ação
		cozinha = cozinhaService.salvar(cozinha);
	}
	
	@Test(expected = EntidadeEmUsoException.class)
	public void deveFalharAoExcluirCozinhaEmUsoTest() {
		cozinhaService.remover(1L);
	}
	
	@Test(expected = CozinhaNaoEncotradaException.class)
	public void deveFalharAoExcluirCozinhaInexistenteTest() {
		cozinhaService.remover(1000L);
	}

}
