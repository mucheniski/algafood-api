package com.algaworks.algafood.dto;

import com.algaworks.algafood.validations.FileContentType;
import com.algaworks.algafood.validations.FileSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class FotoProdutoPutDTO {

    @NotNull
    @FileSize(max = "500KB")
    @FileContentType(allowedTypes = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE } )
    private MultipartFile arquivo;

    @NotBlank
    private String descricao;

}
