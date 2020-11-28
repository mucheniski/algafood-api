package com.algaworks.algafood.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algafood.entity.Restaurante;

/*
 * TODO: Depois de terminar o curso, criar esse conversor genérico e refatorar todos os conversores
 * de DTO para usar o genérico.
 * */
public class ConversorDTO {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Object converterParaDTO(Object classeRecebida) {	
		return modelMapper.map(classeRecebida, verificaClasseRecebida(classeRecebida).getClass());
	}	
		
	private Object verificaClasseRecebida(Object classeRecebida) {
		
		Object classeRetornada = null;
		
		if (classeRecebida instanceof Restaurante) {
			classeRetornada = RestauranteRetornoDTO.class;
		}
		
		return classeRetornada;
	}

}
