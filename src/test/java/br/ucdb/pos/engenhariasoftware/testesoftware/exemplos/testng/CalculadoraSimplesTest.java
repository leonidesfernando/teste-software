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

    @Test(expectedExceptions = NumberFormatException.class)
    public void dividePorZeroTest(){
        CalculadoraSimples calculadoraSimples = new CalculadoraSimples();
        BigDecimal valor = calculadoraSimples.divide(0, 0);
        System.out.println(valor);
        assertTrue(valor.doubleValue() == 0);
    }
}


