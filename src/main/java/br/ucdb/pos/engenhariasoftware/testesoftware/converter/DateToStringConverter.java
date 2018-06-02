package br.ucdb.pos.engenhariasoftware.testesoftware.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static br.ucdb.pos.engenhariasoftware.testesoftware.util.Constantes.DD_MM_YYYY;

public class DateToStringConverter implements Converter<Date, String> {

    @Override
    public String convert(Date date) {
        if(date == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(DD_MM_YYYY);
        return df.format(date);
    }
}
