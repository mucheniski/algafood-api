package com.algaworks.algafood.dto.conversor;

import com.algaworks.algafood.controller.CidadeController;
import com.algaworks.algafood.controller.EstadoController;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.dto.CidadeDTO;
import com.algaworks.algafood.entity.Cidade;


// TODO: verificar se mudo o nome para CidadeAssembler
@Component
public class CidadeConversor extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private EstadoConversor estadoConversor;

	public CidadeConversor() {
		super(CidadeController.class, CidadeDTO.class);
	}

	/*
	* O Método precisou ser renomeado para sobrescrever o toModel herdado de RepresentationModelAssemblerSupport
	* o converterListaParaDTO foi removido porque a classe herdada já tem também um método chamado toCollectionMadel
	* que faz a mesma coisa
	* */
	@Override
	public CidadeDTO toModel(Cidade cidade) {
		var cidadeDTO = modelMapper.map(cidade, CidadeDTO.class);

		/*
		 * o methodOn cria uma proxy do controller e já mapeia automáticamente o método sendo usado, é útil para que caso seja alterado algum
		 * mapeamento no controller o novo link já é alterado automaticamente, não precisamos ficar criando com slash
		 * Criando um link dinamicamente para o CidadeController/cidadeDTO.id ex .../cidades/1
		 * é possível porque o CidadeDTO está estendendo RepresentationModel
		 * */
		Link linkBuscarPorId = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).buscarPorId(cidadeDTO.getId())).withSelfRel();
		Link linkListar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).listar()).withRel("cidades");
		Link linkEstado = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class).buscarPorId(cidadeDTO.getEstado().getId())).withSelfRel();

		cidadeDTO.add(linkBuscarPorId);
		cidadeDTO.add(linkListar);
		cidadeDTO.getEstado().add(linkEstado);

		return cidadeDTO;
	}

	/*
	* Reescrito apenas para adicionar o link do list no final
	* */
	@Override
	public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> cidades) {
		return super.toCollectionModel(cidades).add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());
	}

	public Cidade converterParaObjeto(CidadeDTO cidadeDTO) {
		return modelMapper.map(cidadeDTO, Cidade.class);
	}

	public void copiarParaObjeto(CidadeDTO cidadeDTO, Cidade cidade) {
		cidadeDTO.setId(cidade.getId());
		cidadeDTO.setEstado(estadoConversor.converterParaDTO(cidade.getEstado()));
		modelMapper.map(cidadeDTO, cidade);
	}

}
