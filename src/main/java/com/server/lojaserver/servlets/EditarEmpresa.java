/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.servlets;

import com.google.gson.GsonBuilder;
import com.server.lojaserver.beans.CargoBEAN;
import com.server.lojaserver.beans.EmpresaBEAN;
import com.server.lojaserver.beans.FuncionarioBEAN;
import com.server.lojaserver.controle.ControleCargo;
import com.server.lojaserver.controle.ControleEmpresa;
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
@WebServlet(name = "EditarEmpresa", urlPatterns = {"/loja_server/EditarEmpresa"}, initParams = {
    @WebInitParam(name = "empresa", value = ""),
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = "")})
public class EditarEmpresa extends HttpServlet {

    ControleLogin l = new ControleLogin();
    ControleEmpresa con_fun = new ControleEmpresa();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String u = new String(request.getParameter("nomeUsuario").getBytes("iso-8859-1"), "UTF-8");
        String s = new String(request.getParameter("senha").getBytes("iso-8859-1"), "UTF-8");
        int cod = l.autenticaEmpresa(u, s);
        if (cod > 0) {
            response.setHeader("auth", "1");
            String str = new String(request.getParameter("empresa").getBytes("iso-8859-1"), "UTF-8");
            EmpresaBEAN c = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create().fromJson(str, EmpresaBEAN.class);
            c.setCodigo(cod);
            con_fun.atuaizar(c);
            response.setHeader("sucesso", "Sucesso");

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
