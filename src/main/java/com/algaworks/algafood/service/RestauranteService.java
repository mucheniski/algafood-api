package com.algaworks.algafood.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.dto.conversor.RestauranteConversor;
import com.algaworks.algafood.dto.entrada.RestauranteEntradaDTO;
import com.algaworks.algafood.dto.retorno.RestauranteRetornoDTO;
import com.algaworks.algafood.entity.Cidade;
import com.algaworks.algafood.entity.Cozinha;
import com.algaworks.algafood.entity.FormaPagamento;
import com.algaworks.algafood.entity.Restaurante;
import com.algaworks.algafood.exception.CidadeNaoEncotradaException;
import com.algaworks.algafood.exception.CozinhaNaoEncotradaException;
import com.algaworks.algafood.exception.EntidadeEmUsoException;
import com.algaworks.algafood.exception.EntidadeNaoEncotradaException;
import com.algaworks.algafood.exception.NegocioException;
import com.algaworks.algafood.exception.RestauranteNaoEncotradaException;
import com.algaworks.algafood.repository.CidadeRepository;
import com.algaworks.algafood.repository.CozinhaRepository;
import com.algaworks.algafood.repository.RestauranteRepository;

@Service
public class RestauranteService {
	
	private static final String MSG_RESTAURANTE_EM_USO = "	Restaurante id %d não pode ser removida, está em uso!";
	@Autowired
	private RestauranteRepository repository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private RestauranteConversor conversor;
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	public List<RestauranteRetornoDTO> listar() {
		return conversor.converterListaParaDTO(repository.findAllCustom());
	}
	
	public RestauranteRetornoDTO buscarDtoPorId(Long id) {
		Restaurante restaurante = buscarPorId(id);
		return conversor.converterParaDTO(restaurante);
	}
	
	public Restaurante buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new RestauranteNaoEncotradaException(id) );
	}
	
	public List<RestauranteRetornoDTO> listarPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return conversor.converterListaParaDTO(repository.findByTaxaFreteBetween(taxaInicial, taxaFinal));
	}	
	
	public List<RestauranteRetornoDTO> listarPorNomeTaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return conversor.converterListaParaDTO(repository.findByNomeTaxaFrete(nome, taxaInicial, taxaFinal));
	}
	
	public List<RestauranteRetornoDTO> comFreteGratis(String nome) {	
		return conversor.converterListaParaDTO(repository.findComFreteGratis(nome));
	}
	
	public List<RestauranteRetornoDTO> listarPorNomeECozinha(String nome, Long cozinhaId) {
		return conversor.converterListaParaDTO(repository.consultarPorNome(nome, cozinhaId));
	}
	
	public RestauranteRetornoDTO buscarPrimeiro() {
		return conversor.converterParaDTO(repository.buscarPrimeiro().get());
	}
	
	@Transactional
	public RestauranteRetornoDTO salvar(RestauranteEntradaDTO dto) {
		try {
			Restaurante restaurante = conversor.converterParaObjeto(dto);	
			
			Long cozinhaId = dto.getCozinha().getId();
			Long cidadeId = dto.getEndereco().getCidade().getId();
			
			Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new CozinhaNaoEncotradaException(cozinhaId));
			Cidade cidade = cidadeRepository.findById(cidadeId).orElseThrow(() -> new CidadeNaoEncotradaException(cidadeId));
			
			restaurante.setCozinha(cozinha);
			restaurante.getEndereco().setCidade(cidade);
			
			return conversor.converterParaDTO(repository.save(restaurante));
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@Transactional
	public RestauranteRetornoDTO atualizar(Long id, RestauranteEntradaDTO dto) {
		try {
			Restaurante restauranteAtual = repository.findById(id).orElseThrow(() -> new RestauranteNaoEncotradaException(id));
			
			if (dto.getCozinha() != null) {
				Long cozinhaId = dto.getCozinha().getId();
				Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new CozinhaNaoEncotradaException(cozinhaId));
				restauranteAtual.setCozinha(cozinha);
			}	
			
			if (dto.getEndereco() != null) {
				Long cidadeId = dto.getEndereco().getCidade().getId();
				Cidade cidade = cidadeRepository.findById(cidadeId).orElseThrow(() -> new CidadeNaoEncotradaException(cidadeId));
				restauranteAtual.getEndereco().setCidade(cidade);
			}
			
			conversor.copiarParaObjeto(dto, restauranteAtual);
			return conversor.converterParaDTO(repository.save(restauranteAtual));		
		} catch (EntidadeNaoEncotradaException e) {
			throw new NegocioException(e.getMessage());
		}		
	}
	
	@Transactional
	public void remover(Long id) {
		try {
			repository.deleteById(id);
			repository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncotradaException(id);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, id));
		
		}
	}
	
	@Transactional
	public void ativar(Long id) {
		Restaurante restaurante = repository.findById(id)
				.orElseThrow(() -> new RestauranteNaoEncotradaException(id) );
		
		/*
		 * Não é preciso fazer um save porque à partir do momento que eu busco um registro no banco pelo repository
		 * o contexto de persistência do JPA já gerencia o que foi retornado, assim qualquer alteração que for feita
		 * após esse retorno o próprio spring data JPA jà sincroniza com a base e salva automáticamente.
		 */
		restaurante.ativar();
	}
	
	@Transactional
	public void desativar(Long id) {
		Restaurante restaurante = repository.findById(id)
				.orElseThrow(() -> new RestauranteNaoEncotradaException(id) );
		
		/*
		 * Não é preciso fazer um save porque à partir do momento que eu busco um registro no banco pelo repository
		 * o contexto de persistência do JPA já gerencia o que foi retornado, assim qualquer alteração que for feita
		 * após esse retorno o próprio spring data JPA jà sincroniza com a base e salva automáticamente.
		 */
		restaurante.desativar();
	}
	
	@Transactional
	public void desvincularFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarPorId(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.buscarPorId(formaPagamentoId);
		
		/*
		 * Não precisa chamar o save por causa do contesto de transação, como está anotado com @Transactional
		 * sempre que houver alteração na entidade Restaurante as alterações serão persistidas na base pelo
		 * gerenciador do Spring Data JPA quando for sincronizar as informações.
		 */
		restaurante.desvincularFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void vincularFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarPorId(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.buscarPorId(formaPagamentoId);
		
		/*
		 * Não precisa chamar o save por causa do contesto de transação, como está anotado com @Transactional
		 * sempre que houver alteração na entidade Restaurante as alterações serão persistidas na base pelo
		 * gerenciador do Spring Data JPA quando for sincronizar as informações.
		 */
		restaurante.vincularFormaPagamento(formaPagamento);
	}
	
}
