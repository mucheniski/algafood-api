package com.algaworks.algafood.controller;

import com.algaworks.algafood.dto.VendaDiariaDTO;
import com.algaworks.algafood.filtro.VendaDiariaFiltro;
import com.algaworks.algafood.report.VendaReport;
import com.algaworks.algafood.service.VendaConsultasService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
@RequestMapping(path = "/estatisticas")
public class EstatisticasVendaController {

    @Autowired
    private VendaConsultasService vendaConsultasService;

    @Autowired
    private VendaReport vendaReport;

    // Não precisa marcar com @PathVariable, o spring entende quando é passado um parâmetro
    @GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFiltro vendaDiariaFiltro, @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
        return vendaConsultasService.consultarVendasDiarias(vendaDiariaFiltro, timeOffSet);
    }

    /*
        Quando o consumidor da API especificar no Accept que quer um PDF, automaticamente cai
        nesse método por causa do MediaType especificado.
     */
    @GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultarVendasDiariasPDF(VendaDiariaFiltro vendaDiariaFiltro, @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {

        byte[] bytesPdf = vendaReport.emitirReportVendasDiarias(vendaDiariaFiltro, timeOffSet);

        var customHeaders = new HttpHeaders();

        /*
            attachment indica que o conteúdo de retorno deve ser baixado pelo consumidor da API
            e não exibido inline, ou seja dentro no navegador, vai ser apenas para download.
         */
        customHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");

        return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .headers(customHeaders)
                    .body(bytesPdf);
    }

}
