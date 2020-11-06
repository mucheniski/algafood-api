package com.algaworks.algafood.mixim;

import java.util.ArrayList;
import java.util.List;

import com.algaworks.algafood.entity.Cozinha;
import com.algaworks.algafood.entity.Endereco;
import com.algaworks.algafood.entity.FormaPagamento;
import com.algaworks.algafood.entity.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/* Mixim é uma classe que possui propriedades de uma outra classe original onde podem ser colocadas anotações 
 * que queremos deixar separadas da classe original para que fique mais coeso, como por exemplo anotações 
 * Jackson que são referêntes à API em classes de domínio, o ideal é colocar as anotações Jackson em uma classe mixim. 
 * (RestauranteMixim) */
public class RestauranteMixim {
	
	@JsonIgnoreProperties(value = "nome", allowGetters = true) // Ignorar apenas o nome da cozinha na hora de desserializar o JSON do restaurante, na hora de serializar não, permite os getters
	private Cozinha cozinha;
	
	@JsonIgnore
	private Endereco endereco;	
	
	// @JsonIgnore
	// private OffsetDateTime dataCadastro;	
	
	// @JsonIgnore
	// private OffsetDateTime dataAtualizacao;
	
	@JsonIgnore
	private List<Produto> produtos = new ArrayList<>();	
	
	@JsonIgnore
	private List<FormaPagamento> formasPagamento = new ArrayList<>();

}
