/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.servlets;

import com.google.gson.Gson;
import com.server.lojaserver.beans.Mesa;
import com.server.lojaserver.beans.Venda;
import com.server.lojaserver.beans.VendaBEAN;

import com.server.lojaserver.controle.ControleLogin;
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
@WebServlet(name = "ListarVendasCombinacao", urlPatterns = {"/loja_server/ListarVendasCombinacao"}, initParams = {
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = ""),
    @WebInitParam(name = "dataIn", value = ""),
    @WebInitParam(name = "dataFin", value = ""),
    @WebInitParam(name = "status", value = ""),
    @WebInitParam(name = "consulta", value = "")})
public class ListarVendasCombinacao extends HttpServlet {

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
        String status = new String(request.getParameter("status").getBytes("iso-8859-1"), "UTF-8");
        String dataIn = new String(request.getParameter("dataIn").getBytes("iso-8859-1"), "UTF-8");
        String dataFin = new String(request.getParameter("dataFin").getBytes("iso-8859-1"), "UTF-8");
        String consulta = new String(request.getParameter("consulta").getBytes("iso-8859-1"), "UTF-8");
        int codE = l.autenticaEmpresa(n, s);
        if (codE > 0) {
            response.setHeader("auth", "1");
            ArrayList<Venda> u = con.getVendasPorCominacao(codE, dataIn, dataFin, consulta, status);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(new Gson().toJson(u));

        } else {
            response.setHeader("auth", "0");
            ArrayList<Venda> u = null;
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
