/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.controle;

import com.server.lojaserver.beans.DevolucaoBEAN;
import com.server.lojaeserver.persistencia.DevolucaoDAO;
import com.server.lojaserver.beans.PedidoBEAN;
import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class ControleDevolucao {

    private DevolucaoDAO e = new DevolucaoDAO();

    ControleCaixa caixa = new ControleCaixa();

    public String inserirDevolucao(DevolucaoBEAN pro, int pedido, int emp) {
        ControleCaixa cc = new ControleCaixa();
        ControlePedido cp = new ControlePedido();
        ControleVenda v = new ControleVenda();
        PedidoBEAN p = cp.listarPedido(pedido);
        if (p.getQuantidade() >= pro.getQuantidade()) {
            pro.setCaixa(cc.getCaixa(emp));
            int dev = e.inserir(pro);
            ControleProduto proCon = new ControleProduto();
            proCon.aumentaEstoque(p.getProduto(), (pro.getQuantidade()));
            cp.devolver(pedido, dev);
            float valor = v.getValorFinVenda(p.getVenda(), emp);
            v.atualizaValorFinVenda(p.getVenda(), valor);
            return "Produto Devolvido Com Sucesso!";
        } else {
            return "Erro de Redundancia!";
        }
    }

    public ArrayList<DevolucaoBEAN> listarDevolucaoVenda(String venda, int emp) {
        return e.listarDevolucaoVenda(Integer.parseInt(venda));
    }

    public DevolucaoBEAN listarUm(String cod) {
        return e.listarUm(cod);
    }

    public ArrayList<DevolucaoBEAN> listarDevolucaoCaixa(int empresa) {
        return e.listarDevolucaoCaixa(caixa.getCaixa(empresa));
    }

    public Float getTotalDevolucaoCaixa(int caixa) {
        return e.getValorDevolucaoCaixa(caixa);
    }

}
