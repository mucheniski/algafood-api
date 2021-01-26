package com.algaworks.algafood.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class FotoProdutoDTO {

    private MultipartFile arquivo;
    private String descricao;

}
