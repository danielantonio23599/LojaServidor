/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.server.lojaserver.beans.CargoBEAN;
import com.server.lojaserver.beans.SharedPreferencesBEAN;
import com.server.lojaserver.controle.ControleCargo;
import com.server.lojaserver.controle.ControleLogin;
import com.server.lojaserver.controle.ControleVenda;
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
@WebServlet(name = "CancelarPedido", urlPatterns = {"/restaurante_server/CancelarPedido"}, initParams = {
    @WebInitParam(name = "pedido", value = ""),
    @WebInitParam(name = "motivo", value = ""),
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = "")})
public class CancelarPedido extends HttpServlet {

    ControleLogin l = new ControleLogin();
    ControleVenda ven = new ControleVenda();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usuario = new String(request.getParameter("nomeUsuario").getBytes("iso-8859-1"), "UTF-8");
        String senha = new String(request.getParameter("senha").getBytes("iso-8859-1"), "UTF-8");
        int cod = l.autenticaUsuario(usuario,senha);
        if (cod > 0) {
            response.setHeader("auth", "1");
            response.setHeader("sucesso",ven.excluirPedido(cod,request.getParameter("motivo"),request.getParameter("pedido")));

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
