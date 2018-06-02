package br.ucdb.pos.engenhariasoftware.testesoftware.exemplos;

import java.math.BigDecimal;

public class CalculadoraSimples {

    public int soma(int s1, int s2){
        return s1 + s2;
    }

    public BigDecimal subtrai(double d1, double d2){
        return BigDecimal.valueOf((d1-d2));
    }

    public BigDecimal soma(double d1, double d2){
        return BigDecimal.valueOf((d1+d2));
    }

    public BigDecimal divide(double d1, double d2){
        return BigDecimal.valueOf((d1/d2));
    }

    public BigDecimal multiplica(double d1, double d2){
        return BigDecimal.valueOf((d1*d2));
    }
}



