package com.algaworks.algafood.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class VendaDiariaDTO {

    private Date dataCriacao;
    private Long totalVendas;
    private BigDecimal totalFaturado;

}
