package com.algaworks.algafood.report;

import com.algaworks.algafood.filtro.VendaDiariaFiltro;

public interface VendaReport {

    byte[] emitirReportVendasDiarias(VendaDiariaFiltro filtro, String timeOffset);

}
