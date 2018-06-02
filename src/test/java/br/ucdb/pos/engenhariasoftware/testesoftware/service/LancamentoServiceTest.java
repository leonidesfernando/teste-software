package br.ucdb.pos.engenhariasoftware.testesoftware.service;

import br.ucdb.pos.engenhariasoftware.testesoftware.controller.vo.ResultadoVO;
import br.ucdb.pos.engenhariasoftware.testesoftware.modelo.Lancamento;
import br.ucdb.pos.engenhariasoftware.testesoftware.modelo.TipoLancamento;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static br.ucdb.pos.engenhariasoftware.testesoftware.modelo.TipoLancamento.ENTRADA;
import static br.ucdb.pos.engenhariasoftware.testesoftware.modelo.TipoLancamento.SAIDA;
import static br.ucdb.pos.engenhariasoftware.testesoftware.util.Constantes.DD_MM_YYYY;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class LancamentoServiceTest {

    @Mock
    private LancamentoService lancamentoService;

    @BeforeClass
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider(name = "lancamentos")
    protected Object[][] getLancamentos(){
        return new Object[][]{
                new Object[]{Arrays.asList(new LancamentoBuilder()
                                .comValor(12).comTipo(SAIDA).build(),
                        new LancamentoBuilder()
                                .comValor(104.14).comTipo(SAIDA).build(),
                        new LancamentoBuilder()
                                .comValor(833.44).comTipo(ENTRADA).build(),
                        new LancamentoBuilder()
                                .comValor(439.87).comTipo(ENTRADA).build(),
                        new LancamentoBuilder()
                                .comValor(1434).comTipo(ENTRADA).build(),
                        new LancamentoBuilder()
                                .comValor(176).comTipo(ENTRADA).build(),
                        new LancamentoBuilder()
                                .comValor(194).comTipo(SAIDA).build(),
                        new LancamentoBuilder()
                                .comValor(347.98).comTipo(SAIDA).build(),
                        new LancamentoBuilder()
                                .comValor(98.87).comTipo(ENTRADA).build()
                )}
        };
    }

    @Test(dataProvider = "lancamentos")
    public void getTotalSaidaTest(List<Lancamento> lancamentos){
        given(lancamentoService.getTotalSaida(lancamentos)).willCallRealMethod();
        given(lancamentoService.somaValoresPorTipo(lancamentos, SAIDA)).willCallRealMethod();

        final BigDecimal totalEsperado = BigDecimal.valueOf(658.12);
        final BigDecimal totalObtido = lancamentoService.getTotalSaida(lancamentos);
        assertEquals(totalObtido, totalEsperado);
    }


    static class LancamentoBuilder{
        private Lancamento lancamento;

        LancamentoBuilder(){
            lancamento = new Lancamento();
        }

        LancamentoBuilder comValor(double valor){
            lancamento.setValor(BigDecimal.valueOf(valor));
            return this;
        }
        LancamentoBuilder comTipo(TipoLancamento tipo){
            lancamento.setTipoLancamento(tipo);
            return this;
        }

        Lancamento build(){
            return lancamento;
        }
    }
}
