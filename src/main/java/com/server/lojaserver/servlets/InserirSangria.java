/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.servlets;

import com.google.gson.GsonBuilder;
import com.server.lojaserver.beans.CaixaBEAN;
import com.server.lojaserver.beans.SangriaBEAN;
import com.server.lojaserver.controle.ControleCaixa;
import com.server.lojaserver.controle.ControleLogin;
import com.server.lojaserver.controle.ControleSangria;
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
@WebServlet(name = "InserirSangria", urlPatterns = {"/loja_server/InserirSangria"}, initParams = {
    @WebInitParam(name = "sangria", value = ""),
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = "")})
public class InserirSangria extends HttpServlet {

    ControleLogin l = new ControleLogin();
    ControleSangria con = new ControleSangria();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String n = new String(request.getParameter("nomeUsuario").getBytes("iso-8859-1"), "UTF-8");
        String s = new String(request.getParameter("senha").getBytes("iso-8859-1"), "UTF-8");
        response.setHeader("auth", "1");
        String str = new String(request.getParameter("sangria").getBytes("iso-8859-1"), "UTF-8");
        SangriaBEAN c = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create().fromJson(str, SangriaBEAN.class);
        response.setHeader("sucesso", con.cadastrar(c, n, s));
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
