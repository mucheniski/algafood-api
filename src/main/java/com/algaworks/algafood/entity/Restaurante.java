package com.algaworks.algafood.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.algafood.validations.Grupos;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@NotNull 					// Constraint do BeanValidation, para não deixar o JSON serializado ser enviado na requisição com null
//	@NotEmpty					// Não aceita vazio, mas aceita espaços em branco
	@NotBlank
	@Column(nullable = false) 	// Constraint do JPA, para que a coluna no banco seja notnull 
	private String nome;
	
	// @DecimalMin("0") // Valor mínimo para a taxa frete
	@PositiveOrZero(message = "{taxaFrete.invalida}")
	@Column(nullable = false)
	private BigDecimal taxaFrete;
	
//	@JsonIgnore
//	@JsonIgnoreProperties("hibernateLazyInitializer")
	@Valid // Faz a validação em cascata das propriedades da cozinha mapeadas aqui, como no caso o ID
	@ConvertGroup(from = Default.class, to = Grupos.CozinhaId.class) // Converte a validação apenas para o cadastro de restaurante
	@NotNull
	@ManyToOne // (fetch = FetchType.LAZY)
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	@JsonIgnore
	@Embedded // Essa propriedade é um tipo incorporado de uma classe embeddable
	private Endereco endereco;	
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataCadastro;	
	
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataAtualizacao;
	
	@JsonIgnore
	@OneToMany(mappedBy = "rastaurante")
	private List<Produto> produtos = new ArrayList<>();
	
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(
			name = "restaurante_forma_pagamento",
			joinColumns = @JoinColumn(name = "rastaurante_id"),
			inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id")
	)
	private List<FormaPagamento> formasPagamento = new ArrayList<>();
		
}
