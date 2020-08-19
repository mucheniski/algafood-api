package com.algaworks.algafood.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.entity.Cidade;
import com.algaworks.algafood.exception.EntidadeNaoEncotradaException;
import com.algaworks.algafood.exception.NegocioException;
import com.algaworks.algafood.repository.CidadeRepository;
import com.algaworks.algafood.service.CidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CidadeService cidadeService;

	@GetMapping
	public List<Cidade> listar() {
		return cidadeRepository.findAll();
	}

	@GetMapping("/{cidadeId}")
	public Cidade buscarPorId(@PathVariable Long cidadeId) {
		return cidadeService.buscarPorId(cidadeId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody Cidade cidade) {
		try {
			return cidadeService.salvar(cidade);
		} catch (EntidadeNaoEncotradaException e) {

			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{cidadeId}")
	public Cidade atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidadeRecebida) {
		Cidade cidadeAtual = cidadeService.buscarPorId(cidadeId);
		BeanUtils.copyProperties(cidadeRecebida, cidadeAtual, "id"); // Do terceiro parâmetro em diante passamos o que
																		// queremos que seja ignorado

		try {
			return cidadeService.salvar(cidadeAtual);

		} catch (EntidadeNaoEncotradaException e) {

			throw new NegocioException(e.getMessage());
		}

	}

	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cidadeService.remover(cidadeId);
	}

}
