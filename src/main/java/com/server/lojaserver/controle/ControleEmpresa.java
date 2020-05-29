/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.controle;

import com.server.lojaserver.beans.EmpresaBEAN;
import com.server.lojaserver.beans.FuncionarioBEAN;
import com.server.lojaeserver.persistencia.EmpresaDAO;
import com.server.lojaserver.util.Time;
import java.text.ParseException;

/**
 *
 * @author Daniel
 */
public class ControleEmpresa {

    public String adicionarEmpresa(EmpresaBEAN e) {
        FuncionarioBEAN fun = new FuncionarioBEAN();
        fun.setEmail(e.getEmail());
        fun.setSenha(e.getSenha());
        fun.setLogradouro(e.getLogradouro());
        fun.setBairro(e.getBairro());
        fun.setNumero(e.getNumero());
        fun.setComplemento(e.getComplemento());
        fun.setUf(e.getUf());
        fun.setCep(e.getCep());
        fun.setCidade(e.getCidade());
        EmpresaDAO emp = new EmpresaDAO();
        ControleFuncionario f = new ControleFuncionario();
        int codFun = f.cadastrarADM(fun);
        int codEmp = emp.adcionar(e);
        if (codFun > 0) {
            admitir(codFun, codEmp);
            return "Sucesso";
        } else {
            return "Algo de errado";
        }
    }

    private void admitir(int codFun, int codEmp) {
        ControleAdmicao con = new ControleAdmicao();
        con.admitir(codFun, codEmp);
    }

    public void atuaizar(EmpresaBEAN c) {
        EmpresaDAO emp = new EmpresaDAO();
        emp.editar(c);
    }

    public EmpresaBEAN listarUm(int cod) {
        EmpresaDAO emp = new EmpresaDAO();
        return emp.listar(cod);
    }

}
