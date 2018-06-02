package br.ucdb.pos.engenhariasoftware.testesoftware.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyDeserialize extends JsonDeserializer<BigDecimal> {

    @Override
    public BigDecimal deserialize(JsonParser jsonParser,
                                  DeserializationContext deserializationContext) throws IOException {

        String valorString = jsonParser.getText();
        if(valorString == null || valorString.trim().equals("R$") || valorString.trim().equals("")){
            return null;
        }
        final BigDecimal valor = new BigDecimal(valorString
                .trim()
                .replace(",", ".")
                .replace("R$", "")
                .trim());
        return valor.setScale(2, RoundingMode.HALF_UP);
    }
}
