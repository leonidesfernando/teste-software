package br.ucdb.pos.engenhariasoftware.testesoftware.converter;

import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class StringToMoneyConverter implements Converter<String, BigDecimal> {

    @Override
    public BigDecimal convert(String numberString) {
        if(numberString == null || numberString.equals("") || numberString.trim().replace("R$", "").equals("")) {
            return null;
        }
        try {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            symbols.setDecimalSeparator(',');
            String pattern = "#,##0.0#";
            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
            decimalFormat.setParseBigDecimal(true);

            return new BigDecimal(decimalFormat.parse(numberString.replace("R$ ", "")).toString());
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }
}
