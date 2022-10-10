package br.fatec.b3comandas;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.fatec.b3comandas.formatter.DataFormatter;
import br.fatec.b3comandas.formatter.DecimalFormatter;

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
