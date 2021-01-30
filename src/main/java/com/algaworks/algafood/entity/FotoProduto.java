package com.algaworks.algafood.entity;

import com.algaworks.algafood.validations.Grupos;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FotoProduto {

	@EqualsAndHashCode.Include
	@Id
	@Column(name = "produto_id")
	private Long produtoId;

	/*
		@MapsId é porque o id da Entidade FotoProduto é o mesmo id do produto
		por se tratar de uma mapeamento um pra um, então quando for feito um
		get o JPA vai buscar por esse ID.

		O Lazy no OneToOne é para evitar selects desnecessários.
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private Produto produto;

	private String nomeArquivo;
	private String descricao;
	private String contentType;
	private Long tamanho;
	
}
