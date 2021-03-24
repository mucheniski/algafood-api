package com.algaworks.algafood.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.dto.FormaPagamentoDTO;
import com.algaworks.algafood.service.FormaPagamentoService;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {
	
	@Autowired
	private FormaPagamentoService service;
	
	@GetMapping
	public ResponseEntity<List<FormaPagamentoDTO>> listar(ServletWebRequest request) {

		/*
			Como o Shallow Etag está habilitado, para implementar uma chamada com Deep Etag é preciso desabilitar na chamada.
		 */
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

		String deepETag = service.getDeepEtag();

		// Se o if none match for passado aqui pelo header, não precisa executar o resto, já da pra saber que nada foi alterado.
		if (request.checkNotModified(deepETag)) {
			return null;
		}

		List<FormaPagamentoDTO> formasPagamento = service.listar();

		return ResponseEntity.ok()
				// Definindo a quantidade de tempo de chace para o navegador que vai receber o retorno
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.eTag(deepETag)
				.body(formasPagamento);

	}
	
	@GetMapping("/{id}")
	public ResponseEntity<FormaPagamentoDTO> buscarDtoPorId(@PathVariable Long id) {
		FormaPagamentoDTO formaPagamentoDTO = service.buscarDtoPorId(id);

		/*
			cachePrivate - não pode ser armazenado cache e server de cache compartilhado, apenas localmente
			cachePublic - a resposta pode ser armazenada localmente e em server publico, o default já é esse.
			noCache - sempre vai existir uma validação quando o cache for feita.
			noStore - Não pode ser armazendo o response em nenhum tipo de cache
		 */
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
				.body(formaPagamentoDTO);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoDTO dto) {
		return service.salvar(dto);
	}
	
	@PutMapping("/{id}")
	public FormaPagamentoDTO atualizar(@PathVariable Long id, @RequestBody @Valid FormaPagamentoDTO dto) {
		return service.atualizar(id, dto);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
		service.remover(id);
	}

}
