package com.server.lojaserver.util;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Daniel
 */
public class Horas {

    public static String subtraiHoras(String hora1, String hora2) {
        int h1 = convertMinu(hora1);
        int h2 = convertMinu(hora2);
        int h3 = h1 - h2;
        return convertHora(h3);
    }

    public static int convertMinu(String hora) {
        LocalTime time = LocalTime.parse(hora);
        int h1 = time.getHour();
        int m1 = time.getMinute();
        return (h1 * 60 + m1);
    }

    public static String convertHora(int min) {
        int hora = (min / 60);
        int minuto = (min % 60);
        return hora + ":" + minuto;
    }

    public static String formataHora(String hora) {
        if (!hora.equals("")) {
            LocalTime time = LocalTime.parse(hora);
            int h1 = time.getHour();
            int m1 = time.getMinute();
            return h1 + ":" + m1;
        } else {
            return hora;
        }
    }

    public static String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date data = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();
        String hora_atual = dateFormat.format(data_atual);       
        return hora_atual;
    }
}
