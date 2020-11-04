package com.algaworks.algafood.mixim;

import java.util.ArrayList;
import java.util.List;

import com.algaworks.algafood.entity.Permissao;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class GrupoMixim {

	@JsonIgnore
	private List<Permissao> permissoes = new ArrayList<>();
	
}
