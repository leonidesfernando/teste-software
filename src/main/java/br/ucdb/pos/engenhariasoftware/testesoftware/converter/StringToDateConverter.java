package br.ucdb.pos.engenhariasoftware.testesoftware.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static br.ucdb.pos.engenhariasoftware.testesoftware.util.Constantes.DD_MM_YYYY;

public class StringToDateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String dateString) {
        if(dateString == null || dateString.equals("") || dateString.equals(" ")) {
            return null;
        }
        DateFormat formatter = new SimpleDateFormat(DD_MM_YYYY);
        try{
            return formatter.parse(dateString);
        }catch (ParseException e){
            throw new IllegalStateException(e);
        }
    }
}
