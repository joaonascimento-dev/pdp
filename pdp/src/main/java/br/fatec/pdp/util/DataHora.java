package br.fatec.pdp.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHora {
    
    public static LocalDateTime fromString(String value) {
        try {
            Locale ptBr = new Locale("pt", "BR");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", ptBr);
            if (value.length() == 10) {
                value += " 00:00:00";
            } else if (value.length() == 16) {
                value += ":00";
            }
            
            return LocalDateTime.parse(value, formatter);
            
        } catch (Exception e) {
            return null;
        }
    }
    
    public static LocalDateTime fromStringISO(String value) {
        try {
            Locale ptBr = new Locale("pt", "BR");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS", ptBr);
            if (value.length() == 10) {
                value += " 00:00:00";
            } else if (value.length() == 16) {
                value += ":00";
            }
            
            return LocalDateTime.parse(value, formatter);
            
        } catch (Exception e) {
            return null;
        }
    }
    
    
    public static LocalDateTime fromString(String value, LocalTime atTime) {
        
        LocalDateTime dataHora = fromString(value);
        
        atTime = atTime == null ? LocalTime.MIN : atTime;
        
        return dataHora == null ? null : dataHora.toLocalDate().atTime(atTime);
        
    }
    
    public static String toString(LocalDateTime localDateTime) {
        String data = "";
        if (localDateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            data = localDateTime.format(formatter);
        }



       return data;
    }
    
}