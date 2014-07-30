/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sefin.enumerated;

/**
 * Classe do Projeto Guardião - Criado em 09/05/2013 -
 *
 * @author Gilmário
 */
public enum Mes {

    Janeiro,
    Fevereiro,
    Março,
    Abril,
    Maio,
    Junho,
    Julho,
    Agosto,
    Setembro,
    Outubro,
    Novembro,
    Dezembro;

    public String asNumero(Mes mes) {
        if (mes.ordinal() < 9) {
            return "0" + Integer.toString(mes.ordinal() + 1);
        }
        return Integer.toString(mes.ordinal() + 1);
    }
}
