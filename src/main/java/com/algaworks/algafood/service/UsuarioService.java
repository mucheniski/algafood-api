package com.algaworks.algafood.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.dto.GrupoDTO;
import com.algaworks.algafood.dto.UsuarioAtualizaSenhaDTO;
import com.algaworks.algafood.dto.UsuarioEntradaDTO;
import com.algaworks.algafood.dto.UsuarioEntradaSemSenhaDTO;
import com.algaworks.algafood.dto.UsuarioRetornoDTO;
import com.algaworks.algafood.dto.conversor.UsuarioConversor;
import com.algaworks.algafood.entity.Grupo;
import com.algaworks.algafood.entity.Usuario;
import com.algaworks.algafood.exception.NegocioException;
import com.algaworks.algafood.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.exception.UsuarioSenhaAtualizacaoException;
import com.algaworks.algafood.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private UsuarioConversor conversor;
	
	@Autowired
	private GrupoService grupoService;
	
	public List<UsuarioRetornoDTO> listar() {
		return conversor.converterListaParaDTO(repository.findAll()); 
	}
	
	public Usuario buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}

	public UsuarioRetornoDTO buscarDtoPorId(Long id) {
		return conversor.converterParaDTO(buscarPorId(id));
	}

	@Transactional
	public UsuarioRetornoDTO salvar(UsuarioEntradaDTO dto) {
		
		// TODO: criptografar a senha para salvar
		Usuario usuario = conversor.converterParaObjeto(dto);
		return conversor.converterParaDTO(salvarNaBase(usuario));
	}

	@Transactional
	public UsuarioRetornoDTO atualizar(Long id, UsuarioEntradaSemSenhaDTO dto) {		
		Usuario usuario = buscarPorId(id);
		conversor.copiarParaObjeto(dto, usuario);		
		return conversor.converterParaDTO(salvarNaBase(usuario));
	}	

	@Transactional
	public UsuarioRetornoDTO atualizaSenha(Long id, @Valid UsuarioAtualizaSenhaDTO dto) {
		
		Usuario usuario = buscarPorId(id);
		
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
	
	private Usuario salvarNaBase(Usuario usuario) {
		
		/*
		 *	Faz com que o JPA pare de gerenciar a entidade usuário que está no contexto de persistência
		 *  assim não será sincronizada na base até que o repository.save seja chamado 
		 */
		repository.detach(usuario);
		
		Optional <Usuario> usuarioExistente = repository.findByEmail(usuario.getEmail());
		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
		}
		return repository.save(usuario);
		
	}

	public List<GrupoDTO> listarGruposPorUsuario(Long usuarioId) {
		Usuario usuario = buscarPorId(usuarioId);
		List<GrupoDTO> grupos = new ArrayList<>();
		
		if (!usuario.getGrupos().isEmpty()) {
			grupos = grupoService.converterListaParaDTO(usuario.getGrupos());
		}
		
		return grupos;
	}

	// TODO: não está com idempotencia, verificar
	@Transactional
	public void vincularGrupoAoUsuario(Long usuarioId, Long grupoId) {
		Usuario usuario = buscarPorId(usuarioId);
		Grupo grupo = grupoService.buscarPorId(grupoId);
		usuario.vincularGrupo(grupo);
	}
	
	@Transactional
	public void desvincularGrupoAoUsuario(Long usuarioId, Long grupoId) {
		Usuario usuario = buscarPorId(usuarioId);
		Grupo grupo = grupoService.buscarPorId(grupoId);
		usuario.desvincularGrupo(grupo);
	}
}
