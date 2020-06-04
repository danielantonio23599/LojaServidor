/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.controle;

import com.server.lojaeserver.persistencia.ClienteDAO;
import com.server.lojaeserver.persistencia.EnderecoDAO;
import com.server.lojaserver.beans.ClienteBEAN;
import com.server.lojaserver.beans.EnderecoBEAN;
import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class ControleCliente {

    public int adicionar(ClienteBEAN c, EnderecoBEAN e) {
        ClienteDAO dao = new ClienteDAO();
        EnderecoDAO endao = new EnderecoDAO();
        ClienteBEAN aux = dao.listarPorEmail(c.getEmail());
        if (aux.getCodigo() == 0) {
            int cliente = dao.adicionar(c);
            if(cliente >0){
                e.setCliente(cliente);
                endao.adicionar(e);
            
            }
            return 0;
        } else {
            return aux.getCodigo();
        }
    }

    public int editar(ClienteBEAN c) {
        ClienteDAO dao = new ClienteDAO();
        ClienteBEAN aux = dao.listarPorEmail(c.getEmail());
        if (aux.getCodigo() > 0) {
            c.setCodigo(aux.getCodigo());
            dao.editar(c);
            return 0;
        } else {
            return aux.getCodigo();
        }
    }

    public ArrayList<ClienteBEAN> listarClietes() {
        ClienteDAO dao = new ClienteDAO();
        return dao.listarALl();
    }

    public ArrayList<ClienteBEAN> listarClietesPesquisa(String nome) {
        ClienteDAO dao = new ClienteDAO();
        return dao.listarPorPesquisa(nome);
    }

    public ClienteBEAN listarPorEmail(String email) {
        ClienteDAO dao = new ClienteDAO();
        return dao.listarPorEmail(email);
    }

}
