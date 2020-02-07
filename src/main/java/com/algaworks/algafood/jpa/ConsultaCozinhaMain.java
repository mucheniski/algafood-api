package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.entity.Cozinha;

public class ConsultaCozinhaMain {

	public static void main(String[] args) {		
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
							.web(WebApplicationType.NONE)
							.run(args);
		
		CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);
		
		cadastroCozinha.listar().forEach(System.out::println);
		
		criarNovaCozinha(cadastroCozinha);
		
		buscarPorId(cadastroCozinha, 1L);
		
		atualizar(cadastroCozinha, 1L);
		
		remover(cadastroCozinha, 1L);
		
	}

	private static void remover(CadastroCozinha cadastroCozinha, long id) {
		Cozinha cozinha = new Cozinha();
		cozinha.setId(id);
		cadastroCozinha.remover(cozinha);		
	}

	private static void atualizar(CadastroCozinha cadastroCozinha, long id) {
		Cozinha cozinha = new Cozinha();
		cozinha.setId(id);
		cozinha.setNome("Atualizada");
		cadastroCozinha.salvar(cozinha);
	}

	private static Cozinha buscarPorId(CadastroCozinha cadastroCozinha, long id) {
		Cozinha cozinha = cadastroCozinha.buscar(id);	
		System.out.println("Busca por id: " + cozinha.getNome());
		return cozinha;
	}

	private static void criarNovaCozinha(CadastroCozinha cadastroCozinha) {
		Cozinha cozinha1 = new Cozinha("Brasileira");
		Cozinha cozinha2 = new Cozinha("Japonesa");
		cozinha1 = cadastroCozinha.salvar(cozinha1);
		cozinha2 = cadastroCozinha.salvar(cozinha2);
		
		System.out.printf("%d - %s\n", cozinha1.getId(), cozinha1.getNome());
		System.out.printf("%d - %s\n", cozinha2.getId(), cozinha2.getNome());
	}
	
}
