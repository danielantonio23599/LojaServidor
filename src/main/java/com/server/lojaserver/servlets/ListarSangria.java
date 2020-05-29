/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.server.lojaserver.beans.CaixaBEAN;
import com.server.lojaserver.beans.SangriaBEAN;
import com.server.lojaserver.controle.ControleCaixa;
import com.server.lojaserver.controle.ControleLogin;
import com.server.lojaserver.controle.ControleSangria;
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
@WebServlet(name = "ListarSangria", urlPatterns = {"/restaurante_server/ListarSangria"}, initParams = {
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = "")})
public class ListarSangria extends HttpServlet {

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
        String ss = new String(request.getParameter("senha").getBytes("iso-8859-1"), "UTF-8");
        int cod = l.autenticaEmpresa(n,ss);
        if (cod > 0) {
            response.setHeader("auth", "1");
            ArrayList<SangriaBEAN> s = con.listarSangriasCaixa(cod);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(new Gson().toJson(s));

        } else {
            response.setHeader("auth", "0");
            ArrayList<SangriaBEAN> s  = null;
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(new Gson().toJson(s));
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
