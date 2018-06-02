package br.ucdb.pos.engenhariasoftware.testesoftware.controller;

import br.ucdb.pos.engenhariasoftware.testesoftware.controller.vo.TotalLancamentoVO;
import br.ucdb.pos.engenhariasoftware.testesoftware.converter.DateToStringConverter;
import br.ucdb.pos.engenhariasoftware.testesoftware.service.LancamentoService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.util.Date;
import java.util.List;

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
        mv.addObject("data", getData(dataInicial, dataFinal));
        mv.addObject("titulo", titulo.toString());
        return mv;
    }

    protected String getData(Date dataInicial, Date dataFinal){

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
