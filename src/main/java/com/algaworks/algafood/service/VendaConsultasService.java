package com.algaworks.algafood.service;

import com.algaworks.algafood.dto.VendaDiariaDTO;
import com.algaworks.algafood.filtro.VendaDiariaFiltro;

import java.util.List;

public interface VendaConsultasService {

    List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFiltro vendaDiariaFiltro);

}
