package com.algaworks.algafood.filtro;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


import java.time.OffsetDateTime;



@Getter
@Setter
public class VendaDiariaFiltro {

    private Long restauranteId;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private OffsetDateTime dataInicio;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private OffsetDateTime dataFim;

}
