package com.algaworks.algafood.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.algaworks.algafood.enuns.StatusPedido;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {
	
	private Long id;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private OffsetDateTime dataCriacao;
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataCancelamento;
	private OffsetDateTime dataEntrega;
	private UsuarioRetornoDTO usuarioCliente;
	private EnderecoDTO endereco;
	private RestauranteResumoDTO restaurante;
	private FormaPagamentoDTO formaPagamento;
	private StatusPedido status;
	private List<ItemPedidoDTO> itens = new ArrayList<>();

}
