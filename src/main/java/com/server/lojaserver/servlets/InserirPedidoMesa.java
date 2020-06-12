/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.servlets;

import com.google.gson.GsonBuilder;
import com.google.zxing.WriterException;
import com.server.lojaserver.beans.CaixaBEAN;
import com.server.lojaserver.beans.PedidoBEAN;
import com.server.lojaserver.beans.SangriaBEAN;
import com.server.lojaserver.controle.ControleCaixa;
import com.server.lojaserver.controle.ControleLogin;
import com.server.lojaserver.controle.ControleSangria;
import com.server.lojaserver.controle.ControleVenda;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "InserirPedidoMesa", urlPatterns = {"/loja_server/InserirPedidoMesa"}, initParams = {
    @WebInitParam(name = "pedido", value = ""),
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = "")})
public class InserirPedidoMesa extends HttpServlet {

    ControleLogin l = new ControleLogin();
    ControleVenda con = new ControleVenda();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         String n = new String(request.getParameter("nomeUsuario").getBytes("iso-8859-1"), "UTF-8");
        String s = new String(request.getParameter("senha").getBytes("iso-8859-1"), "UTF-8");
        int cod = l.autenticaEmpresa(n,s);
        if (cod > 0) {
            response.setHeader("auth", "1");
            String str = new String (request.getParameter("pedido").getBytes ("iso-8859-1"), "UTF-8");
            PedidoBEAN c = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create().fromJson(str, PedidoBEAN.class);

            response.setHeader("sucesso", con.adicionar(c,cod) + "");

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
