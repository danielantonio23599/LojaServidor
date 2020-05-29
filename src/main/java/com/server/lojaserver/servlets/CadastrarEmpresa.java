/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.servlets;

import com.server.lojaserver.beans.EmpresaBEAN;
import com.server.lojaserver.controle.ControleEmpresa;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.server.lojaserver.controle.ControleLogin;
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
@WebServlet(name = "AdicionarEmpresa", urlPatterns = {"/restaurante_server/AdicionarEmpresa"}, initParams = {
    @WebInitParam(name = "empresa", value = "")})
public class CadastrarEmpresa extends HttpServlet {

    ControleEmpresa con = new ControleEmpresa();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String str = new String(request.getParameter("empresa").getBytes("iso-8859-1"), "UTF-8");
        EmpresaBEAN c = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create().fromJson(str, EmpresaBEAN.class);

        response.setHeader("sucesso", con.adicionarEmpresa(c));

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
