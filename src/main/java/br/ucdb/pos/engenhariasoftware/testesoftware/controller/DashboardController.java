package br.ucdb.pos.engenhariasoftware.testesoftware.controller;

import br.ucdb.pos.engenhariasoftware.testesoftware.controller.vo.TotalLancamentoCategoriaVO;
import br.ucdb.pos.engenhariasoftware.testesoftware.controller.vo.TotalLancamentoVO;
import br.ucdb.pos.engenhariasoftware.testesoftware.converter.DateToStringConverter;
import br.ucdb.pos.engenhariasoftware.testesoftware.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
public class DashboardController {

    @Autowired
    private LancamentoService lancamentoService;


    @RequestMapping("/dashboard")
    public ModelAndView relatorio(Date dataInicial, Date dataFinal){
        if(dataInicial == null) {
            dataInicial = Date.from(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        }
        if(dataFinal == null) {
            dataFinal = Date.from(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        }

        DateToStringConverter converter = new DateToStringConverter();
        StringBuilder titulo = new StringBuilder();
        titulo.append("Entrada vs Saída ( ");
        titulo.append(converter.convert(dataInicial) + " - " + converter.convert(dataFinal) + " )");
        ModelAndView mv = new ModelAndView("dashboard");
        mv.addObject("dataChart", getChartData(dataInicial, dataFinal));
        mv.addObject("dataTable", getTableData(dataInicial, dataFinal));
        mv.addObject("tituloChart", titulo.toString());
        return mv;
    }

    protected String getTableData(Date dataInicial, Date dataFinal){
        List<TotalLancamentoCategoriaVO> totaisCategoria =  lancamentoService.getTotalPorPeriodoPorCategoria(dataInicial, dataFinal);
        final StringBuilder builder = new StringBuilder("[");

        NumberFormat format = new DecimalFormat("'R$ ' #,###,##0.00");
        totaisCategoria.stream().forEach(t -> {
            String categoria = "Sem Categoria";
            BigDecimal valor = t.total.setScale(2, RoundingMode.HALF_UP);
            if(t.categoria != null){
                categoria = t.categoria.getNome();
            }
            builder.append("['" + categoria + "',{v:" + (valor) + ", f:'" + format.format(valor) +"'}],");
        });

        builder.deleteCharAt(builder.lastIndexOf(","));
        builder.append("]");

        return builder.toString();
    }

    protected String getChartData(Date dataInicial, Date dataFinal){

        List<TotalLancamentoVO> totais =  lancamentoService.getTotalPorPeriodo(dataInicial, dataFinal);

        final StringBuilder builder = new StringBuilder();
        builder.append("[['Lançamentos','Entrada vs Saída'],");
        totais.stream().forEach(t -> {
            builder.append("['" + t.tipo.getTipo() + "'," + t.total.setScale(2, RoundingMode.HALF_UP) + "],");
        });

        builder.deleteCharAt(builder.lastIndexOf(","));
        builder.append("]");

        return builder.toString();
    }
}
