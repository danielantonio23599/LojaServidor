/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.util;

/**
 *
 * @author Daniel
 */
public class Util {

    public static String[] quebraString(String qrCode) {
        return qrCode.split(";");
    }

    public static String geraStringQRCodeVenda(int venda, int mesa) {
        return venda + ";" + mesa;
    }

}
