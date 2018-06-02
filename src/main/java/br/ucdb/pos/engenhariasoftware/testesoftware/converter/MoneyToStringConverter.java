package br.ucdb.pos.engenhariasoftware.testesoftware.converter;

import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;
import java.text.NumberFormat;

import static br.ucdb.pos.engenhariasoftware.testesoftware.util.Constantes.BR;

public class MoneyToStringConverter implements Converter<BigDecimal, String> {

    @Override
    public String convert(BigDecimal bigDecimal) {
        if(bigDecimal == null)
            return "";
        return NumberFormat.getCurrencyInstance(BR).format(bigDecimal);
    }
}

