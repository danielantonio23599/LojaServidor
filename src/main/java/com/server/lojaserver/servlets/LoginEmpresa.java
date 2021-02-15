/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.servlets;

import com.google.gson.Gson;
import com.server.lojaserver.beans.SharedPreferencesBEAN;
import com.server.lojaserver.beans.SharedPreferencesEmpresaBEAN;
import com.server.lojaserver.controle.ControleLogin;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Guilherme
 */
@WebServlet(name = "LoginEmpresa", urlPatterns = {"/loja_server/LoginEmpresa"}, initParams = {
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = "")})
public class LoginEmpresa extends HttpServlet {

    ControleLogin l = new ControleLogin();

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
        System.out.println("cod : " + cod);
        if (cod > 0) {
            SharedPreferencesEmpresaBEAN u = l.listarSharedPreferencesEmpresa(cod);
            response.setHeader("auth", "1");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(new Gson().toJson(u));
        } else {
            SharedPreferencesEmpresaBEAN u = null;
            response.setHeader("auth", "0");
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
