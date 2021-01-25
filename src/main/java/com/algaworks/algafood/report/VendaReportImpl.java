package com.algaworks.algafood.report;

import com.algaworks.algafood.filtro.VendaDiariaFiltro;
import org.springframework.stereotype.Service;

@Service
public class VendaReportImpl implements VendaReport {

    @Override
    public byte[] emitirReportVendasDiarias(VendaDiariaFiltro vendaDiariaFiltro, String timeOffset) {
        return new byte[0];
    }
}
