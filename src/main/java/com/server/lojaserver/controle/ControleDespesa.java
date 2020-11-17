/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.controle;

import com.server.lojaserver.beans.DespesaBEAN;
import com.server.lojaeserver.persistencia.DespesaDAO;
import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class ControleDespesa {

    final private DespesaDAO d = new DespesaDAO();
    final private ControleCaixa c = new ControleCaixa();

    public ArrayList<DespesaBEAN> listarALL(String e, String s) {
        ArrayList<DespesaBEAN> t = d.listarAll(e, s);
        return t;
    }

    public String adicionar(DespesaBEAN despesa, String u, String s) {
        d.adicionar(despesa, u, s);
        return "Cadastro Realizado com SUCESSO!!";
    }

    public String excluir(ArrayList<DespesaBEAN> des) {
        for (DespesaBEAN d : des) {
            this.d.excluir(d.getCodigo());
        }
        return "Excluzão realizada com SUCESSO!!";
    }

    public Float getTotalDespesasCaixa(String u, String s) {
        return d.getTotalDespesasCaixa(u, s);
    }

}
