/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.util;

import java.util.Random;

/**
 *
 * @author Daniel
 */
public class GerarNumeros {

    public static int geraNumeroInterio(int algarismos) {
  //instância um objeto da classe Random usando o construtor padrão
        Random gerador = new Random();
        String numero = "";
        //imprime sequência de 10 números inteiros aleatórios
        for (int i = 0; i < algarismos; i++) {
            numero += gerador.nextInt(10) + "";
            System.out.println(numero);
        }
        int retorno = Integer.parseInt(numero);
        return retorno;
    }

}
