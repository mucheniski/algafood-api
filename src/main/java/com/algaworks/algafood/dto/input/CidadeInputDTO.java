package com.algaworks.algafood.dto.input;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(description = "DTO para Post de uma cidade")
@Getter
@Setter
public class CidadeInputDTO {

    @NotBlank
    private String nome;

    @NotNull
    private Long estadoId;

}
