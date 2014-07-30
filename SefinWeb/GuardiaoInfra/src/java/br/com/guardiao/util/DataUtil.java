/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.util;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author gilmario
 */
public class DataUtil {

    public static Date setDataComHoraMaxima(Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.set(Calendar.HOUR_OF_DAY, c.getActualMaximum(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, c.getActualMaximum(Calendar.MINUTE));
        c.set(Calendar.SECOND, c.getActualMaximum(Calendar.SECOND));
        return c.getTime();
    }

    public static Date setDataComHoraMinima(Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static void main(String[] args) {
        System.out.println(new Date());
        System.out.println(setDataComHoraMaxima(new Date()));
        System.out.println(setDataComHoraMinima(new Date()));
    }
}
