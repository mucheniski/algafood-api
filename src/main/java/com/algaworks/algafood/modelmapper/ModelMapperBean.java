package com.algaworks.algafood.modelmapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.dto.EnderecoDTO;
import com.algaworks.algafood.entity.Endereco;

/*
 * Como o ModelMapper não é uma propriedade do Spring, é preciso criar essa classe
 * para que seja feita uma instância de ModelMapper como um bean do Spring assim
 * que o projeto iniciar.
 * */
@Configuration
public class ModelMapperBean {

	@Bean
	public ModelMapper modelMapper() {
		
		ModelMapper modelMapper = new ModelMapper();
		
		/*
		 * Método para que o nome do estado possa ser mapeado em EnderecoDTO corretamente.
		 * Como o getNome do estado não está na Entidade Endereco pois esta aninhado em endereco.cidade.estado
		 * é preciso fazer esse mapeamento para que o modelMapper busque as informações. 
		 */
		TypeMap<Endereco, EnderecoDTO> mapeiaEnderecoParaCidadeResumoDTO = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		
		/*
		 * Aqui são passadas expressões lambda para o .addMapping(sourceGetter, destinationSetter)
		 * 
		 * sourceGetter é uma interface funcional que recebe um parâmetro, nesse caso vai buscar o valor que é o nome do estado.
		 * 
		 * destinationSetter é uma interface funcional que recebe dois parâmetros (destino, valor)
		 * no caso o destino é o enderecoDTO, o valor a ser atribuido é o nomeEstado que vai receber o valor nome retornado no sourceGetter.
		 */
		mapeiaEnderecoParaCidadeResumoDTO
			.<String>addMapping(endereco -> endereco.getCidade().getEstado().getNome(), // sourceGetter passa o valor para o destino
					    	   (enderecoDTO, nomeEstado) -> enderecoDTO.setNomeEstado(nomeEstado)); // destinationSetter(destino, valor)
		
		return modelMapper;
	}
	
}
