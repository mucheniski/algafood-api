package com.algaworks.algafood.mixim;

import java.util.ArrayList;
import java.util.List;

import com.algaworks.algafood.entity.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CozinhaMixim {
	
	@JsonIgnore
	private List<Restaurante> restaurantes = new ArrayList<>();

}
