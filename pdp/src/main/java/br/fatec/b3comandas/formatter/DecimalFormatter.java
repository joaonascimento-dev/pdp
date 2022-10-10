package br.fatec.b3comandas.formatter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

import br.fatec.b3comandas.util.Decimal;

public class DecimalFormatter implements Formatter<BigDecimal> {

    public DecimalFormatter() {
        super();
    }

    @Override
    public String print(BigDecimal value, Locale locale) {
        return Decimal.toString(value);
        
    }

    @Override
    public BigDecimal parse(String value, Locale locale) throws ParseException {
        try {
            if(value == null) {
                return BigDecimal.ZERO;
            }
            
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            symbols.setDecimalSeparator(',');
                
            String pattern = "#,##0.000000";
            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
            decimalFormat.setParseBigDecimal(true);

            return (BigDecimal) decimalFormat.parse(value);

        } catch (ParseException ex) {
            //System.err.println(ex);
            return BigDecimal.ZERO;
        }
        
        
    }
}