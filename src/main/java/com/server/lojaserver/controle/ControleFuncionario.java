/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.controle;

import com.server.lojaserver.beans.CargoBEAN;
import com.server.lojaserver.beans.FuncionarioBEAN;
import java.util.ArrayList;
import com.server.lojaeserver.persistencia.FuncionarioDAO;
import com.server.lojaserver.util.GerarNumeros;

/**
 *
 * @author Daniel
 */
public class ControleFuncionario {

    public int funCargo(int cod) {
        FuncionarioDAO f = new FuncionarioDAO();
        return f.funCargo(cod);
    }

    public void atuaizar(FuncionarioBEAN fu) {
        FuncionarioDAO f = new FuncionarioDAO();
        f.editar(fu);
    }

    public int cadastrar(FuncionarioBEAN fu) {
        FuncionarioDAO f = new FuncionarioDAO();
        if (f.isExiste(fu.getEmail()) == false) {
            return f.adicionar(fu);
        } else {
            return 0;
        }
    }
    public int cadastrarADM(FuncionarioBEAN fu) {
        FuncionarioDAO f = new FuncionarioDAO();
        if (f.isExiste(fu.getEmail()) == false) {
            return f.adicionarAdm(fu);
        } else {
            return 0;
        }
    }

    public int gerarNumeroPonto() {
        FuncionarioDAO f = new FuncionarioDAO();
        System.out.println("passou");
        boolean para = false;
        int valor = GerarNumeros.geraNumeroInterio(10);
        while (para) {
            if (f.numeroCartaoExistente(valor) == false) {
                System.out.println("true" + " : " + valor);
                para = true;
                return valor;
            }
        }
        System.out.println("passou");
        return valor;
    }

    public boolean numeroCartaoExistente(int valor) {
        FuncionarioDAO f = new FuncionarioDAO();
        return f.numeroCartaoExistente(valor);
    }

    public ArrayList<FuncionarioBEAN> listarAll(int emp) {
        FuncionarioDAO f = new FuncionarioDAO();
        ArrayList<FuncionarioBEAN> retorno = f.listarALl(emp);
        return retorno;

    }

    public int localizar(int parseInt) {
        FuncionarioDAO f = new FuncionarioDAO();
        return f.localizar(parseInt).getCodigo();
    }

    public void excluir(int codExcluir) {
        FuncionarioDAO f = new FuncionarioDAO();
        f.excluir(codExcluir);
    }

    public FuncionarioBEAN listarUm(String cod) {
        FuncionarioDAO f = new FuncionarioDAO();
        return f.localizar(Integer.parseInt(cod));
    }

    public FuncionarioBEAN listarUm(int cod) {
        FuncionarioDAO f = new FuncionarioDAO();
        return f.localizar(cod);
    }

}
