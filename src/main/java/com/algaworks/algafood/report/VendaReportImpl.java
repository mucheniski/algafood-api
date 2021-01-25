package com.algaworks.algafood.report;

import com.algaworks.algafood.exception.ReportException;
import com.algaworks.algafood.filtro.VendaDiariaFiltro;
import com.algaworks.algafood.service.VendaConsultasService;
import lombok.var;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;

@Service
public class VendaReportImpl implements VendaReport {

    @Autowired
    private VendaConsultasService vendaConsultasService;

    @Override
    public byte[] emitirReportVendasDiarias(VendaDiariaFiltro vendaDiariaFiltro, String timeOffset) {

        /*
            this.getClass significa que eu estou buscando algo dentro do meu classpath
            dentro do meu projeto.

            JasperFillManager.fillReport
            inputStream é o relatório compilado, no caso o arquivo .jasper
            parameters são os parametros necessários para o report
            datasource é a fonte de onde vêm os dados para preencher o report
         */
        try {
            var inputStream = this.getClass().getResourceAsStream("/algafood-reports/vendas-diarias.jasper");

            var parameters = new HashMap<String, Object>();
            parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));

            var vendasDiarias = vendaConsultasService.consultarVendasDiarias(vendaDiariaFiltro, timeOffset);
            var dataSource = new JRBeanCollectionDataSource(vendasDiarias);

            var jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException("Não foi possível emitir relatório de vendas diárias", e);
        }

    }
}
