package com.algaworks.algafood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.algaworks.algafood.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class) // Já ativo por padrão mas para customizar com a nossa classe é preciso adicionar esse item
public class AlgafoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgafoodApiApplication.class, args);
	}

}
