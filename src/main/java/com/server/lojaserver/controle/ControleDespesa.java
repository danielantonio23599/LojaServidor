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

    public ArrayList<DespesaBEAN> listarALL(int emp) {        
        ArrayList<DespesaBEAN> t = d.listarAll(c.getCaixa(emp));
        return t;
    }

    public String adicionar(DespesaBEAN despesa, int emp) {
        despesa.setCaixa(c.getCaixa(emp));
        d.adicionar(despesa);
        return "Cadastro Realizado com SUCESSO!!";
    }

    public String excluir(ArrayList<DespesaBEAN> des) {
        for (DespesaBEAN d : des) {
            this.d.excluir(d.getCodigo());
        }
        return "Excluzão realizada com SUCESSO!!";
    }

    public Float getTotalDespesasCaixa(int empresa) {
        return d.getTotalDespesasCaixa(c.getCaixa(empresa));
    }

}
