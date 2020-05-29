/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Daniel
 */
public class Time {

    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date hora = Calendar.getInstance().getTime(); // Ou qualquer outra forma que tem
        return sdf.format(hora);
    }

    public static String getData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String formataDataBR(String data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(data);
    }

    public static String adicionarMeses(String dataAtual, int quantidadeMeses) {
        Date d = null;
        try {
            d = new SimpleDateFormat("dd/MM/yyyy").parse(dataAtual);
        } catch (ParseException o) {

        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        System.out.println(cal.getTime()); //data atual
        cal.add(Calendar.MONTH, quantidadeMeses);
        System.out.println(cal.getTime());
        return cal.getTime() + "";
    }

}
