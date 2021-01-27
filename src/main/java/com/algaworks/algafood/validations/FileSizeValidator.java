package com.algaworks.algafood.validations;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

	/*
		Representa o tamanho de dados em Bytes.
	 */
	private DataSize tamanhoMaximo;
	
	@Override
	public void initialize(FileSize fileSize) {
		this.tamanhoMaximo = DataSize.parse(fileSize.max());
	}
	
	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
		return multipartFile == null || multipartFile.getSize() <= this.tamanhoMaximo.toBytes();
	}

}
