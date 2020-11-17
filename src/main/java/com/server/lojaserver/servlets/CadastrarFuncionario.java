/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.servlets;

import com.google.gson.GsonBuilder;
import com.server.lojaserver.beans.CargoBEAN;
import com.server.lojaserver.beans.FuncionarioBEAN;
import com.server.lojaserver.beans.SharedPreferencesEmpresaBEAN;
import com.server.lojaserver.controle.ControleAdmicao;
import com.server.lojaserver.controle.ControleCargo;
import com.server.lojaserver.controle.ControleFuncionario;
import com.server.lojaserver.controle.ControleLogin;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Daniel
 */
@WebServlet(name = "AdicionarFuncionario", urlPatterns = {"/loja_server/AdicionarFuncionario"}, initParams = {
    @WebInitParam(name = "funcionario", value = ""),
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = "")})
public class CadastrarFuncionario extends HttpServlet {

    ControleLogin l = new ControleLogin();
    ControleFuncionario con_fun = new ControleFuncionario();
    ControleAdmicao con_adm = new ControleAdmicao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usuario = new String(request.getParameter("nomeUsuario").getBytes("iso-8859-1"), "UTF-8");
        String senha = new String(request.getParameter("senha").getBytes("iso-8859-1"), "UTF-8");
        int log = l.autenticaEmpresa(usuario,senha);
        if (log > 0) {
            response.setHeader("auth", "1");
            String str = new String(request.getParameter("funcionario").getBytes("iso-8859-1"), "UTF-8");
            FuncionarioBEAN c = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create().fromJson(str, FuncionarioBEAN.class);
            int cod = con_fun.cadastrar(c);
            if (cod > 0) {
                response.setHeader("sucesso", con_adm.preCadastro(cod, log));
            } else {
                response.setHeader("sucesso", "Funcionario ja CADASTRADO!!");
            }

        } else {
            response.setHeader("auth", "0");

        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
