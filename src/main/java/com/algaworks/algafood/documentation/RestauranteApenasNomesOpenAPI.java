package com.algaworks.algafood.documentation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("RestauranteApenasNomesOpenAPI")
@Getter
@Setter
public class RestauranteApenasNomesOpenAPI {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Restaurante Japones")
    private String nome;

}
