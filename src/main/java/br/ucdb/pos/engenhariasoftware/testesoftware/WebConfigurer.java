package br.ucdb.pos.engenhariasoftware.testesoftware;

import br.ucdb.pos.engenhariasoftware.testesoftware.converter.DateToStringConverter;
import br.ucdb.pos.engenhariasoftware.testesoftware.converter.MoneyToStringConverter;
import br.ucdb.pos.engenhariasoftware.testesoftware.converter.StringToDateConverter;
import br.ucdb.pos.engenhariasoftware.testesoftware.converter.StringToMoneyConverter;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class WebConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToMoneyConverter());
        registry.addConverter(new StringToDateConverter());
        registry.addConverter(new DateToStringConverter());
        registry.addConverter(new MoneyToStringConverter());
    }
}
