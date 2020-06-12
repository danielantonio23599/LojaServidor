/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.server.lojaserver.beans.AdmicaoBEAN;
import com.server.lojaserver.beans.CargoBEAN;
import com.server.lojaserver.beans.SharedPreferencesBEAN;
import com.server.lojaserver.controle.ControleAdmicao;
import com.server.lojaserver.controle.ControleCargo;
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
@WebServlet(name = "AdicionarAdmicao", urlPatterns = {"/loja_server/AdicionarAdmicao"}, initParams = {
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = ""),
    @WebInitParam(name = "admicao", value = "")})
public class Adimitir extends HttpServlet {

    ControleLogin l = new ControleLogin();
    ControleAdmicao con = new ControleAdmicao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usuario = new String(request.getParameter("nomeUsuario").getBytes("iso-8859-1"), "UTF-8");
        String senha = new String(request.getParameter("senha").getBytes("iso-8859-1"), "UTF-8");
        int cod = l.autenticaUsuario(usuario, senha);
        if (cod > 0) {
            response.setHeader("auth", "1");
            String str = new String(request.getParameter("admicao").getBytes("iso-8859-1"), "UTF-8");
            AdmicaoBEAN c = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().fromJson(str, AdmicaoBEAN.class);
            response.setHeader("sucesso", con.admitir(c));

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
