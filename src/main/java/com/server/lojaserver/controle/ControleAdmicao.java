/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.controle;

import com.server.lojaserver.beans.AdmicaoBEAN;
import com.server.lojaeserver.persistencia.AdmicaoDAO;
import com.server.lojaserver.util.Time;

/**
 *
 * @author Daniel
 */
public class ControleAdmicao {

    public String preCadastro(int cod, int empCodigo) {
        AdmicaoDAO a = new AdmicaoDAO();
        a.preAdicionar(cod, empCodigo);
        // enviar email para empresa notificando
        return "Sucesso";
    }

    public AdmicaoBEAN localizar(String funcionario, String email, String senha) {
        AdmicaoDAO a = new AdmicaoDAO();

        return a.localizar(Integer.parseInt(funcionario), email, senha);
    }

    public boolean admitir(AdmicaoBEAN ad, String email, String senha) {
        AdmicaoDAO a = new AdmicaoDAO();
        return a.confirmarAdmicao(ad, email, senha);
    }

    public boolean demitir(AdmicaoBEAN ad, String email, String senha) {
        AdmicaoDAO a = new AdmicaoDAO();
        return a.demitir(ad, email, senha);
    }

    public boolean excluir(AdmicaoBEAN ad, String email, String senha) {
        AdmicaoDAO a = new AdmicaoDAO();        
        return a.excluir(ad, email, senha);
    }

    public void admitir(int codFun, int codEmp) {
        AdmicaoDAO a = new AdmicaoDAO();
        AdmicaoBEAN ad = new AdmicaoBEAN();
        ad.setEmpresa(codEmp);
        ad.setFuncionario(codFun);
        ad.setAdmicao(Time.getData());
        a.admitir(ad);

    }
}
