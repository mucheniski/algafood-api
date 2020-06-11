package com.algaworks.algafood.entity;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Embeddable  // Tem a capacidade de ser incorporada em uma entidade
public class Endereco {
	
	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cidade_id")
	private Cidade cidade;

}
