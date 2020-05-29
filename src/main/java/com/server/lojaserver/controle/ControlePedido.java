/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.controle;

import com.server.lojaserver.beans.Pedido;
import com.server.lojaserver.beans.ProdutoBEAN;
import com.server.lojaserver.beans.Produtos;
import com.server.lojaeserver.persistencia.PedidoDAO;
import com.server.lojaeserver.persistencia.ProdutoDAO;
import com.server.lojaserver.util.Horas;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Daniel
 */
public class ControlePedido {

    private ProdutoDAO p = new ProdutoDAO();

    public DefaultComboBoxModel buscar(String produto,int emp) {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        ArrayList<Produtos> pe = p.buscar(produto, emp);
        for (Produtos p : pe) {
            modelo.addElement(p.getCodigo() + " : " + p.getNome() + " : R$ " + p.getPreco());

        }

        return modelo;
    }

    public ArrayList<ProdutoBEAN> listarAll(int emp) {
        return p.listarALl(emp);
    }

    public String cadastrar(ProdutoBEAN f, int emp) {
        p.adicionar(f, emp);
        return "Cadastro realizado com sucesso!!";
    }

    public String editar(ProdutoBEAN f) {
        p.editar(f);
        return "Pedido editado com sucesso!!";
    }

    public String excluir(int cod) {
        p.excluir(cod);
        return "Exclusão realizado com sucesso!!";
    }

    public ProdutoBEAN localizar(int i) {
        return p.localizar(i);
    }

    public ArrayList<Produtos> listarPedidos(ArrayList<Produtos> pro, int emp) {
        ArrayList<Produtos> produtos = p.listarProdutos(emp);
        for (Produtos produto : produtos) {
            pro.add(produto);
        }
        return pro;
    }

    public ArrayList<Pedido> listarPedidos(int emp) {
        ControleCaixa cai = new ControleCaixa();
        PedidoDAO ped = new PedidoDAO();
        int caixa = cai.getCaixa(emp);
        //ArrayList<Pedido> pedidos = ped.listarPedidosAbertos(emp, caixa);
        return null;
    }

    public ArrayList<Pedido> listarPedidosCaixa(int emp) {
        ControleCaixa cai = new ControleCaixa();
        PedidoDAO ped = new PedidoDAO();
        int caixa = cai.getCaixa(emp);
       // ArrayList<Pedido> pedidos = ped.listarPedidos(emp, caixa);
        return null;
    }

    public ArrayList<Pedido> alterarPedido(String p, int emp) {
        PedidoDAO ped = new PedidoDAO();
        ped.mudarStatusRealizado(Integer.parseInt(p), Horas.getTime());
        return listarPedidos(emp);
    }

    public ArrayList<Pedido> listarPedidosRealizados(int emp) {
        ControleCaixa cai = new ControleCaixa();
        PedidoDAO ped = new PedidoDAO();
        int caixa = cai.getCaixa(emp);
        //ArrayList<Pedido> pedidos = ped.listarPedidosRealizados(emp, caixa);
        return null;
    }

    public ArrayList<Pedido> listarPedidosAtrazados(int emp) {
        ControleCaixa cai = new ControleCaixa();
        PedidoDAO ped = new PedidoDAO();
        int caixa = cai.getCaixa(emp);
        //ArrayList<Pedido> pedidos = ped.listarPedidosAtrazados(emp, caixa);
        return null;
    }

    public Produtos buscarUm(String combo, int emp) {
        ArrayList<Produtos> todos = p.listarProdutos(emp);
        for (Produtos p : todos) {
            String pro = p.getCodigo() + " : " + p.getNome() + " : R$ " + p.getPreco();
            if (combo.equals(pro)) {
                return p;
            }
        }
        return null;

    }

}
