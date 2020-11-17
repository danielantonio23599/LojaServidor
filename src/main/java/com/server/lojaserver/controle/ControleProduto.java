/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.controle;

import com.server.lojaserver.beans.ProdutoBEAN;
import com.server.lojaserver.beans.Produtos;
import com.server.lojaeserver.persistencia.ProdutoDAO;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Daniel
 */
public class ControleProduto {

    private ProdutoDAO p = new ProdutoDAO();
    private ControlePedido pc = new ControlePedido();

    public DefaultComboBoxModel buscar(String produto, String email, String senha) {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();

        ArrayList<Produtos> pp = p.buscar(produto, email, senha);
        if (pp.size() > 0) {
            for (Produtos p : pp) {
                modelo.addElement(p.getCodigo() + " : " + p.getNome() + " : R$ " + p.getPreco());

            }
        } else {
            System.out.println("Retorno vasio");
        }
        return modelo;
    }

    public ArrayList<Produtos> buscarP(String produto, String email, String senha) {
        ArrayList<Produtos> pp = p.buscar(produto, email, senha);
        return pp;
    }

    public ProdutoBEAN localizar(String produto) {
        return p.localizar(Integer.parseInt(produto));
    }

    public ArrayList<ProdutoBEAN> listarAll(String email, String senha) {
        return p.listarALl(email, senha);
    }

    public String cadastrar(ProdutoBEAN f, String email, String senha) {
        ProdutoBEAN pro = p.localizar(f.getNome(), email, senha);
        if (pro.getCodigo() == 0) {
            p.adicionar(f, email, senha);
            return "Cadastro Realizado com sucesso!!";
        } else {
            return "Produto com o mesmo nome já CADASTRADO!!";
        }
    }

    public String editar(ProdutoBEAN f) {
        p.editar(f);
        return "Produto editado com sucesso!!";
    }

    public String excluir(String cod) {
        p.excluir(Integer.parseInt(cod));

        return "Produto Excluido com sucesso!!";
    }

    public Produtos buscarUm(String combo, String email, String senha) {
        ArrayList<Produtos> produtos = p.listarProdutos(email, senha);
        for (Produtos p : produtos) {
            String pro = p.getCodigo() + " : " + p.getNome() + " : R$ " + p.getPreco();
            if (combo.equals(pro)) {
                return p;
            }
        }
        return null;
    }

    public float quantidadeEstoque(int produto, String e, String s, float quantidade) {
        float qtd = p.quantidadeEstoque(produto, e, s);
        return qtd;
    }

    public void diminuiEstoque(int produto, float diminuicao, float atual) {
        p.alteraQuantidade(produto, (-diminuicao));
    }

    public void aumentaEstoque(int produto, float quantidade) {
        System.out.println(" quantidade : " + quantidade);
        p.alteraQuantidade(produto, quantidade);

    }

}
