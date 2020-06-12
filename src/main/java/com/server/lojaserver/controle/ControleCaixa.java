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

    public int isCaixaAberto(int empresa) {
        CaixaBEAN caixa = c.listar(empresa);
        return caixa.getCodigo();
    }

    public CaixaBEAN getCaixaAberto(int empresa) {
        CaixaBEAN caixa = c.listar(empresa);
        return caixa;
    }

    public int getCaixa(int empresa) {
        return isCaixaAberto(empresa);
    }

    public String abrirCaixa(CaixaBEAN ca, int empCod) {
        ca.setEmpresa(empCod);
        if (isCaixaAberto(empCod) == 0) {
            c.abrirCaixa(ca);
            return "Abriu!!";
        } else {
            return "Caixa Já Aberto";
        }

    }

    public String fecharCaixa(String troco, int empresa) {
        CaixaBEAN ca = new CaixaBEAN();
        ca.setCodigo(getCaixa(empresa));
        ca.setOut(Time.getTime());
        ca.setTrocoFin(Float.parseFloat(troco));
        c.fecharCaixa(ca);
        return "Sucesso";
    }

    public CaixaBEAN listar(int empresa) {
        return c.listar(empresa);
    }

    public String getSaldoAtual(int empresa) {
        int caixa = getCaixa(empresa);
        System.out.println(caixa);
        if (caixa > 0) {
            float saldo = c.getSaldoAtual(caixa, empresa);
            return saldo + "";
        } else {
            return "-1";
        }

    }

    public String getTotalVendido(int empresa) {
        float saldo = c.getTotalVendido(getCaixa(empresa), empresa);
        return saldo + "";
    }

    public Caixa listarValoresCaixa(int empresa) {
        ControleDespesa d = new ControleDespesa();
        ControleSangria s = new ControleSangria();
        ControleDevolucao e = new ControleDevolucao();
        int caixa = getCaixa(empresa);
        Caixa c = new Caixa();
        c.setCaixa(caixa);
        c.setDespesas(d.getTotalDespesasCaixa(empresa));
        c.setSangria(s.getTotalSangriasCaixa(empresa));
        c.setSaldo(Float.parseFloat(getSaldoAtual(empresa)));
        c.setFaturamento(Float.parseFloat(getTotalVendido(empresa)));
        c.setDevolucao(e.getTotalDevolucaoCaixa(caixa));
        return c;
    }
}
