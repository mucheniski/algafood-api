package com.algaworks.algafood.controller;

import com.algaworks.algafood.dto.VendaDiariaDTO;
import com.algaworks.algafood.filtro.VendaDiariaFiltro;
import com.algaworks.algafood.service.VendaConsultasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/estatisticas")
public class EstatisticasVendaController {

    @Autowired
    private VendaConsultasService vendaConsultasService;

    @GetMapping("/vendas-diarias")
    public List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFiltro vendaDiariaFiltro) {
        return vendaConsultasService.consultarVendasDiarias(vendaDiariaFiltro);
    }

}
