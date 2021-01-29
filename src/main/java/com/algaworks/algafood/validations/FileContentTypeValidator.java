package com.algaworks.algafood.validations;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<String> allowedTypes;

    @Override
    public void initialize(FileContentType fileContentType) {
        this.allowedTypes = Arrays.asList(fileContentType.allowedTypes());
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {

        /*
            Precisa validar se é null para evitar nullpointerexception e o erro ser tratado corretamente pela nossa tratativa
         */
        return multipartFile == null || this.allowedTypes.contains(multipartFile.getContentType());
    }
}
