package com.algaworks.algafood.dto.conversor;

import java.util.List;
import java.util.stream.Collectors;

import com.algaworks.algafood.controller.CidadeController;
import com.algaworks.algafood.controller.CozinhaController;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.dto.CozinhaDTO;
import com.algaworks.algafood.entity.Cozinha;

@Component
public class CozinhaConversor extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO> {
	
	@Autowired
	private ModelMapper modelMapper;

	public CozinhaConversor() {
		super(CozinhaController.class, CozinhaDTO.class);
	}

	@Override
	public CozinhaDTO toModel(Cozinha cozinha) {
		var cozinhaDTO = createModelWithId(cozinha.getId(), cozinha);
		modelMapper.map(cozinha, cozinhaDTO);
		Link linkListar = WebMvcLinkBuilder.linkTo(CozinhaController.class).withRel("listar");
		cozinhaDTO.add(linkListar);
		return cozinhaDTO;
	}

	public List<CozinhaDTO> toCollectionModel(List<Cozinha> cozinhas) {
		return cozinhas.stream()
						.map(cozinha -> toModel(cozinha))
						.collect(Collectors.toList());
	}
	
	public Cozinha converterParaObjeto(CozinhaDTO cozinhaDTO) {
		return modelMapper.map(cozinhaDTO, Cozinha.class);
	}
	
	public void copiarParaObjeto(CozinhaDTO cozinhaDTO, Cozinha cozinha) {
		cozinhaDTO.setId(cozinha.getId());
		modelMapper.map(cozinhaDTO, cozinha);
	}

}
