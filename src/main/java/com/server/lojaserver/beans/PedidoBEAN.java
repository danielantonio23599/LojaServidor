/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.beans;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author Daniel
 */
public class PedidoBEAN {

    private int codigo;
    private Float quantidade;
    private String observacao;
    private String status;
    private int excluzao;
    private int produto;
    private int venda;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }


    public Float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Float quantidade) {
        this.quantidade = quantidade;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getExcluzao() {
        return excluzao;
    }

    public void setExcluzao(int excluzao) {
        this.excluzao = excluzao;
    }

    public int getProduto() {
        return produto;
    }

    public void setProduto(int produto) {
        this.produto = produto;
    }

    public int getVenda() {
        return venda;
    }

    public void setVenda(int venda) {
        this.venda = venda;
    }

}
