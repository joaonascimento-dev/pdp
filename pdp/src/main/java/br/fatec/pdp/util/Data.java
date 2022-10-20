package br.fatec.pdp.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 *
 * @author Ivan
 */
public class Data {
    
    public static LocalDate fromStringISO(String string) {
        try {
            Locale ptBr = new Locale("pt", "BR");
            String formato = "yyyy-MM-dd";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato, ptBr);
            
            return LocalDate.parse(string, formatter);
            
        } catch (Exception e) {
            return null;
        }
    }
    
    public static LocalDate fromString(String string) {
        try {
            Locale ptBr = new Locale("pt", "BR");
            String formato = string.length() == 10 ? "dd/MM/yyyy" : "dd/MM/yy";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato, ptBr);
            
            return LocalDate.parse(string, formatter);
            
        } catch (Exception e) {
            return null;
        }
    }
    
    public static LocalDate fromStringApenasNumeros(String string) {
        try {
            string = Texto.soNumeros(string);
            //string = Texto.substring(string, 0, 2) + "/" + Texto.substring(string, 2, 4) + "/" + Texto.substring(string, 4, string.length());
            System.out.println("string: " + string);
            Locale ptBr = new Locale("pt", "BR");
            String formato = string.length() == 8 ? "ddMMyyyy" : "ddMMyy";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato, ptBr);
            
            return LocalDate.parse(string, formatter);
            
        } catch (Exception e) {
            return null;
        }
    }


    public static String toString(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "";
        }
        return toString(localDateTime.toLocalDate());
    }
    
    public static String toString(LocalDate localDate) {
        String data = "";
        if (localDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            data = localDate.format(formatter);
        }

        return data;
    }
    
    public static String toStringPorExtenso(LocalDate localDate) {
        String data = "";
        if (localDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy");
            data = localDate.format(formatter);
        }

        return data;
    }
    
    
}
