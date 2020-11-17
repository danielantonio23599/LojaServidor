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
import com.server.lojaserver.beans.PedidoBEAN;
import com.server.lojaserver.util.Horas;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Daniel
 */
public class ControlePedido {

    private ProdutoDAO p = new ProdutoDAO();
    private PedidoDAO ped = new PedidoDAO();

    public DefaultComboBoxModel buscar(String produto, String email, String senha) {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        ArrayList<Produtos> pe = p.buscar(produto, email, senha);
        for (Produtos p : pe) {
            modelo.addElement(p.getCodigo() + " : " + p.getNome() + " : R$ " + p.getPreco());

        }

        return modelo;
    }

    public ArrayList<ProdutoBEAN> listarAll(String email, String senha) {
        return p.listarALl(email, senha);
    }

    public String cadastrar(ProdutoBEAN f, String email, String senha) {
        p.adicionar(f, email, senha);
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

    public ArrayList<Produtos> listarPedidos(ArrayList<Produtos> pro, String email, String senha) {
        ArrayList<Produtos> produtos = p.listarProdutos(email, senha);
        for (Produtos produto : produtos) {
            pro.add(produto);
        }
        return pro;
    }

    public PedidoBEAN listarPedido(int pedido) {
        PedidoDAO p = new PedidoDAO();
        return p.localizar(pedido);
    }

    public ArrayList<PedidoBEAN> listarPedidos(String e, String s) {
        PedidoDAO ped = new PedidoDAO();
        return ped.listarPedidos(e, s);
    }

    public ArrayList<PedidoBEAN> alterarPedido(String p, String e, String s) {
        PedidoDAO ped = new PedidoDAO();
        ped.mudarStatusRealizado(Integer.parseInt(p), Horas.getTime());
        return listarPedidos(e,s);
    }

    /* public ArrayList<Pedido> listarPedidosCaixa(String e, String s) {
        ControleCaixa cai = new ControleCaixa();
        PedidoDAO ped = new PedidoDAO();
        return ped.listarPedidos(emp, caixa);

    }

    

    public ArrayList<Pedido> listarPedidosRealizados(String e, String s) {
        ControleCaixa cai = new ControleCaixa();
        PedidoDAO ped = new PedidoDAO();
        return ped.listarPedidosRealizados(emp, caixa);
    }

    public ArrayList<Pedido> listarPedidosAtrazados(String e, String s) {
        ControleCaixa cai = new ControleCaixa();
        PedidoDAO ped = new PedidoDAO();
        return ped.listarPedidosAtrazados(emp, caixa);
    }*/

    public Produtos buscarUm(String combo, String email, String senha) {
        ArrayList<Produtos> todos = p.listarProdutos(email, senha);
        for (Produtos p : todos) {
            String pro = p.getCodigo() + " : " + p.getNome() + " : R$ " + p.getPreco();
            if (combo.equals(pro)) {
                return p;
            }
        }
        return null;

    }

    public void devolver(int codigo, int devolucao) {

        ped.devolver(codigo, devolucao);
    }

}
