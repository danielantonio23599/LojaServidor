/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.server.lojaserver.beans.DespesaBEAN;
import com.server.lojaserver.beans.SharedPreferencesBEAN;
import com.server.lojaserver.controle.ControleDespesa;
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
@WebServlet(name = "AdicionarDespesa", urlPatterns = {"/loja_server/AdicionarDespesa"}, initParams = {
    @WebInitParam(name = "despesa", value = ""),
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = "")})
public class CadastrarDespesas extends HttpServlet {

    ControleLogin l = new ControleLogin();
    ControleDespesa con_des = new ControleDespesa();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usuario = new String(request.getParameter("nomeUsuario").getBytes("iso-8859-1"), "UTF-8");
        String senha = new String(request.getParameter("senha").getBytes("iso-8859-1"), "UTF-8");
        response.setHeader("auth", "1");
        String str = new String(request.getParameter("despesa").getBytes("iso-8859-1"), "UTF-8");
        DespesaBEAN c = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create().fromJson(str, DespesaBEAN.class);
        response.setHeader("sucesso", con_des.adicionar(c, usuario, senha));
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
