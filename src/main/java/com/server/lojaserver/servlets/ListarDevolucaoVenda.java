/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.servlets;

import com.google.gson.Gson;
import com.server.lojaserver.beans.DevolucaoBEAN;
import com.server.lojaserver.beans.ProdutoBEAN;
import com.server.lojaserver.controle.ControleDevolucao;
import com.server.lojaserver.controle.ControleLogin;
import com.server.lojaserver.controle.ControleProduto;
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
@WebServlet(name = "ListarDevolucaoVenda", urlPatterns = {"/loja_server/ListarDevolucaoVenda"}, initParams = {
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = ""),
    @WebInitParam(name = "venda", value = "")})
public class ListarDevolucaoVenda extends HttpServlet {

    ControleLogin l = new ControleLogin();
    ControleDevolucao pro = new ControleDevolucao();

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
            ArrayList<DevolucaoBEAN> u = pro.listarDevolucaoVenda(request.getParameter("venda"));
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(new Gson().toJson(u));

        } else {
            response.setHeader("auth", "0");
            ArrayList<DevolucaoBEAN> u = null;
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
