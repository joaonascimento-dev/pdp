package br.fatec.b3comandas.util;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ivand
 */
public class Texto {

    /**
     * 
     * @param str
     * @return String str.trim() ou "" se valor for nulo
     */
    public static String parse(String str) {
        return str != null ? str.trim() : "";
    }
    
    public static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
    
    public static String removerEspeciais(String str) {
        str = parse(str);
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^a-zA-Z0-9 ]", "");
    }

    public static String soNumeros(String str) {
        str = parse(str);
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^0-9]", "");
    }

    /**
     * 
     * @param s
     * @param length
     * @return pad with " " to the right to the given length and cut
     */
    public static String padRightAndCut(String s, int length) {
        return String.format("%1$-" + length + "s", s).substring(0, length);
    }

    /**
     * 
     * @param s
     * @param length
     * @param caracterPad
     * @return preenche à direita com o caracter informado e corta no comprimento informado
     */
    public static String padRightAndCut(String s, int length, Character caracterPad) {
        return Texto.padRightAndCut(s, length).replace(' ', caracterPad);
    }
    
    /**
     * 
     * @param s
     * @param length
     * @return pad with " " to the left to the given length and cut
     */
    public static String padLeftAndCut(String s, int length) {
        return String.format("%1$" + length + "s", s).substring(0, length);
    }
    
    /**
     * 
     * @param s
     * @param length
     * @param caracterPad
     * @return preenche a esquerda com o caracter informado e corta no comprimento informado
     */
    public static String padLeftAndCut(String s, int length, Character caracterPad) {
        return Texto.padLeftAndCut(s, length).replace(' ', caracterPad);
    }
    
    public static String padSplit(String prefixo, String s, int length) {
        return prefixo + Texto.padLeftAndCut(s, length - prefixo.length());
    }
    
    /**
     * 
     * @param s
     * @param length
     * @return pad with " " to the left to the given length and cut
     */
    public static String padCenterAndCut(String s, int length) {
        if (s.length() > length) {
            return s.substring(0, length);
        }
        
        int newLen = s.length() + (length - s.length()) / 2;
        
        //newLen = newLen > 0 ? newLen : length;
        
        //System.out.println("padSize: " + newLen);
        
        return padLeftAndCut(s, newLen);
    }
    

    
    /**
     * 
     * @param value
     * @param beginIndex
     * @param endIndex
     * @return subtring ignorando erros por index range
     */
    public static String substring(String value, int beginIndex, int endIndex) {
        if (value.length() < beginIndex) {
            return "";
        } else if (value.length() > endIndex) {
            return value.substring(beginIndex, endIndex);
        } else {
            return value.substring(beginIndex, value.length());
        }
    }
    
    /**
     * 
     * @param value
     * @param largura
     * @param partes
     * @return lista com a quantidade limite de partes e largura definidas
     */
    public static List<String> fatiar(String value, int largura, int partes) {
        List<String> fatias = new ArrayList<>();
        for(int i = 0; i < partes; i++) {
            String fatia = substring(value, i * largura, i * largura + largura);
            //System.out.println("fatia: " + fatia);
            if(!fatia.isEmpty()) {
                fatias.add(fatia);
            }
        }
        
        return fatias;
    }

    public static String formatarCnpj(String cnpj) {
        String str = soNumeros(cnpj);
        //04.6159.180/001-04
        return str.substring(0, 2) + "." + str.substring(2, 5) + "." + str.substring(5, 8) + "/" + str.substring(8, 12) + "-" + str.substring(12, 14);
        
    }
    
    public static String formatarCep(String cep) {
        try {
            String str = soNumeros(cep);
            //13970-000
            return str.substring(0, 5) + "-" + str.substring(5, 8);
            
        } catch (Exception e) {
            return null;
        }
        
    }
    
}
