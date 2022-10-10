package br.fatec.b3comandas.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class Decimal {

    /**
     * Converts strings from brazillian format decimal values to BigDecimal
     *
     * @param value
     * @param separadorPonto indica se o separador decimal é ponto. O padrão é vírgula.
     * @return
     */
    private static BigDecimal fromString(String value, boolean separadorPonto) {
        try {
            if(value == null) {
                return BigDecimal.ZERO;
            }
            
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            if(separadorPonto) {
                symbols.setGroupingSeparator(',');
                symbols.setDecimalSeparator('.');
            } else {
                symbols.setGroupingSeparator('.');
                symbols.setDecimalSeparator(',');
            }
            String pattern = "#,##0.0000000000";
            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
            decimalFormat.setParseBigDecimal(true);

            return (BigDecimal) decimalFormat.parse(value);

        } catch (ParseException ex) {
            //System.err.println(ex);
            return BigDecimal.ZERO;
        }
    }
    
    public static BigDecimal fromStringComPonto(String value) {
        return fromString(value, true);
    }
    
    public static BigDecimal fromString(String value) {
        return fromString(value, false);
    }
    
    public static String toString(BigDecimal value) {
        return toString(value, 2);
    }

    public static String toString(BigDecimal value, Integer decimalPlaces) {
        BigDecimal bd;
        try {
            bd = value.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            bd = BigDecimal.ZERO;
        }
        
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(decimalPlaces);
        decimalFormat.setMinimumFractionDigits(decimalPlaces);
        decimalFormat.setGroupingUsed(true);
        
        return decimalFormat.format(bd);
    }
    
    public static String toStringAjustarDecimais(BigDecimal valor) {
        return toStringAjustarDecimais(valor, 0);
    }
     
    public static String toStringAjustarDecimais(BigDecimal valor, int minimoCasasDecimais) {
        int casasDecimais = Math.max(minimoCasasDecimais, valor.stripTrailingZeros().scale());
        return toString(valor, casasDecimais);
    }
    
    public static BigDecimal nullToZero(BigDecimal valor) {
        return valor == null
                ? BigDecimal.ZERO
                : valor;
    }
}
