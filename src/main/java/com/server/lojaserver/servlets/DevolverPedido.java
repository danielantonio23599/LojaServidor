/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.server.lojaserver.beans.CargoBEAN;
import com.server.lojaserver.beans.DespesaBEAN;
import com.server.lojaserver.beans.DevolucaoBEAN;
import com.server.lojaserver.beans.SharedPreferencesBEAN;
import com.server.lojaserver.controle.ControleCargo;
import com.server.lojaserver.controle.ControleDevolucao;
import com.server.lojaserver.controle.ControleLogin;
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
@WebServlet(name = "DevolucaoPedido", urlPatterns = {"/loja_server/DevolucaoPedido"}, initParams = {
    @WebInitParam(name = "devolucao", value = ""),
    @WebInitParam(name = "pedido", value = ""),
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = "")})
public class DevolverPedido extends HttpServlet {

    ControleLogin l = new ControleLogin();
    ControleDevolucao ven = new ControleDevolucao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usuario = new String(request.getParameter("nomeUsuario").getBytes("iso-8859-1"), "UTF-8");
        String senha = new String(request.getParameter("senha").getBytes("iso-8859-1"), "UTF-8");
        int pedido = Integer.parseInt(new String(request.getParameter("pedido").getBytes("iso-8859-1"), "UTF-8"));
        int cod = l.autenticaEmpresa(usuario, senha);
        if (cod > 0) {
            response.setHeader("auth", "1");
            String str = new String(request.getParameter("devolucao").getBytes("iso-8859-1"), "UTF-8");
            DevolucaoBEAN c = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create().fromJson(str, DevolucaoBEAN.class);
            response.setHeader("sucesso", ven.inserirDevolucao(c, pedido, cod));

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
