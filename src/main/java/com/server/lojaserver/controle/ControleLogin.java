/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.controle;

import com.server.lojaserver.beans.CargoBEAN;
import com.server.lojaserver.beans.SharedPreferencesBEAN;
import com.server.lojaserver.beans.SharedPreferencesEmpresaBEAN;
import com.server.lojaeserver.persistencia.EmpresaDAO;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import com.server.lojaeserver.persistencia.FuncionarioDAO;

/**
 *
 * @author Daniel
 */
public class ControleLogin {

    public int autenticaUsuario(String email, String senha, int emp) {
        FuncionarioDAO f = new FuncionarioDAO();
        int funcionario = f.Login(email, senha, emp);
        return funcionario;
    }

    public int autenticaUsuario(String email, String senha) {
        FuncionarioDAO f = new FuncionarioDAO();
        int funcionario = f.Login(email, senha);
        return funcionario;
    }

    public int autenticaEmpresa(String email, String senha) {
        EmpresaDAO f = new EmpresaDAO();
        return f.login(email, senha);
    }

    public DefaultComboBoxModel buscar(String produto) {
        FuncionarioDAO f = new FuncionarioDAO();
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        ArrayList<String> pe = f.buscar(produto);
        for (String p : pe) {
            modelo.addElement(p);

        }
        return modelo;

    }

    public SharedPreferencesBEAN listarSharedPreferences(int cod, int emp) {
        FuncionarioDAO f = new FuncionarioDAO();
        return f.listarSharedPreferences(cod, emp);
    }

    public SharedPreferencesEmpresaBEAN listarSharedPreferencesEmpresa(int cod) {
        EmpresaDAO f = new EmpresaDAO();
        return f.localizar(cod);
    }

}
