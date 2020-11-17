/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.servlets;

import com.google.gson.GsonBuilder;
import com.server.lojaserver.beans.OrdemServicoBEAN;
import com.server.lojaserver.controle.ControleCaixa;
import com.server.lojaserver.controle.ControleLogin;
import com.server.lojaserver.controle.ControleOS;
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
@WebServlet(name = "AtualizarOS", urlPatterns = {"/loja_server/AtualizarOS"}, initParams = {
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = ""),
    @WebInitParam(name = "os", value = "")})
public class AtualizarOS extends HttpServlet {

    ControleLogin l = new ControleLogin();
    ControleOS con = new ControleOS();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String n = new String(request.getParameter("nomeUsuario").getBytes("iso-8859-1"), "UTF-8");
        String s = new String(request.getParameter("senha").getBytes("iso-8859-1"), "UTF-8");
        String str = new String(request.getParameter("os").getBytes("iso-8859-1"), "UTF-8");
        OrdemServicoBEAN os = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create().fromJson(str, OrdemServicoBEAN.class);
        int cod = l.autenticaEmpresa(n, s);
        if (cod > 0) {
            response.setHeader("auth", "1");
            response.setHeader("sucesso", con.atualizar(os) + "");

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
