package com.algaworks.algafood.mixim;

import com.algaworks.algafood.entity.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class CidadeMixim {
	
	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Estado estado;

}
