package br.ucdb.pos.engenhariasoftware.testesoftware.exemplos.testng;

import br.ucdb.pos.engenhariasoftware.testesoftware.exemplos.CalculadoraSimples;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.*;

public class CalculadoraSimplesTest {

    @Test
    public void somaNumerosPositivosTest(){
        CalculadoraSimples calculadoraSimples = new CalculadoraSimples();
        assertEquals(calculadoraSimples.soma(10, 20), 30);
    }

    @Test(groups = "somaNegativos")
    public void somaNumerosNegativosTest(){
        CalculadoraSimples calculadoraSimples = new CalculadoraSimples();
        assertEquals(calculadoraSimples.soma(-10, -20), -30);
    }

    @Test
    public void somaNumeroNegativosComPositivoTest() {
        CalculadoraSimples calculadoraSimples = new CalculadoraSimples();
        assertTrue(calculadoraSimples.soma(-10, 20) == 10);
    }
}


