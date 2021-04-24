package com.algaworks.algafood.dto;

import com.algaworks.algafood.entity.Estado;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;

// TODO: refatorar dotos os DTO para Model e o conversor para Assembler
@ApiModel(description = "Representa uma DTO de Estado")
@Getter
@Setter
public class EstadoDTO extends RepresentationModel<EstadoDTO> {

	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long id;

	@ApiModelProperty(example = "Paraná")
	private String nome;
	
}
