/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.controle;

import com.server.lojaserver.beans.DevolucaoBEAN;
import com.server.lojaeserver.persistencia.DevolucaoDAO;
import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class ControleExcluzao {

    private DevolucaoDAO e = new DevolucaoDAO();

    ControleCaixa caixa = new ControleCaixa();
    public int inserirExclusao(DevolucaoBEAN pro) {
        return e.inserir(pro);
    }

    public ArrayList<DevolucaoBEAN> listarExclusaoVenda(String mesa, int emp) {
        ControleVenda v = new ControleVenda();
        int venda = v.getVenda(Integer.parseInt(mesa),emp);
        return e.listarExclusaoVenda(venda);
    }

    public DevolucaoBEAN listarUm(String cod) {
        return e.listarUm(cod);
    }

    public ArrayList<DevolucaoBEAN> listarExclusaoCaixa(int empresa) {
        return e.listarExclusaoCaixa(caixa.getCaixa(empresa));
    }

}
