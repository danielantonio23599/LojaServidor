/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.servlets;

import com.google.gson.Gson;
import com.server.lojaserver.beans.DevolucaoBEAN;
import com.server.lojaserver.beans.Pedido;
import com.server.lojaserver.beans.PedidoBEAN;
import com.server.lojaserver.controle.ControleDevolucao;
import com.server.lojaserver.controle.ControleLogin;
import com.server.lojaserver.controle.ControlePedido;
import com.server.lojaserver.controle.ControleVenda;
import java.io.IOException;
import java.util.ArrayList;
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
@WebServlet(name = "ListarPedidos", urlPatterns = {"/loja_server/ListarPedidos"}, initParams = {
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = "")})
public class ListarPedidosPendentes extends HttpServlet {

    ControleLogin l = new ControleLogin();
    ControlePedido con = new ControlePedido();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String n = new String(request.getParameter("nomeUsuario").getBytes("iso-8859-1"), "UTF-8");
        String s = new String(request.getParameter("senha").getBytes("iso-8859-1"), "UTF-8");
        int cod = l.autenticaEmpresa(n, s);
        if (cod > 0) {
            response.setHeader("auth", "1");
          //  ArrayList<PedidoBEAN> u = con.listarPedidos(cod);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            //response.getWriter().println(new Gson().toJson(u));

        } else {
            response.setHeader("auth", "0");
            ArrayList<Pedido> u = null;
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(new Gson().toJson(u));
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
