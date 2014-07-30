/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/**
 * Classe do Projeto Guardião - Criado em 02/05/2013 - Classe utilizada para
 * converter as mascaras de javascript com valores em reais para BigDecimal
 * Funciona apenas se o valor contiver pelomenos uma casa decilmal separada por
 * virgula
 *
 * @author Gilmário
 */
@Named
public class BigDecimalConverter implements Converter {

    /**
     *
     * @param context
     * @param component
     * @param value Valor a ser convertido
     * @return
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) {
            if (value.contains(",")) {
                String cen = value.split(",")[1];
                String inteira = value.split(",")[0].replace(" ", "").replace(".", "").replace("R$", "");
                return new BigDecimal(inteira + "." + cen);
            } else {
                return new BigDecimal(BigInteger.ZERO);
            }
        }
        return new BigDecimal(BigInteger.ZERO);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            String dec = ((BigDecimal) value).toString();
            String inte = "0";
            String cen = "00";
            if (dec.contains(".")) {
                inte = dec.split("\\.")[0];
                cen = dec.split("\\.")[1];
            } else {
                inte = dec;
            }

            char[] listChar = inte.toCharArray();
            String retorno = "";
            for (int i = 0; i <= listChar.length; i++) {
                if (i >= 3 && (i % 3) == 0 && i < listChar.length) {
                    retorno = "." + retorno;
                }
                if (i > 0 & i < listChar.length) {
                    retorno = listChar[listChar.length - i - 1] + retorno;
                } else {
                    if (i < listChar.length) {
                        retorno = listChar[listChar.length - 1] + retorno;
                    }
                }
            }
            return "R$ " + retorno + "," + cen;
        } else {
            return null;
        }
    }
}
