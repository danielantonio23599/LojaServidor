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

    public String inserirDevolucao(DevolucaoBEAN pro, int pedido, String u, String s) {
        ControlePedido cp = new ControlePedido();
        ControleVenda v = new ControleVenda();
        PedidoBEAN p = cp.listarPedido(pedido);
        if (p.getQuantidade() >= pro.getQuantidade()) {
            int dev = e.inserir(pro, u, s);
            ControleProduto proCon = new ControleProduto();
            proCon.aumentaEstoque(p.getProduto(), (pro.getQuantidade()));
            cp.devolver(pedido, dev);
            float valor = pro.getQuantidade() * pro.getValor();
            v.atualizaValorFinVenda(p.getVenda(), valor);
            return "Produto Devolvido Com Sucesso!";
        } else {
            return "Erro de Redundancia!";
        }
    }

    public ArrayList<DevolucaoBEAN> listarDevolucaoVenda(String venda) {
        return e.listarDevolucaoVenda(Integer.parseInt(venda));
    }

    public DevolucaoBEAN listarUm(String cod) {
        return e.listarUm(cod);
    }

    public ArrayList<DevolucaoBEAN> listarDevolucaoCaixa(String u, String s) {
        return e.listarDevolucaoCaixa(u, s);
    }

    public Float getTotalDevolucaoCaixa(String u, String s) {
        return e.getValorDevolucaoCaixa(u, s);
    }

}
