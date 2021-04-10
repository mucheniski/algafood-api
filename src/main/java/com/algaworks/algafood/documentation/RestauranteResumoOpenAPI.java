package com.algaworks.algafood.documentation;

import com.algaworks.algafood.dto.CozinhaDTO;
import com.algaworks.algafood.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@ApiModel("RestauranteResumoOpenAPI")
@Getter
@Setter
public class RestauranteResumoOpenAPI {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Restaurante Japones")
    private String nome;

    @ApiModelProperty(example = "10.00")
    private BigDecimal taxaFrete;

    private CozinhaDTO cozinha;

}
