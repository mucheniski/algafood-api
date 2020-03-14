//package com.algaworks.algafood.jpa;
//
//import org.springframework.boot.WebApplicationType;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.context.ApplicationContext;
//
//import com.algaworks.algafood.AlgafoodApiApplication;
//import com.algaworks.algafood.entity.Cozinha;
//import com.algaworks.algafood.repository.CozinhaRepository;
//
//public class CozinhaMain {
//
//	public static void main(String[] args) {		
//		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
//							.web(WebApplicationType.NONE)
//							.run(args);
//		
//		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);
//		
//		cozinhaRepository.listar().forEach(System.out::println);
//		
//		criar(cozinhaRepository);
//		
//		buscar(cozinhaRepository, 1L);
//		
//		atualizar(cozinhaRepository, 1L);
//		
//		remover(cozinhaRepository, 1L);
//		
//	}
//
//	private static void remover(CozinhaRepository cozinhaRepository, long id) {		
//		cozinhaRepository.remover(id);		
//	}
//
//	private static void atualizar(CozinhaRepository cozinhaRepository, long id) {
//		Cozinha cozinha = new Cozinha();
//		cozinha.setId(id);
//		cozinha.setNome("Atualizada");
//		cozinhaRepository.salvar(cozinha);
//	}
//
//	private static Cozinha buscar(CozinhaRepository cozinhaRepository, long id) {
//		Cozinha cozinha = cozinhaRepository.buscar(id);	
//		System.out.println("Busca por id: " + cozinha.getNome());
//		return cozinha;
//	}
//
//	private static void criar(CozinhaRepository cozinhaRepository) {
//		Cozinha cozinha1 = new Cozinha("Brasileira");
//		Cozinha cozinha2 = new Cozinha("Japonesa");
//		cozinha1 = cozinhaRepository.salvar(cozinha1);
//		cozinha2 = cozinhaRepository.salvar(cozinha2);
//		
//		System.out.printf("%d - %s\n", cozinha1.getId(), cozinha1.getNome());
//		System.out.printf("%d - %s\n", cozinha2.getId(), cozinha2.getNome());
//	}
//	
//}
