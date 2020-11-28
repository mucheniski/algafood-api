package com.algaworks.algafood.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.dto.conversor.UsuarioConversor;
import com.algaworks.algafood.dto.entrada.UsuarioAtualizaSenhaDTO;
import com.algaworks.algafood.dto.entrada.UsuarioEntradaDTO;
import com.algaworks.algafood.dto.entrada.UsuarioEntradaSemSenhaDTO;
import com.algaworks.algafood.dto.retorno.UsuarioRetornoDTO;
import com.algaworks.algafood.entity.Usuario;
import com.algaworks.algafood.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.exception.UsuarioSenhaAtualizacaoException;
import com.algaworks.algafood.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private UsuarioConversor conversor;
	
	public List<UsuarioRetornoDTO> listar() {
		return conversor.converterListaParaDTO(repository.findAll()); 
	}

	public UsuarioRetornoDTO buscarPorId(Long id) {
		return conversor.converterParaDTO(buscar(id));
	}

	@Transactional
	public UsuarioRetornoDTO salvar(UsuarioEntradaDTO dto) {
		// TODO: criptografar a senha para salvar
		Usuario usuario = conversor.converterParaObjeto(dto);
		return conversor.converterParaDTO(repository.save(usuario));
	}

	@Transactional
	public UsuarioRetornoDTO atualizar(Long id, UsuarioEntradaSemSenhaDTO dto) {		
		Usuario usuario = buscar(id);
		conversor.copiarParaObjeto(dto, usuario);		
		return conversor.converterParaDTO(repository.save(usuario));
	}	

	@Transactional
	public UsuarioRetornoDTO atualizaSenha(Long id, @Valid UsuarioAtualizaSenhaDTO dto) {
		
		Usuario usuario = buscar(id);
		
		if (!usuario.getSenha().equals(dto.getSenhaAtual())) {
			throw new UsuarioSenhaAtualizacaoException("A senha atual digitada não confere, por favor verifique");
		}
		
		if (usuario.getSenha().equals(dto.getSenhaNova())) {
			throw new UsuarioSenhaAtualizacaoException("A senha nova é igual a senha anterior, por favor informe uma senha diferente");
		}
		
		// TODO: criptografar a senha para salvar
		usuario.setSenha(dto.getSenhaNova());
		
		return conversor.converterParaDTO(repository.save(usuario));
	}
	
	private Usuario buscar(Long id) {
		return repository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}
}
