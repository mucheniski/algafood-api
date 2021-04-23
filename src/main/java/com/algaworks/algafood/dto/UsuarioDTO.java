package com.algaworks.algafood.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@ApiModel("Representa um usuário")
@Getter
@Setter
public class UsuarioDTO extends RepresentationModel<UsuarioDTO> {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Diego Mucheniski")
	private String nome;

	@ApiModelProperty(example = "diego@teste.com")
	private String email;


}
