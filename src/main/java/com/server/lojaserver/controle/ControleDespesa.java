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
    private DespesaDAO d = new DespesaDAO();
    private ControleCaixa c = new ControleCaixa();

    public ArrayList<DespesaBEAN> listarALL() {
        ArrayList<DespesaBEAN> t = d.listarAll();
        return t;
    }

    public String adicionar(DespesaBEAN despesa) {
        d.adicionar(despesa);
        return "Cadastro Realizado com SUCESSO!!";
    }

    public String excluir(ArrayList<DespesaBEAN> des) {
        for (DespesaBEAN d : des) {
            this.d.excluir(d.getCodigo());
        }
        return "Excluzão realizada com SUCESSO!!";
    }

    public String adicionarDespesaDia(ArrayList<DespesaBEAN> dadosIncluir, int empresa) {
        for (DespesaBEAN d : dadosIncluir) {
            this.d.adicionarDespesaDia(d, c.getCaixa(empresa));
        }
        return "Cadastro Realizado com SUCESSO!!";

    }

    public String retirarDespesaDia(ArrayList<DespesaBEAN> dadosIncluir, int empresa) {
        for (DespesaBEAN d : dadosIncluir) {
            this.d.retirarDespesa(c.getCaixa(empresa), d.getCodigo());
        }
        return "SUCESSO!!";

    }

    public ArrayList<DespesaBEAN> listarDespesaDia(int empresa) {
        return d.listarDespesaCaixa(c.getCaixa(empresa));
    }

    public Float getTotalDespesasCaixa(int empresa) {
        return d.getTotalDespesasCaixa(c.getCaixa(empresa));
    }

}
