package com.algaworks.algafood.documentation;

import com.algaworks.algafood.dto.CozinhaDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("PageCozinhasOpenAPI")
public class PageCozinhasOpenAPI extends PageGenericoOperAPI<CozinhaDTO> {

}
