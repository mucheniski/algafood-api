package com.algaworks.algafood.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.algafood.validations.Multiplo;
import com.algaworks.algafood.validations.ValorZeroIncluiDescricao;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ValorZeroIncluiDescricao(atributo = "taxaFrete", descricao = "nome", descricaoObrigatoria = "Frete Grátis")
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
//	@NotBlank
	@Column(nullable = false) 	// Constraint do JPA, para que a coluna no banco seja notnull 
	private String nome;
	
//  @DecimalMin("0") // Valor mínimo para a taxa frete
//	@NotNull
//	@PositiveOrZero
	@Multiplo(numero = 5)
	@Column(nullable = false)
	private BigDecimal taxaFrete;
	
//	@Valid // Faz a validação em cascata das propriedades da cozinha mapeadas aqui, como no caso o ID
//	@ConvertGroup(from = Default.class, to = Grupos.CozinhaId.class) // Converte a validação apenas para o cadastro de restaurante
//	@NotNull
	@ManyToOne // (fetch = FetchType.LAZY)
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	@Embedded // Essa propriedade é um tipo incorporado de uma classe embeddable
	private Endereco endereco;	
	
	private Boolean ativo = Boolean.TRUE;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;	// O OffsetDateTime possui o offset para ser apresentado no retorno da API ex "2020-11-06T06:51:52-03:00"
	
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;
	
	@OneToMany(mappedBy = "rastaurante")
	private List<Produto> produtos = new ArrayList<>();
		
	@ManyToMany
	@JoinTable(
			name = "restaurante_forma_pagamento",
			joinColumns = @JoinColumn(name = "rastaurante_id"),
			inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id")
	)
	private Set<FormaPagamento> formasPagamento = new HashSet();
	
	// TODO: refatorar para gravar a data da atualização
	public void ativar() {
		setAtivo(true);
	}
	
	// TODO: refatorar para gravar a data da atualização
	public void desativar() {
		setAtivo(false);
	}
	
	/*
	 * Retorna um boolean true se foi adicionado e false se não foi
	 * faz parta dos métodos de collections do java.
	 */
	public boolean vincularFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().add(formaPagamento);
	}
	
	/*
	 * Retorna um boolean true se foi adicionado e false se não foi
	 * faz parta dos métodos de collections do java.
	 */
	public boolean desvincularFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().remove(formaPagamento);
	}
		
}
