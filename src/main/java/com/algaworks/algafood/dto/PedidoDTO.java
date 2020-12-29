package com.algaworks.algafood.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.algaworks.algafood.enuns.StatusPedido;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {
	
	private String codigo;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private OffsetDateTime dataCriacao;
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataCancelamento;
	private OffsetDateTime dataEntrega;
	private UsuarioRetornoDTO usuarioCliente;
	private EnderecoPedidoDTO enderecoEntrega;
	private RestauranteResumoDTO restaurante;
	private FormaPagamentoDTO formaPagamento;
	private StatusPedido status;
	
	@NotNull
	@Size(min = 1)
	private List<ItemPedidoDTO> itens = new ArrayList<>();

}
