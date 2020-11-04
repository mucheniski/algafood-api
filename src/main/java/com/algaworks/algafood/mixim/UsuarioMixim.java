package com.algaworks.algafood.mixim;

import java.util.ArrayList;
import java.util.List;

import com.algaworks.algafood.entity.Grupo;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UsuarioMixim {
	
	@JsonIgnore
	private List<Grupo> grupos = new ArrayList<>();

}
