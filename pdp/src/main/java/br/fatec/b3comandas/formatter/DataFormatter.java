package br.fatec.b3comandas.formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.springframework.format.Formatter;

import br.fatec.b3comandas.util.Data;

public class DataFormatter implements Formatter<LocalDate> {

    public DataFormatter() {
        super();
    }

    @Override
    public String print(LocalDate localDate, Locale locale) {
        String data = "";
        if (localDate != null) {
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            data = localDate.format(formatter);
        }

        return data;
    }

    @Override
    public LocalDate parse(String string, Locale locale) throws ParseException {
        
        System.out.println("string: " + string);
        
        if (Data.fromStringISO(string) != null) {
            return Data.fromStringISO(string);
        }
        
        
        return Data.fromString(string);
    }
    
}