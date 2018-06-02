package br.ucdb.pos.engenhariasoftware.testesoftware.exemplos.mockito;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class GeradorTest {

    @Mock
    private GeradorService gerador;

    @BeforeClass
    protected void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test(dataProvider = "lista")
    public void exportaXls(List<Usuario> usuarios){
        when(gerador.xls(anyListOf(Usuario.class))).thenCallRealMethod();
        File arquivoXls = gerador.xls(usuarios);
        List<ErroConteudoColuna> errosConteudo = validaConteudoColunas(arquivoXls, usuarios);
        ErroOrdemColunas erroOrdemColunas = validaOrdemColunas(arquivoXls);
        assertTrue(errosConteudo.isEmpty(),
                String.format("A(s) segunte(s) coluna(s) está(ão) fora do padrão (esperado/encontrado) -> %s",
                        errosConteudo.stream().map(ErroConteudoColuna::toString)
                                .collect(Collectors.joining(","))));

        assertFalse(erroOrdemColunas.isTemErro(),
                String.format("O arquivo possui uma ou mais colunas fora de ordem. Colunas esperadas nesta ordem: [%s]."
                                + " Ordem encontrada no arquivo: [%s]",
                        erroOrdemColunas.ordemCorreta, erroOrdemColunas.ordemEncontrada));

    }

    @DataProvider(name = "lista")
    protected Object[][] geraListaUsuarios() {
        return new Object[][]{
                new Object[]{Arrays.asList(
                        new Usuario("Brenda Gomes Melo", "brendagomesmelo@rhyta.com",
                                null, null,
                                Usuario.Perfil.ADMIN),
                        new Usuario("Luan Araujo Gomes", "",
                                "203.549.743-43",
                                LocalDateTime.of(2018, 03, 10, 15, 33),
                                Usuario.Perfil.OPERADOR),
                        new Usuario("José Melo Martins", "JoseMeloMartins@dayrep.com",
                                "216.049.733-90",
                                LocalDateTime.of(2018, 04, 12, 18, 45),
                                Usuario.Perfil.OPERADOR),
                        new Usuario("Luiza Melo Fernandes", "luizamelofernandes@teleworm.us",
                                "142.987.409-00 ",
                                LocalDateTime.of(2018, 03, 10, 14, 19),
                                Usuario.Perfil.GERENTE),
                        new Usuario("Martim Silva Cardoso", "martimsilvacardoso@jourrapide.com",
                                "175.278.480-49",
                                LocalDateTime.of(2018, 03, 10, 20, 3),
                                Usuario.Perfil.OPERADOR)
                )},
        };
    }

    @Test(dataProvider = "lista")
    public void exportaUsuarioXlsTestComMetodoEstatico(List<Usuario> usuarios) {
        GeradorService geradorLocal = mock(GeradorService.class);
        when(geradorLocal.xls(anyListOf(Usuario.class))).thenCallRealMethod();
        File arquivoXls = geradorLocal.xls(usuarios);

        List<ErroConteudoColuna> errosConteudo = validaConteudoColunas(arquivoXls, usuarios);
        ErroOrdemColunas erroOrdemColunas = validaOrdemColunas(arquivoXls);
        assertTrue(errosConteudo.isEmpty(),
                String.format("A(s) segunte(s) coluna(s) está(ão) fora do padrão (esperado/encontrado) -> %s",
                        errosConteudo.stream().map(ErroConteudoColuna::toString)
                                .collect(Collectors.joining(","))));

        assertFalse(erroOrdemColunas.isTemErro(),
            String.format("O arquivo possui uma ou mais colunas fora de ordem. Colunas esperadas nesta ordem: [%s]."
                                + " Ordem encontrada no arquivo: [%s]",
                        erroOrdemColunas.ordemCorreta, erroOrdemColunas.ordemEncontrada));
    }

    private ErroOrdemColunas validaOrdemColunas(File arquivoXls) {
        return new ErroOrdemColunas();
    }

    private List<ErroConteudoColuna> validaConteudoColunas(File arquivoXls, List<Usuario> usuarios) {
        return Collections.EMPTY_LIST;
    }

    static class ErroConteudoColuna {
        String esperado;
        String encontrado;

        @Override
        public String toString() {
            return super.toString();
        }
    }

    static class ErroOrdemColunas {
        String ordemCorreta;
        String ordemEncontrada;

        boolean isTemErro(){
            return false;
        }
    }

}

