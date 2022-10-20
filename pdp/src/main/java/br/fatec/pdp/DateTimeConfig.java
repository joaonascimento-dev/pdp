package br.fatec.pdp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.fatec.pdp.formatter.DataFormatter;
import br.fatec.pdp.formatter.DecimalFormatter;

@Configuration
class DateTimeConfig {
    @Bean
    public DataFormatter dataFormatter() {
        return new DataFormatter();
    }
    
    @Bean
    public DecimalFormatter decimalFormatter() {
        return new DecimalFormatter();
    }
}
