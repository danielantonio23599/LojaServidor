/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.controle;

import com.server.lojaserver.beans.Caixa;
import com.server.lojaserver.beans.CaixaBEAN;
import com.server.lojaeserver.persistencia.CaixaDAO;

import com.server.lojaserver.util.Time;

/**
 *
 * @author Daniel
 */
public class ControleCaixa {

    private final CaixaDAO c = new CaixaDAO();
    //

    public int isCaixaAberto(String u, String s) {
        return c.listar(u, s);
    }

    public CaixaBEAN getCaixaAberto(int empresa) {
        CaixaBEAN caixa = c.listar(empresa);
        return caixa;
    }

    public int getCaixa(String u, String s) {
        return c.listar(u, s);
    }

    public String abrirCaixa(CaixaBEAN ca, String u, String s) {
        if (isCaixaAberto(u, s) == 0) {
            c.abrirCaixa(ca, u, s);
            return "Abriu!!";
        } else {
            return "Caixa Já Aberto";
        }

    }

    public String fecharCaixa(String troco, String u, String s) {
        CaixaBEAN ca = new CaixaBEAN();
        ca.setCodigo(getCaixa(u,s));
        ca.setOut(Time.getTime());
        ca.setTrocoFin(Float.parseFloat(troco));
        c.fecharCaixa(ca);
        return "Sucesso";
    }

    public CaixaBEAN listar(int empresa) {
        return c.listar(empresa);
    }

    public String getSaldoAtual(String u, String s) {
            float saldo = c.getSaldoAtual(u,s);
            return saldo + "";
    }

    public String getTotalVendido(String u, String s) {
        float saldo = c.getTotalVendido(u,s);
        return saldo + "";
    }

    public Caixa listarValoresCaixa(String u, String senha) {
        return c.getValoresCaixa(u, senha);
    }
}
