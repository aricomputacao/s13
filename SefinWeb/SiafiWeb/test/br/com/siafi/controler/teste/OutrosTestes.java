/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controler.teste;

import br.com.guardiao.util.importacao.ArquivoEmLinha;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe do Projeto ******* - Criado em 02/05/2013 -
 *
 * @author Gilmário
 */
public class OutrosTestes {

    public static void main(String[] args) {
        BigDecimal a = new BigDecimal(50);
        BigDecimal b = new BigDecimal(51);
        System.out.println(a.compareTo(b));

    }

    public String teste(String linha) {
        linha = "xxxx";
        System.err.println(linha);
        linha = "Metodo";
        teste(linha);
        return linha;
    }
}
