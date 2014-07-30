/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.util;

import java.text.Normalizer;

/**
 *
 * @author gilmario
 */
public class RemoveAcentosUtil {

    public static String removeAccentos(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}
